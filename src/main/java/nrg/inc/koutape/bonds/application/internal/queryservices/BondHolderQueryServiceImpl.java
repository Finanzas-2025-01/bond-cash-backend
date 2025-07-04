package nrg.inc.koutape.bonds.application.internal.queryservices;

import nrg.inc.koutape.bonds.domain.model.aggregates.Bond;
import nrg.inc.koutape.bonds.domain.model.aggregates.BondHolder;
import nrg.inc.koutape.bonds.domain.model.queries.GetAllBondsByBondHolderIdQuery;
import nrg.inc.koutape.bonds.domain.model.queries.GetBondHolderByUsernameQuery;
import nrg.inc.koutape.bonds.domain.services.BondHolderQueryService;
import nrg.inc.koutape.bonds.infrastructure.persistence.jpa.repositories.BondHolderRepository;
import nrg.inc.koutape.iam.domain.model.queries.GetUserByUsernameQuery;
import nrg.inc.koutape.iam.domain.services.UserQueryService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BondHolderQueryServiceImpl implements BondHolderQueryService {
    private final UserQueryService userQueryService;
    private final BondHolderRepository bondHolderRepository;

    public BondHolderQueryServiceImpl(UserQueryService userQueryService, BondHolderRepository bondHolderRepository) {
        this.userQueryService = userQueryService;
        this.bondHolderRepository = bondHolderRepository;
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

    @Override
    public List<Bond> handle(GetAllBondsByBondHolderIdQuery query) {
        var bondHolder = bondHolderRepository.findById(query.bondHolderId());
        if (bondHolder.isPresent()) {
            return bondHolder.get().getBonds();
        }
        return List.of();
    }
}
