package nrg.inc.koutape.bonds.interfaces.rest.resources;

import nrg.inc.koutape.bonds.domain.model.valueobjects.Capitalization;
import nrg.inc.koutape.bonds.domain.model.valueobjects.CuponFrequency;
import nrg.inc.koutape.bonds.domain.model.valueobjects.InterestRateType;

import java.util.Date;

public record CreateBondResource(
        String name,
        Double nominalValue,
        Double comercialValue,
        Integer years,
        CuponFrequency cuponFrequency,
        Integer daysPerYear,
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
