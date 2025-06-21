package nrg.inc.koutape.bonds.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import nrg.inc.koutape.bonds.domain.model.commands.CreateInvestorCommand;
import nrg.inc.koutape.iam.domain.model.aggregates.User;
import nrg.inc.koutape.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.util.List;

/**
 * Represents an investor in the bond system. Este es el bonista.
 * An investor can hold multiple bonds and is associated with a user.
 */
@Entity
@Setter
@Getter
public class Investor extends AuditableAbstractAggregateRoot<Investor> {
    @ManyToMany
    @JoinTable(
            name = "investor_bonds",
            joinColumns = @JoinColumn(name = "investor_id"),
            inverseJoinColumns = @JoinColumn(name = "bond_id")
    )
    private List<Bond> bonds;

    @OneToOne(mappedBy = "investor")
    private User user;

    public Investor(CreateInvestorCommand command) {
        // Initialize the investor with the command data
    }

    public Investor() {

    }
}
