package nrg.inc.koutape.bonds.application.internal.commandservices;

import nrg.inc.koutape.bonds.domain.model.aggregates.BondHolder;
import nrg.inc.koutape.bonds.domain.model.commands.CreateBondHolderCommand;
import nrg.inc.koutape.bonds.domain.services.BondHolderCommandService;
import nrg.inc.koutape.bonds.infrastructure.persistence.jpa.repositories.BondHolderRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BondHolderCommandServiceImpl implements BondHolderCommandService {

    private final BondHolderRepository bondHolderRepository;

    public BondHolderCommandServiceImpl(BondHolderRepository bondHolderRepository) {
        this.bondHolderRepository = bondHolderRepository;
    }

    @Override
    public Optional<BondHolder> handle(CreateBondHolderCommand createBondCommand) {
        var bondHolder = new BondHolder(createBondCommand);
        var savedBondHolder = bondHolderRepository.save(bondHolder);
        return Optional.of(savedBondHolder);
    }
}
