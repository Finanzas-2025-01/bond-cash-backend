package nrg.inc.koutape.bonds.interfaces.rest.transform;

import nrg.inc.koutape.bonds.domain.model.aggregates.Bond;
import nrg.inc.koutape.bonds.domain.model.valueobjects.Capitalization;
import nrg.inc.koutape.bonds.domain.model.valueobjects.CuponFrequency;
import nrg.inc.koutape.bonds.domain.model.valueobjects.InterestRateType;
import nrg.inc.koutape.bonds.interfaces.rest.resources.BondResource;
import nrg.inc.koutape.bonds.interfaces.rest.resources.IssuerResource;

import java.util.Date;

public class BondResourceFromEntityAssembler {
    public static BondResource toResourceFromEntity(Bond bond) {
        return new BondResource(
                bond.getId(),
                bond.getName(),
                bond.getNominalValue(),
                bond.getComercialValue(),
                bond.getYears(),
                bond.getCuponFrequency(),
                bond.getDaysPerYear(),
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
                IssuerResourceFromEntityAssembler.toResourceFromEntity(bond.getIssuer())
        );
    }
}
