package nrg.inc.koutape.bonds.infrastructure.persistence.jpa.repositories;

import nrg.inc.koutape.bonds.domain.model.aggregates.BondResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BondResultRepository extends JpaRepository<BondResult, Long> {
    Optional<BondResult> findByBondId(Long bondId);
}
