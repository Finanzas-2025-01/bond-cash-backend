package nrg.inc.koutape.bonds.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import nrg.inc.koutape.bonds.domain.model.commands.CreateBondHolderCommand;
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
public class BondHolder extends AuditableAbstractAggregateRoot<BondHolder> {
    @ManyToMany
    @JoinTable(
            name = "bond_holder_bonds",
            joinColumns = @JoinColumn(name = "bond_holder_id"),
            inverseJoinColumns = @JoinColumn(name = "bond_id")
    )
    private List<Bond> bonds;

    @OneToOne(mappedBy = "bondHolder")
    private User user;

    public BondHolder(CreateBondHolderCommand command) {
        // Initialize the investor with the command data
    }

    public BondHolder() {

    }
}
