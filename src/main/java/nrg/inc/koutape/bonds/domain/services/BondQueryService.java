package nrg.inc.koutape.bonds.domain.services;

import nrg.inc.koutape.bonds.domain.model.aggregates.Bond;
import nrg.inc.koutape.bonds.domain.model.queries.GetBondByIdQuery;

import java.util.Optional;

public interface BondQueryService {
    Optional<Bond> handle(GetBondByIdQuery query);
}
