package nrg.inc.koutape.bonds.domain.model.aggregates;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import nrg.inc.koutape.bonds.domain.model.commands.CreateBondCommand;
import nrg.inc.koutape.bonds.domain.model.valueobjects.Capitalization;
import nrg.inc.koutape.bonds.domain.model.valueobjects.CuponFrequency;
import nrg.inc.koutape.bonds.domain.model.valueobjects.InterestRateType;
import nrg.inc.koutape.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
public class Bond extends AuditableAbstractAggregateRoot<Bond> {

    @ManyToOne
    @JoinColumn(name = "issuer_id")
    private Issuer issuer;

    @ManyToMany(mappedBy = "bonds")
    private List<BondHolder> bondholders;

    private String name;

    private Double nominalValue;

    private Double comercialValue;

    private Integer years;

    @Enumerated(EnumType.STRING)
    private CuponFrequency cuponFrequency;

    private Integer daysPerYear = 360; // Default value, can be adjusted based on the context

    @Enumerated(EnumType.STRING)
    private InterestRateType interestRateType;

    @Enumerated(EnumType.STRING)
    private Capitalization capitalization;

    private Double interestRatePercentage;

    private Double anualDiscountRatePercentage;

    private Double incomeTaxPercentage;

    private Date issueDate;

    private Double premiumPercentage;

    private Double structuringPercentage;

    private Double placementPercentage;

    private Double floatingRatePercentage;

    private Double CAVALIPercentage;

    private Double anualInflationPercentage;

    @OneToMany(mappedBy = "bond", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CashFlow> cashFlows;

    @OneToOne(mappedBy = "bond", cascade = CascadeType.ALL, orphanRemoval = true)
    private BondResult bondResult;

    public Bond() {
        // Default constructor
    }

    public Bond(CreateBondCommand command) {
        this.name = command.name();
        this.nominalValue = command.nominalValue();
        this.comercialValue = command.comercialValue();
        this.years = command.years();
        this.cuponFrequency = command.cuponFrequency();
        this.daysPerYear = command.daysPerYear();
        this.interestRateType = command.interestRateType();
        this.capitalization = command.capitalization();
        this.interestRatePercentage = command.interestRatePercentage();
        this.anualDiscountRatePercentage = command.anualDiscountRatePercentage();
        this.incomeTaxPercentage = command.incomeTaxPercentage();
        this.issueDate = command.issueDate();
        this.premiumPercentage = command.premiumPercentage();
        this.structuringPercentage = command.structuringPercentage();
        this.placementPercentage = command.placementPercentage();
        this.floatingRatePercentage = command.floatingRatePercentage();
        this.CAVALIPercentage = command.CAVALIPercentage();
        this.anualInflationPercentage = command.anualInflationPercentage();
    }

    public void generateCashFlows(){
        var cuponFrequencyValue = 0;
        switch (this.cuponFrequency) {
            case MONTHLY -> cuponFrequencyValue = 30;
            case BIMONTHLY -> cuponFrequencyValue = 60;
            case TRIMESTRAL -> cuponFrequencyValue = 90;
            case QUADRIMONTHLY -> cuponFrequencyValue = 120;
            case SEMIANNUAL -> cuponFrequencyValue = 180;
            case ANNUAL -> cuponFrequencyValue = 360;
        }
        var capitalizationDays = 0;
        switch (this.capitalization) {
            case NONE -> capitalizationDays = 0;
            case DAILY -> capitalizationDays = 1;
            case BIWEEKLY -> capitalizationDays = 15;
            case MONTHLY -> capitalizationDays = 30;
            case BIMONTHLY -> capitalizationDays = 60;
            case TRIMONTHLY -> capitalizationDays = 90;
            case QUADRIMONTHLY -> capitalizationDays = 120;
            case SEMIANNUAL -> capitalizationDays = 180;
            case ANNUAL -> capitalizationDays = 360;
        }
        var periodsPerYear = this.daysPerYear/ cuponFrequencyValue;
        var totalPeriods = 0;
        var effectiveAnualRate = 0.0;
        var effectivePeriodRate = 0.0;
        var periodCOK = 0.0;
        var initialIssuerCosts = 0.0;
        var initialBondHolderCosts = 0.0;
    }
}
