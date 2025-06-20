package nrg.inc.koutape.bonds.application.internal.commandservices;

import nrg.inc.koutape.bonds.domain.model.aggregates.Investor;
import nrg.inc.koutape.bonds.domain.model.commands.CreateInvestorCommand;
import nrg.inc.koutape.bonds.domain.services.InvestorCommandService;
import nrg.inc.koutape.bonds.infrastructure.persistence.jpa.repositories.InvestorRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InvestorCommandServiceImpl implements InvestorCommandService {

    private final InvestorRepository investorRepository;

    public InvestorCommandServiceImpl(InvestorRepository investorRepository) {
        this.investorRepository = investorRepository;
    }

    @Override
    public Optional<Investor> handle(CreateInvestorCommand command) {
        var investor = new Investor(command);
        var createdInvestor = investorRepository.save(investor);
        return Optional.of(createdInvestor);
    }
}
