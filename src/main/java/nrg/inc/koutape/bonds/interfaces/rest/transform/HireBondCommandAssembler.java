package nrg.inc.koutape.bonds.interfaces.rest.transform;

import nrg.inc.koutape.bonds.domain.model.aggregates.Bond;
import nrg.inc.koutape.bonds.domain.model.commands.HireBondCommand;

public class HireBondCommandAssembler {
    public static HireBondCommand toCommand(Long bondId, Long bondHolderId) {
        return new HireBondCommand(bondId, bondHolderId);
    }
}
