package nrg.inc.koutape.bonds.application.internal.queryservices;

import nrg.inc.koutape.bonds.domain.model.aggregates.BondHolder;
import nrg.inc.koutape.bonds.domain.model.queries.GetBondHolderByUsernameQuery;
import nrg.inc.koutape.bonds.domain.services.BondHolderQueryService;
import nrg.inc.koutape.iam.domain.model.queries.GetUserByUsernameQuery;
import nrg.inc.koutape.iam.domain.services.UserQueryService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BondHolderQueryServiceImpl implements BondHolderQueryService {
    private final UserQueryService userQueryService;

    public BondHolderQueryServiceImpl(UserQueryService userQueryService) {
        this.userQueryService = userQueryService;
    }

    @Override
    public Optional<BondHolder> handle(GetBondHolderByUsernameQuery query) {
        var getUserByUsernameQuery = new GetUserByUsernameQuery(query.username());
        var user = userQueryService.handle(getUserByUsernameQuery);
        var role = user.get().getRoles().stream().findFirst().get().getName().toString();
        if (!role.equals("ROLE_BONDHOLDER")) {
            return Optional.empty();
        }
        var bondHolder = user.get().getBondHolder();
        if (bondHolder != null) {
            return Optional.of(bondHolder);
        }
        return Optional.empty();
    }
}
