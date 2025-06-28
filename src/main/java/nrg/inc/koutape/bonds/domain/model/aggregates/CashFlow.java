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

    private Integer periodNumber = 0;

    private Date assignedDate = new Date();

    private Double anualInflation = 0.0;

    private Double periodInflation = 0.0;

    @Enumerated(EnumType.STRING)
    private GracePeriod gracePeriod = GracePeriod.N;

    private Double bondValue = 0.0;

    private Double indexedBondValue = 0.0;

    private Double cupon = 0.0;

    private Double payment = 0.0;

    private Double amortization = 0.0;

    private Double premium = 0.0;

    private Double shield = 0.0;

    private Double issuerFlow = 0.0;

    private Double issuerFlowWithShield = 0.0;

    private Double bondHolderFlow = 0.0;

    private Double updatedFlow = 0.0;

    private Double FAxPeriod = 0.0;

    private Double pFactor = 0.0;

    public CashFlow() {

    }

}
