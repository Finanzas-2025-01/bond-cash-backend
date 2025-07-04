package nrg.inc.koutape.bonds.domain.services;

import nrg.inc.koutape.bonds.domain.model.aggregates.Issuer;
import nrg.inc.koutape.bonds.domain.model.commands.CreateIssuerCommand;
import nrg.inc.koutape.bonds.domain.model.queries.GetIssuerByUsernameQuery;

import java.util.Optional;

public interface IssuerCommandService {
    Optional<Issuer> handle(CreateIssuerCommand command);
}
