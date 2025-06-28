package nrg.inc.koutape.bonds.infrastructure.persistence.jpa.repositories;

import nrg.inc.koutape.bonds.domain.model.aggregates.Bond;
import nrg.inc.koutape.bonds.domain.model.entities.CashFlowGracePeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CashFlowGracePeriodRepository extends JpaRepository<CashFlowGracePeriod, Long> {
    Optional<CashFlowGracePeriod> findByBondAndPeriodNumber(Bond bond, Integer periodNumber);
}
