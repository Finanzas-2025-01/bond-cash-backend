package nrg.inc.koutape.bonds.interfaces.rest.transform;

import nrg.inc.koutape.bonds.domain.model.aggregates.Bond;
import nrg.inc.koutape.bonds.domain.model.aggregates.BondHolder;
import nrg.inc.koutape.bonds.interfaces.rest.resources.HiredBondResource;

public class HiredBondResourceFromEntityAssembler {
    public static HiredBondResource toResourceFromEntity(Bond bond, BondHolder bondHolder) {
        return new HiredBondResource(
                bond.getId(),
                bond.getName(),
                bond.getNominalValue(),
                bond.getComercialValue(),
                bond.getYears(),
                bond.getCuponFrequency(),
                bond.getInterestRateType(),
                bond.getCapitalization(),
                bond.getInterestRatePercentage(),
                bond.getAnualDiscountRatePercentage(),
                bond.getIncomeTaxPercentage(),
                bond.getIssueDate(),
                bond.getPremiumPercentage(),
                bond.getStructuringPercentage(),
                bond.getPlacementPercentage(),
                bond.getFloatingRatePercentage(),
                bond.getCAVALIPercentage(),
                bond.getAnualInflationPercentage(),
                IssuerResourceFromEntityAssembler.toResourceFromEntity(bond.getIssuer()),
                BondHolderResourceFromEntityAssembler.toResourceFromEntity(bondHolder)
        );
    }
}
