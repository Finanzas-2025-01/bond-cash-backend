package nrg.inc.koutape.iam.interfaces.rest.transform;

import nrg.inc.koutape.iam.domain.model.commands.CreateUserIssuerCommand;

public class CreateUserIssuerCommandFromResourceAssembler {
    public static CreateUserIssuerCommand toCommandFromResource(Long userId) {
        return new CreateUserIssuerCommand(userId);
    }
}
