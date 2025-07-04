package nrg.inc.koutape.iam.interfaces.rest.transform;

import nrg.inc.koutape.iam.domain.model.aggregates.User;
import nrg.inc.koutape.iam.domain.model.entities.Role;
import nrg.inc.koutape.iam.interfaces.rest.resources.AuthenticatedUserResource;

public class AuthenticatedUserResourceFromEntityAssembler {

  public static AuthenticatedUserResource toResourceFromEntity(User user, String token) {
    var roles = user.getRoles().stream()
            .map(Role::getStringName)
            .toList();
    return new AuthenticatedUserResource(
            user.getId(),
            user.getUsername(),
            token,
            roles
    );
  }
}
