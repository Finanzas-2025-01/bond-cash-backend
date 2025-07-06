package nrg.inc.koutape.bonds.domain.services;

import nrg.inc.koutape.bonds.domain.model.aggregates.Bond;
import nrg.inc.koutape.bonds.domain.model.aggregates.BondResult;
import nrg.inc.koutape.bonds.domain.model.aggregates.CashFlow;
import nrg.inc.koutape.bonds.domain.model.queries.GetAllBondsQuery;
import nrg.inc.koutape.bonds.domain.model.queries.GetBondByIdQuery;
import nrg.inc.koutape.bonds.domain.model.queries.GetBondResultByBondIdQuery;
import nrg.inc.koutape.bonds.domain.model.queries.GetCashFlowsByBondIdQuery;

import java.util.List;
import java.util.Optional;

public interface BondQueryService {
    Optional<Bond> handle(GetBondByIdQuery query);
    List<Bond> handle(GetAllBondsQuery query);
    List<CashFlow> handle(GetCashFlowsByBondIdQuery query);
    Optional<BondResult> handle(GetBondResultByBondIdQuery query);
    //List<BondHolder> handle(GetBondHoldersByBondIdQuery query);
}
