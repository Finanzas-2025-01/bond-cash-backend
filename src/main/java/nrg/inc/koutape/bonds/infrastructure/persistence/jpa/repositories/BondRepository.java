package nrg.inc.koutape.bonds.infrastructure.persistence.jpa.repositories;

import nrg.inc.koutape.bonds.domain.model.aggregates.Bond;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BondRepository extends JpaRepository<Bond, Long> {
}
