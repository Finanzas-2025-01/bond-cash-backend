package nrg.inc.koutape.bonds.interfaces.rest.resources;

public record UpdateBondResource(
        Double comercialValue,
        Double interestRatePercentage,
        Double anualDiscountRatePercentage,
        Double premiumPercentage,
        Double structuringPercentage,
        Double placementPercentage,
        Double floatingRatePercentage,
        Double CAVALIPercentage
) {
}
