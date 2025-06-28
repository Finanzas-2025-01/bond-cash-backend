package nrg.inc.koutape.bonds.domain.model.commands;

public record UpdateBondCommand(
        Long bondId,
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
