package nrg.inc.koutape.bonds.domain.model.commands;

public record HireBondCommand(
        Long bonId,
        Long bondHolderId
) {
}
