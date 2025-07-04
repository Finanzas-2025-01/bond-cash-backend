package nrg.inc.koutape.bonds.domain.model.queries;

import nrg.inc.koutape.bonds.domain.model.aggregates.Bond;

public record GetAllBondsByBondHolderIdQuery(Long bondHolderId) {
}
