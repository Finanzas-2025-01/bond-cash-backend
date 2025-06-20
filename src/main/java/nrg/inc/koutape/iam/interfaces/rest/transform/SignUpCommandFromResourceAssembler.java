package nrg.inc.koutape.iam.interfaces.rest.transform;

import nrg.inc.koutape.iam.domain.model.commands.SignUpCommand;
import nrg.inc.koutape.iam.domain.model.entities.Role;
import nrg.inc.koutape.iam.interfaces.rest.resources.SignUpResource;

import java.util.ArrayList;

public class SignUpCommandFromResourceAssembler {

  public static SignUpCommand toCommandFromResource(SignUpResource resource) {
    var roles = resource.roles() != null
        ? resource.roles().stream().map(name -> Role.toRoleFromName(name)).toList()
        : new ArrayList<Role>();
    return new SignUpCommand(
            resource.username(),
            resource.name(),
            resource.surname(),
            resource.email(),
            resource.password(),
            roles);
  }
}
