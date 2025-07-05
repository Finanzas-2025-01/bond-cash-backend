package nrg.inc.koutape.bonds.application.internal.queryservices;

import nrg.inc.koutape.bonds.domain.model.aggregates.Bond;
import nrg.inc.koutape.bonds.domain.model.aggregates.Issuer;
import nrg.inc.koutape.bonds.domain.model.queries.GetAllBondsByIssuerIdQuery;
import nrg.inc.koutape.bonds.domain.model.queries.GetAllHiredBondsByIssuerIdQuery;
import nrg.inc.koutape.bonds.domain.model.queries.GetIssuerByUsernameQuery;
import nrg.inc.koutape.bonds.domain.model.valueobjects.BondType;
import nrg.inc.koutape.bonds.domain.services.IssuerQueryService;
import nrg.inc.koutape.bonds.infrastructure.persistence.jpa.repositories.IssuerRepository;
import nrg.inc.koutape.iam.domain.model.queries.GetUserByUsernameQuery;
import nrg.inc.koutape.iam.domain.services.UserQueryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IssuerQueryServiceImpl implements IssuerQueryService {

    private final UserQueryService userQueryService;
    private final IssuerRepository issuerRepository;

    public IssuerQueryServiceImpl(UserQueryService userQueryService, IssuerRepository issuerRepository) {
        this.userQueryService = userQueryService;
        this.issuerRepository = issuerRepository;
    }

    @Override
    public Optional<Issuer> handle(GetIssuerByUsernameQuery query) {
        var getUserByUsernameQuery = new GetUserByUsernameQuery(query.username());
        var user = userQueryService.handle(getUserByUsernameQuery);
        var role = user.get().getRoles().stream().findFirst().get().getName().toString();
        if (!role.equals("ROLE_ISSUER")) {
            return Optional.empty();
        }
        var issuer = user.get().getIssuer();
        if (issuer != null) {
            return Optional.of(issuer);
        }
        return Optional.empty();
    }

    @Override
    public List<Bond> handle(GetAllBondsByIssuerIdQuery query) {
        var issuer = issuerRepository.findById(query.issuerId());
        if (issuer.isPresent()) {
            var bonds = issuer.get().getBonds();
            bonds.removeIf(bond -> bond.getBondType() != BondType.BASE);
            return issuer.get().getBonds();
        }
        return List.of();
    }

    @Override
    public List<Bond> handle(GetAllHiredBondsByIssuerIdQuery query) {
        var issuer = issuerRepository.findById(query.issuerId());
        if (issuer.isPresent()) {
            var bonds = issuer.get().getBonds();
            bonds.removeIf(bond -> bond.getBondType() != BondType.HIRED);
            return issuer.get().getBonds();
        }
        return List.of();
    }
}
