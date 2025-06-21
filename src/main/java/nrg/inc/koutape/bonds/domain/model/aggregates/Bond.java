package nrg.inc.koutape.bonds.domain.model.aggregates;

import jakarta.persistence.*;
import nrg.inc.koutape.bonds.domain.model.valueobjects.Capitalization;
import nrg.inc.koutape.bonds.domain.model.valueobjects.CuponFrequency;
import nrg.inc.koutape.bonds.domain.model.valueobjects.InterestRateType;
import nrg.inc.koutape.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.util.Date;
import java.util.List;

@Entity
public class Bond extends AuditableAbstractAggregateRoot<Bond> {

    @ManyToOne
    @JoinColumn(name = "bond_holder_id")
    private BondHolder bondHolder;

    @ManyToMany(mappedBy = "bonds")
    private List<Investor> investors;

    private String name;

    private Double nominalValue;

    private Double comercialValue;

    private Integer years;

    @Enumerated(EnumType.STRING)
    private CuponFrequency cuponFrequency;

    @Enumerated(EnumType.STRING)
    private InterestRateType interestRateType;

    @Enumerated(EnumType.STRING)
    private Capitalization capitalization;

    private Double interestRatePercentage;

    private Double anualDiscountRatePercentage;

    private Double incomeTaxPercentage;

    private Date issueDate;

    private Double percentagePremium;

    private Double percentageStructuring;

    private Double percentagePlacement;

    private Double percentageFloatingRate;

    private Double percentageCAVALI;

    private Double percentageAnualInflation;

    @OneToMany(mappedBy = "bond", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CashFlow> cashFlows;

    public Bond() {
        // Default constructor
    }

    // Additional methods can be added here
}
