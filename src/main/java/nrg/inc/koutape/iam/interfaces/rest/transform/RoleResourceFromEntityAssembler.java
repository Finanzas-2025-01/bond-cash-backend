package nrg.inc.koutape.iam.interfaces.rest.transform;


import nrg.inc.koutape.iam.domain.model.entities.Role;
import nrg.inc.koutape.iam.interfaces.rest.resources.RoleResource;

public class RoleResourceFromEntityAssembler {

  public static RoleResource toResourceFromEntity(Role role) {
    return new RoleResource(
            role.getId(),
            role.getStringName()
    );
  }
}
