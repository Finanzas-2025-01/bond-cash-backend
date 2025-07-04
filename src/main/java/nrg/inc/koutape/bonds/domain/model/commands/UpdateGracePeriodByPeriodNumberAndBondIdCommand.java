package nrg.inc.koutape.bonds.domain.model.commands;

import nrg.inc.koutape.bonds.domain.model.valueobjects.GracePeriod;

public record UpdateGracePeriodByPeriodNumberAndBondIdCommand(Long bondId, Integer periodNumber, GracePeriod gracePeriod) {
}
