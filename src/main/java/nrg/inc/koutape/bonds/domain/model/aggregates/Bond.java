package nrg.inc.koutape.bonds.domain.model.aggregates;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import nrg.inc.koutape.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.util.List;

@Entity
public class Bond extends AuditableAbstractAggregateRoot<Bond> {

    @ManyToOne
    @JoinColumn(name = "bond_holder_id")
    private BondHolder bondHolder;

    @ManyToMany(mappedBy = "bonds")
    private List<Investor> investors;

    public Bond() {
        // Default constructor
    }

    // Additional methods can be added here
}
