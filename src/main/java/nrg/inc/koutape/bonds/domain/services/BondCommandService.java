package nrg.inc.koutape.bonds.domain.services;

import nrg.inc.koutape.bonds.domain.model.aggregates.Bond;
import nrg.inc.koutape.bonds.domain.model.commands.*;

import java.util.Optional;

public interface BondCommandService {
    Optional<Bond> handle(HireBondCommand command);
    Optional<Bond> handle(CreateBondCommand command);
    void handle(GenerateCashFlowsByBondIdCommand command);
    void handle(UpdateGracePeriodByPeriodNumberAndBondIdCommand command);
    void handle(UpdateBondCommand command);
}
