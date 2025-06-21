package nrg.inc.koutape.bonds.domain.model.commands;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import nrg.inc.koutape.bonds.domain.model.valueobjects.Capitalization;
import nrg.inc.koutape.bonds.domain.model.valueobjects.CuponFrequency;
import nrg.inc.koutape.bonds.domain.model.valueobjects.InterestRateType;

import java.util.Date;

public record CreateBondCommand(
       Long bondHolderId,
       String name,
       Double nominalValue,
       Double comercialValue,
       Integer years,
       CuponFrequency cuponFrequency,
       InterestRateType interestRateType,
       Capitalization capitalization,
       Double interestRatePercentage,
       Double anualDiscountRatePercentage,
       Double incomeTaxPercentage,
       Date issueDate,
       Double premiumPercentage,
       Double structuringPercentage,
       Double placementPercentage,
       Double floatingRatePercentage,
       Double CAVALIPercentage,
       Double anualInflationPercentage
) {
}
