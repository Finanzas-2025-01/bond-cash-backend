package nrg.inc.koutape.bonds.domain.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import nrg.inc.koutape.bonds.domain.model.aggregates.Bond;
import nrg.inc.koutape.bonds.domain.model.valueobjects.GracePeriod;
import nrg.inc.koutape.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

@Entity
@Getter
@Setter
public class CashFlowGracePeriod extends AuditableAbstractAggregateRoot<CashFlowGracePeriod> {
    @ManyToOne
    private Bond bond;
    private Integer periodNumber = 0;
    private GracePeriod gracePeriod;

    public CashFlowGracePeriod() {
        // Default constructor for JPA
    }

}
