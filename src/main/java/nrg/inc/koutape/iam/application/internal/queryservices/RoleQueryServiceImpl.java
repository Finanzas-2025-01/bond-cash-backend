package nrg.inc.koutape.iam.application.internal.queryservices;

import nrg.inc.koutape.iam.domain.model.entities.Role;
import nrg.inc.koutape.iam.domain.model.queries.GetAllRolesQuery;
import nrg.inc.koutape.iam.domain.model.queries.GetRoleByNameQuery;
import nrg.inc.koutape.iam.domain.services.RoleQueryService;
import nrg.inc.koutape.iam.infrastructure.persistence.jpa.repositories.RoleRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * RoleQueryServiceImpl class
 * This class is used to handle the role queries
 */
@Service
public class RoleQueryServiceImpl implements RoleQueryService {
  private final RoleRepository roleRepository;

  /**
   * RoleQueryServiceImpl constructor
   * @param roleRepository the role repository
   */
  public RoleQueryServiceImpl(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  /**
   * Handle the get all roles query
   * @param query the get all roles query
   * @return List<Role> the list of roles
   */
  @Override
  public List<Role> handle(GetAllRolesQuery query) {
    return roleRepository.findAll();
  }

  /**
   * Handle the get role by name query
   * @param query the get role by name query
   * @return Optional<Role> the role
   */
  @Override
  public Optional<Role> handle(GetRoleByNameQuery query) {
    return roleRepository.findByName(query.name());
  }
}
