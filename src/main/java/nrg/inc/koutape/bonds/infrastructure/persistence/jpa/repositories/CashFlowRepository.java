package nrg.inc.koutape.bonds.infrastructure.persistence.jpa.repositories;

import nrg.inc.koutape.bonds.domain.model.aggregates.CashFlow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CashFlowRepository extends JpaRepository<CashFlow, Long> {
    List<CashFlow> findByBondId(Long bondId);
}
