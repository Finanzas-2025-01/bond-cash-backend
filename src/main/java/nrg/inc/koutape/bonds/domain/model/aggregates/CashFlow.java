package nrg.inc.koutape.bonds.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import nrg.inc.koutape.bonds.domain.model.valueobjects.GracePeriod;
import nrg.inc.koutape.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.util.Date;

@Entity
@Setter
@Getter
public class CashFlow extends AuditableAbstractAggregateRoot<CashFlow> {
    @ManyToOne
    @JoinColumn(name = "bond_id")
    private Bond bond;

    private Integer periodNumber;

    private Date assignedDate;

    private Double anualInflation;

    private Double periodInflation;

    @Enumerated(EnumType.STRING)
    private GracePeriod gracePeriod;

    private Double bondValue;

    private Double indexedBondValue;

    private Double cupon;

    private Double payment;

    private Double amortization;

    private Double premium;

    private Double shield;

    private Double issuerFlow;

    private Double issuerFlowWithShield;

    private Double bondHolderFlow;

    private Double updatedFlow;

    private Double FAxPeriod;

    private Double pFactor;

}
