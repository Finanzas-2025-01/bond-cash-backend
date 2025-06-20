package nrg.inc.koutape.iam.interfaces.rest.transform;

import nrg.inc.koutape.iam.domain.model.commands.CreateUserInvestorCommand;

public class CreateUserInvestorCommandFromResourceAssembler {
    public static CreateUserInvestorCommand toCommandFromResource(Long userId) {
        return new CreateUserInvestorCommand(userId);
    }
}
