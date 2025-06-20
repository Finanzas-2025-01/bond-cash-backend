package nrg.inc.koutape.bonds.domain.services;

import nrg.inc.koutape.bonds.domain.model.aggregates.Investor;
import nrg.inc.koutape.bonds.domain.model.commands.CreateInvestorCommand;

import java.util.Optional;

public interface InvestorCommandService {
    Optional<Investor> handle(CreateInvestorCommand createInvestorCommand);
}
