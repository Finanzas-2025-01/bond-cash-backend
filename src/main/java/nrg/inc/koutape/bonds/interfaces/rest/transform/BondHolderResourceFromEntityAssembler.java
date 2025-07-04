package nrg.inc.koutape.bonds.interfaces.rest.transform;

import nrg.inc.koutape.bonds.domain.model.aggregates.BondHolder;
import nrg.inc.koutape.bonds.interfaces.rest.resources.BondHolderResource;

public class BondHolderResourceFromEntityAssembler {
    public static BondHolderResource toResourceFromEntity(BondHolder entity) {
        return new BondHolderResource(
                entity.getId(),
                entity.getUser().getUsername(),
                entity.getUser().getName(),
                entity.getUser().getSurname(),
                entity.getUser().getEmail()
        );
    }
}
