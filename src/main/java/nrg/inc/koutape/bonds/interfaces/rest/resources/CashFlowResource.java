package nrg.inc.koutape.bonds.interfaces.rest.resources;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import nrg.inc.koutape.bonds.domain.model.aggregates.Bond;
import nrg.inc.koutape.bonds.domain.model.valueobjects.GracePeriod;

import java.util.Date;

public record CashFlowResource(
        Long bondId,
        Integer periodNumber,
        Date assignedDate,
        Double anualInflation,
        Double periodInflation,
        GracePeriod gracePeriod,
        Double bondValue,
        Double indexedBondValue,
        Double cupon,
        Double payment,
        Double amortization,
        Double premium,
        Double shield,
        Double issuerFlow,
        Double issuerFlowWithShield,
        Double bondHolderFlow,
        Double updatedFlow,
        Double FAxPeriod,
        Double pFactor
) {
}
