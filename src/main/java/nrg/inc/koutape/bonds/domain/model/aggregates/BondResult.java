package nrg.inc.koutape.bonds.domain.model.aggregates;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Setter;
import nrg.inc.koutape.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

@Entity
@Setter
public class BondResult extends AuditableAbstractAggregateRoot<BondResult> {

    @OneToOne
    @JoinColumn(name = "bond_id")
    private Bond bond;

    private Double duration;

    private Double convexity;

    private Double modifiedDuration;

    private Double percentageTCEA;

    private Double percentageTREA;

    public BondResult() {
        this.convexity = 0.0;
        this.duration = 0.0;
        this.modifiedDuration = 0.0;
        this.percentageTCEA = 0.0;
        this.percentageTREA = 0.0;
    }
}
