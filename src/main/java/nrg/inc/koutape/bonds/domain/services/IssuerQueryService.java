package nrg.inc.koutape.bonds.domain.services;

import nrg.inc.koutape.bonds.domain.model.aggregates.Bond;
import nrg.inc.koutape.bonds.domain.model.aggregates.Issuer;
import nrg.inc.koutape.bonds.domain.model.queries.GetAllBondsByIssuerIdQuery;
import nrg.inc.koutape.bonds.domain.model.queries.GetIssuerByUsernameQuery;

import java.util.List;
import java.util.Optional;

public interface IssuerQueryService {
    Optional<Issuer> handle(GetIssuerByUsernameQuery query);
    List<Bond> handle(GetAllBondsByIssuerIdQuery query);
}
