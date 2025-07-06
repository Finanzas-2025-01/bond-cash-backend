package nrg.inc.koutape.bonds.application.internal.commandservices;

import nrg.inc.koutape.bonds.domain.model.aggregates.Bond;
import nrg.inc.koutape.bonds.domain.model.commands.*;
import nrg.inc.koutape.bonds.domain.model.valueobjects.BondType;
import nrg.inc.koutape.bonds.domain.services.BondCommandService;
import nrg.inc.koutape.bonds.infrastructure.persistence.jpa.repositories.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BondCommandServiceImpl implements BondCommandService {

    private final BondHolderRepository bondHolderRepository;
    private final BondRepository bondRepository;
    private final IssuerRepository issuerRepository;
    private final CashFlowRepository cashFlowRepository;
    private final CashFlowGracePeriodRepository cashFlowGracePeriodRepository;
    private final BondResultRepository bondResultRepository;

    public BondCommandServiceImpl(BondHolderRepository bondHolderRepository, BondRepository bondRepository, IssuerRepository issuerRepository, CashFlowRepository cashFlowRepository, CashFlowGracePeriodRepository cashFlowGracePeriodRepository, BondResultRepository bondResultRepository) {
        this.bondHolderRepository = bondHolderRepository;
        this.bondRepository = bondRepository;
        this.issuerRepository = issuerRepository;
        this.cashFlowRepository = cashFlowRepository;
        this.cashFlowGracePeriodRepository = cashFlowGracePeriodRepository;
        this.bondResultRepository = bondResultRepository;
    }

    @Override
    public Optional<Bond> handle(HireBondCommand command) {

        var bondHolder = this.bondHolderRepository.findById(command.bondHolderId());

        if (bondHolder.isEmpty()) {
            throw new IllegalArgumentException("Bond holder with id " + command.bondHolderId() + " does not exist");
        }

        var baseBond = this.bondRepository.findById(command.bondId());

        if (baseBond.isEmpty()) {
            throw new IllegalArgumentException("Bond with id " + command.bondId() + " does not exist");
        }

        var issuer = this.issuerRepository.findById(baseBond.get().getIssuer().getId());
        if (issuer.isEmpty()) {
            throw new IllegalArgumentException("Issuer with id " + baseBond.get().getIssuer().getId() + " does not exist");
        }

        var bond = new Bond(baseBond.get());

        bond.setIssuer(issuer.get());
        bond.setBondholder(bondHolder.get());
        issuer.get().addBond(bond);
        bondHolder.get().addBond(bond);
        try {
            this.issuerRepository.save(issuer.get());

            this.bondHolderRepository.save(bondHolder.get());

            var hiredBond = bondRepository.save(bond);

            return Optional.of(hiredBond);
        } catch (Exception e) {
            throw new RuntimeException("Failed to hire bond: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Bond> handle(CreateBondCommand command) {
        if (command.daysPerYear() != 360 && command.daysPerYear() != 365) {
            throw new IllegalArgumentException("Days per year must be either 360 or 365");
        }
        var bond = new Bond(command);
        var issuer = this.issuerRepository.findById(command.issuerId());
        if (issuer.isEmpty()) {
            throw new IllegalArgumentException("Issuer with id " + command.issuerId() + " does not exist");
        }

        bond.setIssuer(issuer.get());
        bond.generateCashFlowGracePeriods();
        issuer.get().addBond(bond);

        this.issuerRepository.save(issuer.get());
        var createdBond = bondRepository.save(bond);

        return Optional.of(createdBond);
    }

    @Override
    public void handle(GenerateCashFlowsByBondIdCommand command) {
        var bond = this.bondRepository.findById(command.bondId());

        if (bond.isEmpty()) {
            throw new IllegalArgumentException("Bond with id " + command.bondId() + " does not exist");
        }

        if (bond.get().getCashFlows() != null && !bond.get().getCashFlows().isEmpty()) {
            var cashFlows = this.cashFlowRepository.findByBondId(command.bondId());
            for (var cashFlow : cashFlows) {
                this.cashFlowRepository.delete(cashFlow);
            }
            bond.get().getCashFlows().clear();
        }

        try {
            bond.get().generateCashFlows();
            this.bondRepository.save(bond.get());
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate cash flows for bond: " + e.getMessage(), e);
        }
    }

    @Override
    public void handle(UpdateGracePeriodByPeriodNumberAndBondIdCommand command) {
        var bond = this.bondRepository.findById(command.bondId());
        if (bond.isEmpty()) {
            throw new IllegalArgumentException("Bond with id " + command.bondId() + " does not exist");
        }

        if(bond.get().getBondType() != BondType.BASE){
            throw new IllegalArgumentException("Bond with id " + command.bondId() + " is not a base bond and cannot have its grace period updated");
        }

        var cashFlowGracePeriod = this.cashFlowGracePeriodRepository.findByBondAndPeriodNumber(bond.get(), command.periodNumber());
        if (cashFlowGracePeriod.isEmpty()) {
            throw new IllegalArgumentException("Cash flow grace period for bond with id " + command.bondId() + " and period number " + command.periodNumber() + " does not exist");
        }

        var updatedGracePeriod = cashFlowGracePeriod.get();
        updatedGracePeriod.setGracePeriod(command.gracePeriod());
        try {
            this.cashFlowGracePeriodRepository.save(updatedGracePeriod);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update grace period: " + e.getMessage(), e);
        }
    }

    @Override
    public void handle(UpdateBondCommand command) {
        var bond = this.bondRepository.findById(command.bondId());
        if (bond.isEmpty()) {
            throw new IllegalArgumentException("Bond with id " + command.bondId() + " does not exist");
        }
        var updatedBond = bond.get();
        updatedBond.updateBond(command);
        try {
            this.bondRepository.save(updatedBond);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update bond: " + e.getMessage(), e);
        }
    }

    @Override
    public void handle(GenerateBondResultByBondIdCommand command) {
        var bond = this.bondRepository.findById(command.bondId());

        if (bond.isEmpty()) {
            throw new IllegalArgumentException("Bond with id " + command.bondId() + " does not exist");
        }

        try {
            bond.get().calculateResult();
            this.bondRepository.save(bond.get());
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate bond result: " + e.getMessage(), e);
        }
    }
}
