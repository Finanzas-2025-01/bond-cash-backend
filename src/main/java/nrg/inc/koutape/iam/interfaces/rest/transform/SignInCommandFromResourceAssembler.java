package nrg.inc.koutape.iam.interfaces.rest.transform;


import nrg.inc.koutape.iam.domain.model.commands.SignInCommand;
import nrg.inc.koutape.iam.interfaces.rest.resources.SignInResource;

public class SignInCommandFromResourceAssembler {

  public static SignInCommand toCommandFromResource(SignInResource signInResource) {
    return new SignInCommand(signInResource.username(), signInResource.password());
  }
}
