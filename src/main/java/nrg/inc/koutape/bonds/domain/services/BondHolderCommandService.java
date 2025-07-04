package nrg.inc.koutape.bonds.domain.services;

import nrg.inc.koutape.bonds.domain.model.aggregates.BondHolder;
import nrg.inc.koutape.bonds.domain.model.commands.CreateBondHolderCommand;
import nrg.inc.koutape.bonds.domain.model.queries.GetBondHolderByUsernameQuery;

import java.util.Optional;

public interface BondHolderCommandService {
    Optional<BondHolder> handle(CreateBondHolderCommand command);
}
