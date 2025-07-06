package nrg.inc.koutape.bonds.interfaces.rest.resources;

import nrg.inc.koutape.bonds.domain.model.aggregates.Bond;

public record BondResultResource(
        Long id,
        Long bondId,
        Double duration,
        Double convexity,
        Double modifiedDuration,
        Double percentageTCEA,
        Double percentageTREA
) {
}
