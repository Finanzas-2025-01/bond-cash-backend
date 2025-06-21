package nrg.inc.koutape.bonds.interfaces.rest.transform;

import nrg.inc.koutape.bonds.domain.model.commands.CreateBondCommand;
import nrg.inc.koutape.bonds.domain.model.valueobjects.Capitalization;
import nrg.inc.koutape.bonds.domain.model.valueobjects.CuponFrequency;
import nrg.inc.koutape.bonds.domain.model.valueobjects.InterestRateType;
import nrg.inc.koutape.bonds.interfaces.rest.resources.CreateBondResource;

import java.util.Date;

public class CreateBondCommandFromResourceAssembler {
    public static CreateBondCommand toCommandFromResource(CreateBondResource command, Long bondId) {
        return new CreateBondCommand(
                bondId,
                command.name(),
                command.nominalValue(),
                command.comercialValue(),
                command.years(),
                command.cuponFrequency(),
                command.interestRateType(),
                command.capitalization(),
                command.interestRatePercentage(),
                command.anualDiscountRatePercentage(),
                command.incomeTaxPercentage(),
                command.issueDate(),
                command.premiumPercentage(),
                command.structuringPercentage(),
                command.placementPercentage(),
                command.floatingRatePercentage(),
                command.CAVALIPercentage(),
                command.anualInflationPercentage()
        );
    }
}
