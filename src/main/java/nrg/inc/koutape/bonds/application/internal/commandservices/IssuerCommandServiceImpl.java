package nrg.inc.koutape.bonds.application.internal.commandservices;

import nrg.inc.koutape.bonds.domain.model.aggregates.Issuer;
import nrg.inc.koutape.bonds.domain.model.commands.CreateIssuerCommand;
import nrg.inc.koutape.bonds.domain.services.IssuerCommandService;
import nrg.inc.koutape.bonds.infrastructure.persistence.jpa.repositories.IssuerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IssuerCommandServiceImpl implements IssuerCommandService {

    private final IssuerRepository bondHolderRepository;

    public IssuerCommandServiceImpl(IssuerRepository bondHolderRepository) {
        this.bondHolderRepository = bondHolderRepository;
    }

    @Override
    public Optional<Issuer> handle(CreateIssuerCommand createBondCommand) {
        var bondHolder = new Issuer(createBondCommand);
        var savedBondHolder = bondHolderRepository.save(bondHolder);
        return Optional.of(savedBondHolder);
    }
}
