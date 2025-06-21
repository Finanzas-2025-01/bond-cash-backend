package nrg.inc.koutape.bonds.application.internal.commandservices;

import nrg.inc.koutape.bonds.domain.model.aggregates.BondHolder;
import nrg.inc.koutape.bonds.domain.model.commands.CreateBondHolderCommand;
import nrg.inc.koutape.bonds.domain.services.BondHolderCommandService;
import nrg.inc.koutape.bonds.infrastructure.persistence.jpa.repositories.BondHolderRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BondHolderCommandServiceImpl implements BondHolderCommandService {

    private final BondHolderRepository investorRepository;

    public BondHolderCommandServiceImpl(BondHolderRepository investorRepository) {
        this.investorRepository = investorRepository;
    }

    @Override
    public Optional<BondHolder> handle(CreateBondHolderCommand command) {
        var bondHolder = new BondHolder(command);
        var createdBondHolder= investorRepository.save(bondHolder);
        return Optional.of(createdBondHolder);
    }
}
