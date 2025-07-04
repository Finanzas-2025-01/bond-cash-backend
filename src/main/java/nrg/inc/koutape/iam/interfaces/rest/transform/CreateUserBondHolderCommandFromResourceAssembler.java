package nrg.inc.koutape.iam.interfaces.rest.transform;

import nrg.inc.koutape.iam.domain.model.commands.CreateUserBondHolderCommand;

public class CreateUserBondHolderCommandFromResourceAssembler {
    public static CreateUserBondHolderCommand toCommandFromResource(Long userId) {
        return new CreateUserBondHolderCommand(userId);
    }
}
