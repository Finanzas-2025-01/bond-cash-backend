package nrg.inc.koutape.iam.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import nrg.inc.koutape.iam.domain.services.UserCommandService;
import nrg.inc.koutape.iam.interfaces.rest.resources.AuthenticatedUserResource;
import nrg.inc.koutape.iam.interfaces.rest.resources.SignInResource;
import nrg.inc.koutape.iam.interfaces.rest.resources.SignUpResource;
import nrg.inc.koutape.iam.interfaces.rest.resources.UserResource;
import nrg.inc.koutape.iam.interfaces.rest.transform.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AuthenticationController
 * <p>
 *     This controller is responsible for handling authentication requests.
 *     It exposes two endpoints:
 *     <ul>
 *         <li>POST /api/v1/auth/sign-in</li>
 *         <li>POST /api/v1/auth/sign-up</li>
 *     </ul>
 * </p>
 */
@RestController
@RequestMapping(value = "/api/v1/authentication", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Authentication", description = "Authentication Endpoints")
public class AuthenticationController {

  private final UserCommandService userCommandService;

  public AuthenticationController(UserCommandService userCommandService) {
    this.userCommandService = userCommandService;
  }

  /**
   * Handles the sign-in request.
   * @param signInResource the sign-in request body.
   * @return the authenticated user resource.
   */
  @PostMapping("/sign-in")
  public ResponseEntity<AuthenticatedUserResource> signIn(
      @RequestBody SignInResource signInResource) {

    var signInCommand = SignInCommandFromResourceAssembler
        .toCommandFromResource(signInResource);
    var authenticatedUser = userCommandService.handle(signInCommand);
    if (authenticatedUser.isEmpty()) {
      return ResponseEntity.notFound().build();
    }

    var authenticatedUserResource = AuthenticatedUserResourceFromEntityAssembler
        .toResourceFromEntity(
            authenticatedUser.get().getLeft(), authenticatedUser.get().getRight());
    return ResponseEntity.ok(authenticatedUserResource);
  }

  /**
   * Handles the sign-up request.
   * @param signUpResource the sign-up request body.
   * @return the created user resource.
   */
  @PostMapping("/sign-up")
  public ResponseEntity<UserResource> signUp(@RequestBody SignUpResource signUpResource) {
    var signUpCommand = SignUpCommandFromResourceAssembler
        .toCommandFromResource(signUpResource);
    var user = userCommandService.handle(signUpCommand);
    if (user.isEmpty()) {
      return ResponseEntity.badRequest().build();
    }

    var role = user.get().getRoles().stream().findFirst().get().getName().toString();

    if (role.equals("ROLE_BONDHOLDER")) {
      var createUserInvestorCommand = CreateUserBondHolderCommandFromResourceAssembler.toCommandFromResource(user.get().getId());
      user = userCommandService.handle(createUserInvestorCommand);
      if (user.isEmpty()) {
        return ResponseEntity.badRequest().build();
      }
    } else if (role.equals("ROLE_ISSUER")) {
      var createUserBondHolderCommand = CreateUserIssuerCommandFromResourceAssembler.toCommandFromResource(user.get().getId());
        user = userCommandService.handle(createUserBondHolderCommand);
        if (user.isEmpty()) {
          return ResponseEntity.badRequest().build();
        }
    }
    var userResource = UserResourceFromEntityAssembler.toResourceFromEntity(user.get());
    return new ResponseEntity<>(userResource, HttpStatus.CREATED);
  }
}
