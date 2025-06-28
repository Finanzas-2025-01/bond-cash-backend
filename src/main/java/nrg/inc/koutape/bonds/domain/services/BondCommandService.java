package nrg.inc.koutape.bonds.domain.services;

import nrg.inc.koutape.bonds.domain.model.aggregates.Bond;
import nrg.inc.koutape.bonds.domain.model.commands.CreateBondCommand;
import nrg.inc.koutape.bonds.domain.model.commands.GenerateCashFlowsByBondIdCommand;
import nrg.inc.koutape.bonds.domain.model.commands.HireBondCommand;
import nrg.inc.koutape.bonds.domain.model.commands.UpdateGracePeriodByPeriodNumberAndBondIdCommand;

import java.util.Optional;

public interface BondCommandService {
    Optional<Bond> handle(HireBondCommand command);
    Optional<Bond> handle(CreateBondCommand command);
    void handle(GenerateCashFlowsByBondIdCommand command);
    void handle(UpdateGracePeriodByPeriodNumberAndBondIdCommand command);
}
