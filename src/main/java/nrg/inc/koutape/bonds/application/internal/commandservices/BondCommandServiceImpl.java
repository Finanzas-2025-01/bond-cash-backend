package nrg.inc.koutape.bonds.application.internal.commandservices;

import nrg.inc.koutape.bonds.domain.model.aggregates.Bond;
import nrg.inc.koutape.bonds.domain.model.commands.*;
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

    public BondCommandServiceImpl(BondHolderRepository bondHolderRepository, BondRepository bondRepository, IssuerRepository issuerRepository, CashFlowRepository cashFlowRepository, CashFlowGracePeriodRepository cashFlowGracePeriodRepository) {
        this.bondHolderRepository = bondHolderRepository;
        this.bondRepository = bondRepository;
        this.issuerRepository = issuerRepository;
        this.cashFlowRepository = cashFlowRepository;
        this.cashFlowGracePeriodRepository = cashFlowGracePeriodRepository;
    }

    @Override
    public Optional<Bond> handle(HireBondCommand command) {
        System.out.println("[HireBond] Iniciando contratación de bono con BondHolderId: " + command.bondHolderId() + ", BondId: " + command.bondId());

        var bondHolder = this.bondHolderRepository.findById(command.bondHolderId());
        System.out.println("[HireBond] Resultado búsqueda BondHolder: " + bondHolder);

        if (bondHolder.isEmpty()) {
            System.out.println("[HireBond] BondHolder no encontrado para id: " + command.bondHolderId());
            throw new IllegalArgumentException("Bond holder with id " + command.bondHolderId() + " does not exist");
        }

        var baseBond = this.bondRepository.findById(command.bondId());
        System.out.println("[HireBond] Resultado búsqueda Bond: " + baseBond);

        if (baseBond.isEmpty()) {
            System.out.println("[HireBond] Bond no encontrado para id: " + command.bondId());
            throw new IllegalArgumentException("Bond with id " + command.bondId() + " does not exist");
        }

        var issuer = this.issuerRepository.findById(baseBond.get().getIssuer().getId());
        if (issuer.isEmpty()) {
            System.out.println("[HireBond] Emisor no encontrado para id: " + baseBond.get().getIssuer().getId());
            throw new IllegalArgumentException("Issuer with id " + baseBond.get().getIssuer().getId() + " does not exist");
        }

        var bond = new Bond(baseBond.get());
        System.out.println("[HireBond] Nuevo Bond creado a partir de baseBond: " + bond);

        bond.setIssuer(issuer.get());
        bond.setBondholder(bondHolder.get());
        issuer.get().addBond(bond);
        bondHolder.get().addBond(bond);
        System.out.println("[HireBond] Bond agregado a BondHolder. Bonds actuales: " + bondHolder.get().getBonds());

        try {
            System.out.println("[HireBond] Guardando issuer: " + issuer.get());
            this.issuerRepository.save(issuer.get());

            System.out.println("[HireBond] Guardando bondHolder: " + bondHolder.get());
            this.bondHolderRepository.save(bondHolder.get());

            System.out.println("[HireBond] Guardando bond: " + bond);
            var hiredBond = bondRepository.save(bond);

            System.out.println("[HireBond] BondHolder guardado exitosamente con nuevos bonds: " + bondHolder.get().getBonds());
            return Optional.of(hiredBond);
        } catch (Exception e) {
            System.out.println("[HireBond] Error al guardar BondHolder: " + e.getMessage());
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
        System.out.println("[GenerateCashFlows] Iniciando generación de cash flows para bondId: " + command.bondId());
        var bond = this.bondRepository.findById(command.bondId());
        System.out.println("[GenerateCashFlows] Resultado búsqueda Bond: " + bond);

        if (bond.isEmpty()) {
            System.out.println("[GenerateCashFlows] Bond no encontrado para id: " + command.bondId());
            throw new IllegalArgumentException("Bond with id " + command.bondId() + " does not exist");
        }

        if (bond.get().getCashFlows() != null && !bond.get().getCashFlows().isEmpty()) {
            System.out.println("[GenerateCashFlows] CashFlows existentes encontrados, eliminando...");
            var cashFlows = this.cashFlowRepository.findByBondId(command.bondId());
            for (var cashFlow : cashFlows) {
                System.out.println("[GenerateCashFlows] Eliminando CashFlow: " + cashFlow);
                this.cashFlowRepository.delete(cashFlow);
            }
            bond.get().getCashFlows().clear();
        }

        try {
            System.out.println("[GenerateCashFlows] Generando nuevos CashFlows...");
            bond.get().generateCashFlows();
            this.bondRepository.save(bond.get());
            System.out.println("[GenerateCashFlows] CashFlows generados y guardados exitosamente para bondId: " + command.bondId());
        } catch (Exception e) {
            System.out.println("[GenerateCashFlows] Error al generar cash flows: " + e.getMessage());
            throw new RuntimeException("Failed to generate cash flows for bond: " + e.getMessage(), e);
        }
    }

    @Override
    public void handle(UpdateGracePeriodByPeriodNumberAndBondIdCommand command) {
        var bond = this.bondRepository.findById(command.bondId());
        if (bond.isEmpty()) {
            throw new IllegalArgumentException("Bond with id " + command.bondId() + " does not exist");
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
}
