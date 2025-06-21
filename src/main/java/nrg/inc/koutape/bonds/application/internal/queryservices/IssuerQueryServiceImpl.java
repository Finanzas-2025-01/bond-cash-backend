package nrg.inc.koutape.bonds.application.internal.queryservices;

import nrg.inc.koutape.bonds.domain.model.aggregates.Issuer;
import nrg.inc.koutape.bonds.domain.model.queries.GetIssuerByUsernameQuery;
import nrg.inc.koutape.bonds.domain.services.IssuerQueryService;
import nrg.inc.koutape.iam.domain.model.queries.GetUserByUsernameQuery;
import nrg.inc.koutape.iam.domain.services.UserQueryService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IssuerQueryServiceImpl implements IssuerQueryService {

    private final UserQueryService userQueryService;

    public IssuerQueryServiceImpl(UserQueryService userQueryService) {
        this.userQueryService = userQueryService;
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
}
