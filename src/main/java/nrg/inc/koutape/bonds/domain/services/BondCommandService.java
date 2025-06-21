package nrg.inc.koutape.bonds.domain.services;

import nrg.inc.koutape.bonds.domain.model.aggregates.Bond;
import nrg.inc.koutape.bonds.domain.model.commands.CreateBondCommand;
import nrg.inc.koutape.bonds.domain.model.commands.HireBondCommand;

import java.util.Optional;

public interface BondCommandService {
    Optional<Bond> handle(HireBondCommand hireBondCommand);
    Optional<Bond> handle(CreateBondCommand createBondCommand);
}
