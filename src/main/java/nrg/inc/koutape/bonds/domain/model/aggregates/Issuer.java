package nrg.inc.koutape.bonds.domain.model.aggregates;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import nrg.inc.koutape.bonds.domain.model.commands.CreateIssuerCommand;
import nrg.inc.koutape.iam.domain.model.aggregates.User;
import nrg.inc.koutape.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.util.List;

/**
 * Represents an issuer in the bond system. Este es el emisor de bonos.
 * An issuer emit multiple bonds and is associated with a user.
 */
@Entity
@Setter
@Getter
public class Issuer extends AuditableAbstractAggregateRoot<Issuer> {

    @OneToMany(mappedBy = "issuer")
    private List<Bond> bonds;

    @OneToOne(mappedBy = "issuer")
    private User user;

    public Issuer(CreateIssuerCommand command) {
        // Initialize the BondHolder with the command data
    }

    public Issuer() {

    }

    public void addBond(Bond bond) {
        this.bonds.add(bond);
    }
}
