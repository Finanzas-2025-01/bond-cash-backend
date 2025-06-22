package nrg.inc.koutape.bonds.interfaces.rest.transform;

import nrg.inc.koutape.bonds.domain.model.aggregates.Bond;
import nrg.inc.koutape.bonds.domain.model.aggregates.CashFlow;
import nrg.inc.koutape.bonds.domain.model.valueobjects.GracePeriod;
import nrg.inc.koutape.bonds.interfaces.rest.resources.CashFlowResource;

import java.util.Date;

public class CashFlowResourceFromEntityAssembler {
    public static CashFlowResource toResourceFromEntity(CashFlow cashFlow) {
        return new CashFlowResource(
                cashFlow.getPeriodNumber(),
                cashFlow.getAssignedDate(),
                cashFlow.getAnualInflation(),
                cashFlow.getPeriodInflation(),
                cashFlow.getGracePeriod(),
                cashFlow.getBondValue(),
                cashFlow.getIndexedBondValue(),
                cashFlow.getCupon(),
                cashFlow.getPayment(),
                cashFlow.getAmortization(),
                cashFlow.getPremium(),
                cashFlow.getShield(),
                cashFlow.getIssuerFlow(),
                cashFlow.getIssuerFlowWithShield(),
                cashFlow.getBondHolderFlow(),
                cashFlow.getUpdatedFlow(),
                cashFlow.getFAxPeriod(),
                cashFlow.getPFactor()
        );
    }
}
