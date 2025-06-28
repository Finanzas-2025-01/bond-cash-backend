package nrg.inc.koutape.bonds.interfaces.rest.transform;

import nrg.inc.koutape.bonds.domain.model.aggregates.Bond;
import nrg.inc.koutape.bonds.domain.model.commands.UpdateBondCommand;
import nrg.inc.koutape.bonds.interfaces.rest.resources.UpdateBondResource;

public class UpdateBondCommandFromResourceAssembler {
    public static UpdateBondCommand toCommandFromResource(UpdateBondResource resource, Long bondId) {
        return new UpdateBondCommand(
                bondId,
                resource.comercialValue(),
                resource.interestRatePercentage(),
                resource.anualDiscountRatePercentage(),
                resource.premiumPercentage(),
                resource.structuringPercentage(),
                resource.placementPercentage(),
                resource.floatingRatePercentage(),
                resource.CAVALIPercentage()
        );
    }
}
