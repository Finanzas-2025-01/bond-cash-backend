package nrg.inc.koutape.bonds.application.internal.commandservices;

import nrg.inc.koutape.bonds.domain.model.aggregates.Bond;
import nrg.inc.koutape.bonds.domain.model.commands.CreateBondCommand;
import nrg.inc.koutape.bonds.domain.model.commands.GenerateCashFlowsByBondIdCommand;
import nrg.inc.koutape.bonds.domain.model.commands.HireBondCommand;
import nrg.inc.koutape.bonds.domain.services.BondCommandService;
import nrg.inc.koutape.bonds.infrastructure.persistence.jpa.repositories.BondHolderRepository;
import nrg.inc.koutape.bonds.infrastructure.persistence.jpa.repositories.BondRepository;
import nrg.inc.koutape.bonds.infrastructure.persistence.jpa.repositories.IssuerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BondCommandServiceImpl implements BondCommandService {

    private final BondHolderRepository bondHolderRepository;
    private final BondRepository bondRepository;
    private final IssuerRepository issuerRepository;

    public BondCommandServiceImpl(BondHolderRepository bondHolderRepository, BondRepository bondRepository, IssuerRepository issuerRepository) {
        this.bondHolderRepository = bondHolderRepository;
        this.bondRepository = bondRepository;
        this.issuerRepository = issuerRepository;
    }

    @Override
    public Optional<Bond> handle(HireBondCommand command) {
        var bondHolder = this.bondHolderRepository.findById(command.bondHolderId());
        if (bondHolder.isEmpty()) {
            throw new IllegalArgumentException("Bond holder with id " + command.bondHolderId() + " does not exist");
        }
        var bond = this.bondRepository.findById(command.bondId());
        if (bond.isEmpty()) {
            throw new IllegalArgumentException("Bond with id " + command.bondId() + " does not exist");
        }

        bondHolder.get().addBond(bond.get());

        try{
            this.bondHolderRepository.save(bondHolder.get());
            return Optional.of(bond.get());
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

        try {
            bond.get().generateCashFlows();
            this.bondRepository.save(bond.get());
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate cash flows for bond: " + e.getMessage(), e);
        }
    }
}
