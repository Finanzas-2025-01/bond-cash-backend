package nrg.inc.koutape.iam.interfaces.rest.transform;

import nrg.inc.koutape.iam.domain.model.aggregates.User;
import nrg.inc.koutape.iam.domain.model.entities.Role;
import nrg.inc.koutape.iam.interfaces.rest.resources.UserResource;

public class UserResourceFromEntityAssembler {

  public static UserResource toResourceFromEntity(User user) {
    var roles = user.getRoles().stream()
        .map(Role::getStringName)
        .toList();
    return new UserResource(user.getId(), user.getUsername(), roles);
  }
}
