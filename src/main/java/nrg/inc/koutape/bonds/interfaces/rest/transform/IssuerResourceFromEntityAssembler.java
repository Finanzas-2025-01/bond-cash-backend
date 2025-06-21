package nrg.inc.koutape.bonds.interfaces.rest.transform;

import nrg.inc.koutape.bonds.domain.model.aggregates.Issuer;
import nrg.inc.koutape.bonds.interfaces.rest.resources.IssuerResource;

public class IssuerResourceFromEntityAssembler {
    public static IssuerResource toResourceFromEntity(Issuer entity) {
        return new IssuerResource(
                entity.getId(),
                entity.getUser().getUsername(),
                entity.getUser().getName(),
                entity.getUser().getSurname(),
                entity.getUser().getEmail()
        );
    }
}
