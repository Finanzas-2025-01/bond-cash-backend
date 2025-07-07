package nrg.inc.koutape.bonds.interfaces.rest.transform;

import nrg.inc.koutape.bonds.domain.model.aggregates.BondResult;
import nrg.inc.koutape.bonds.interfaces.rest.resources.BondResultResource;

public class BondResultResourceFromEntityAssembler {
    public static BondResultResource toResourceFromEntity(BondResult bondResult) {
        return new BondResultResource(
                bondResult.getId(),
                bondResult.getBond().getId(),
                bondResult.getDuration(),
                bondResult.getConvexity(),
                bondResult.getModifiedDuration(),
                bondResult.getPercentageTCEA(),
                bondResult.getPercentageTREA()
        );
    }
}
