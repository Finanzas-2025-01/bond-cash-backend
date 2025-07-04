package nrg.inc.koutape.iam.interfaces.acl;

import nrg.inc.koutape.iam.domain.model.commands.SignUpCommand;
import nrg.inc.koutape.iam.domain.model.entities.Role;
import nrg.inc.koutape.iam.domain.model.queries.GetUserByIdQuery;
import nrg.inc.koutape.iam.domain.model.queries.GetUserByUsernameQuery;
import nrg.inc.koutape.iam.domain.services.UserCommandService;
import nrg.inc.koutape.iam.domain.services.UserQueryService;
import org.apache.logging.log4j.util.Strings;
import java.util.ArrayList;
import java.util.List;

/**
 * IamContextFacade
 * <p>
 *     This class is a facade for the IAM context. It provides a simple interface for other
 *     bounded contexts to interact with the
 *     IAM context.
 *     This class is a part of the ACL layer.
 * </p>
 *
 */
public class IamContextFacade {

  private final UserQueryService userQueryService;

  public IamContextFacade(UserQueryService userQueryService) {
    this.userQueryService = userQueryService;
  }
  /**
   * Fetches the id of the user with the given username.
   * @param username The username of the user.
   * @return The id of the user.
   */
  public Long fetchUserIdByUsername(String username) {
    var getUserByUsernameQuery = new GetUserByUsernameQuery(username);
    var result = userQueryService.handle(getUserByUsernameQuery);
    if (result.isEmpty())
      return 0L;
    return result.get().getId();
  }

  /**
   * Fetches the username of the user with the given id.
   * @param userId The id of the user.
   * @return The username of the user.
   */
  public String fetchUsernameByUserId(Long userId) {
    var getUserByIdQuery = new GetUserByIdQuery(userId);
    var result = userQueryService.handle(getUserByIdQuery);
    if (result.isEmpty())
      return Strings.EMPTY;
    return result.get().getUsername();
  }
}
