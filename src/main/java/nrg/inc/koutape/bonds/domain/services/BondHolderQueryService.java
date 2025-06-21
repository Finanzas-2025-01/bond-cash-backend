package nrg.inc.koutape.bonds.domain.services;

import nrg.inc.koutape.bonds.domain.model.aggregates.Bond;
import nrg.inc.koutape.bonds.domain.model.aggregates.BondHolder;
import nrg.inc.koutape.bonds.domain.model.queries.GetAllBondsByBondHolderIdQuery;
import nrg.inc.koutape.bonds.domain.model.queries.GetBondHolderByUsernameQuery;

import java.util.List;
import java.util.Optional;

public interface BondHolderQueryService {
    Optional<BondHolder> handle(GetBondHolderByUsernameQuery query);
    List<Bond> handle(GetAllBondsByBondHolderIdQuery query);
}
