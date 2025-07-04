package nrg.inc.koutape.bonds.application.internal.queryservices;

import nrg.inc.koutape.bonds.domain.model.aggregates.Bond;
import nrg.inc.koutape.bonds.domain.model.aggregates.BondHolder;
import nrg.inc.koutape.bonds.domain.model.aggregates.CashFlow;
import nrg.inc.koutape.bonds.domain.model.queries.GetAllBondsQuery;
import nrg.inc.koutape.bonds.domain.model.queries.GetBondByIdQuery;
import nrg.inc.koutape.bonds.domain.model.queries.GetBondHoldersByBondIdQuery;
import nrg.inc.koutape.bonds.domain.model.queries.GetCashFlowsByBondIdQuery;
import nrg.inc.koutape.bonds.domain.services.BondQueryService;
import nrg.inc.koutape.bonds.infrastructure.persistence.jpa.repositories.BondRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BondQueryServiceImpl implements BondQueryService {
    private final BondRepository bondRepository;

    public BondQueryServiceImpl(BondRepository bondRepository) {
        this.bondRepository = bondRepository;
    }

    @Override
    public Optional<Bond> handle(GetBondByIdQuery query) {
        return this.bondRepository.findById(query.bondId());
    }

    @Override
    public List<Bond> handle(GetAllBondsQuery query) {
        return this.bondRepository.findAll();
    }

    @Override
    public List<CashFlow> handle(GetCashFlowsByBondIdQuery query) {
        var bond = this.bondRepository.findById(query.bondId());
        return bond.get().getCashFlows();
    }

    @Override
    public List<BondHolder> handle(GetBondHoldersByBondIdQuery query) {
        var bond = this.bondRepository.findById(query.bondId());
        return bond.get().getBondholders();
    }
}
