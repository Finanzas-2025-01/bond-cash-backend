package nrg.inc.koutape.bonds.domain.model.aggregates;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import nrg.inc.koutape.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

@Entity
public class BondResult extends AuditableAbstractAggregateRoot<BondResult> {

    @OneToOne
    @JoinColumn(name = "bond_id")
    private Bond bond;

    private Double convexity;

    private Double duration;

    private Double modifiedDuration;

    private Double percentageTCEA;

    private Double percentageTREA;
}
