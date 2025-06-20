package nrg.inc.koutape.bonds.domain.model.aggregates;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;
import nrg.inc.koutape.bonds.domain.model.commands.CreateBondHolderCommand;
import nrg.inc.koutape.iam.domain.model.aggregates.User;
import nrg.inc.koutape.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.util.List;

@Entity
@Setter
@Getter
public class BondHolder extends AuditableAbstractAggregateRoot<BondHolder> {

    @OneToMany(mappedBy = "bondHolder")
    private List<Bond> bonds;

    @OneToOne(mappedBy = "bondHolder")
    private User user;

    public BondHolder(CreateBondHolderCommand command) {
        // Initialize the BondHolder with the command data
    }

    public BondHolder() {

    }
}
