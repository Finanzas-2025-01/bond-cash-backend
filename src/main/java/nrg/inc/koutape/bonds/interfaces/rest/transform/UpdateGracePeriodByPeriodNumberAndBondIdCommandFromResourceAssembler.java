package nrg.inc.koutape.bonds.interfaces.rest.transform;

import nrg.inc.koutape.bonds.domain.model.commands.UpdateGracePeriodByPeriodNumberAndBondIdCommand;
import nrg.inc.koutape.bonds.domain.model.valueobjects.GracePeriod;

public class UpdateGracePeriodByPeriodNumberAndBondIdCommandFromResourceAssembler {
    public static UpdateGracePeriodByPeriodNumberAndBondIdCommand toCommandFromResource(
            Long bondId, Integer periodNumber, GracePeriod gracePeriod) {
        return new UpdateGracePeriodByPeriodNumberAndBondIdCommand(
                bondId,
                periodNumber,
                gracePeriod
        );
    }
}
