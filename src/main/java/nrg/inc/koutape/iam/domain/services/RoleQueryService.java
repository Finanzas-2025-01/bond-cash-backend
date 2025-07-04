package nrg.inc.koutape.iam.domain.services;



import nrg.inc.koutape.iam.domain.model.entities.Role;
import nrg.inc.koutape.iam.domain.model.queries.GetAllRolesQuery;
import nrg.inc.koutape.iam.domain.model.queries.GetRoleByNameQuery;

import java.util.List;
import java.util.Optional;

public interface RoleQueryService {
  List<Role> handle(GetAllRolesQuery query);
  Optional<Role> handle(GetRoleByNameQuery query);
}
