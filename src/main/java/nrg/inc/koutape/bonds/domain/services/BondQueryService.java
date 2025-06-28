package nrg.inc.koutape.bonds.domain.services;

import nrg.inc.koutape.bonds.domain.model.aggregates.Bond;
import nrg.inc.koutape.bonds.domain.model.aggregates.BondHolder;
import nrg.inc.koutape.bonds.domain.model.aggregates.CashFlow;
import nrg.inc.koutape.bonds.domain.model.queries.GetAllBondsQuery;
import nrg.inc.koutape.bonds.domain.model.queries.GetBondByIdQuery;
import nrg.inc.koutape.bonds.domain.model.queries.GetBondHoldersByBondIdQuery;
import nrg.inc.koutape.bonds.domain.model.queries.GetCashFlowsByBondIdQuery;

import java.util.List;
import java.util.Optional;

public interface BondQueryService {
    Optional<Bond> handle(GetBondByIdQuery query);
    List<Bond> handle(GetAllBondsQuery query);
    List<CashFlow> handle(GetCashFlowsByBondIdQuery query);
    List<BondHolder> handle(GetBondHoldersByBondIdQuery query);
}
