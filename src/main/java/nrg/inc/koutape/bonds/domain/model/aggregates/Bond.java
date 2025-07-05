package nrg.inc.koutape.bonds.domain.model.aggregates;

import io.micrometer.common.lang.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import nrg.inc.koutape.bonds.domain.model.commands.CreateBondCommand;
import nrg.inc.koutape.bonds.domain.model.commands.UpdateBondCommand;
import nrg.inc.koutape.bonds.domain.model.entities.CashFlowGracePeriod;
import nrg.inc.koutape.bonds.domain.model.valueobjects.*;
import nrg.inc.koutape.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
public class Bond extends AuditableAbstractAggregateRoot<Bond> {

    @ManyToOne
    @JoinColumn(name = "issuer_id")
    private Issuer issuer;

    @ManyToOne
    @Nullable
    @JoinColumn(name = "bondholder_id")
    private BondHolder bondholder;

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

    @OneToMany(mappedBy = "bond", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CashFlowGracePeriod> cashFlowGracePeriods;

    @Enumerated(EnumType.STRING)
    private BondType bondType;

    public Bond() {
        // Default constructor
    }

    public Bond(Bond baseBond){
        this.name = baseBond.name;
        this.nominalValue = baseBond.nominalValue;
        this.comercialValue = baseBond.comercialValue;
        this.years = baseBond.years;
        this.cuponFrequency = baseBond.cuponFrequency;
        this.daysPerYear = baseBond.daysPerYear;
        this.interestRateType = baseBond.interestRateType;
        this.capitalization = baseBond.capitalization;
        this.interestRatePercentage = baseBond.interestRatePercentage;
        this.anualDiscountRatePercentage = baseBond.anualDiscountRatePercentage;
        this.incomeTaxPercentage = baseBond.incomeTaxPercentage;
        this.issueDate = new Date();
        this.premiumPercentage = baseBond.premiumPercentage;
        this.structuringPercentage = baseBond.structuringPercentage;
        this.placementPercentage = baseBond.placementPercentage;
        this.floatingRatePercentage = baseBond.floatingRatePercentage;
        this.CAVALIPercentage = baseBond.CAVALIPercentage;
        this.anualInflationPercentage = baseBond.anualInflationPercentage;
        this.bondType = BondType.HIRED;
        this.cashFlowGracePeriods = new ArrayList<>();
        if (baseBond.cashFlowGracePeriods != null) {
            for (CashFlowGracePeriod gp : baseBond.cashFlowGracePeriods) {
                CashFlowGracePeriod newGp = new CashFlowGracePeriod();
                newGp.setBond(this); // Importante: asignar el nuevo bond
                newGp.setPeriodNumber(gp.getPeriodNumber());
                newGp.setGracePeriod(gp.getGracePeriod());
                this.cashFlowGracePeriods.add(newGp);
            }
        }
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
        if(command.issueDate() != null) {
            this.issueDate = command.issueDate();
        } else {
            // If no issue date is provided, set it to the current date
            this.issueDate = new Date();
        }
        this.premiumPercentage = command.premiumPercentage();
        this.structuringPercentage = command.structuringPercentage();
        this.placementPercentage = command.placementPercentage();
        this.floatingRatePercentage = command.floatingRatePercentage();
        this.CAVALIPercentage = command.CAVALIPercentage();
        this.anualInflationPercentage = command.anualInflationPercentage();
        this.bondType = BondType.BASE;
    }

    public void updateBond(UpdateBondCommand command) {
        if (command.comercialValue() != null && command.comercialValue() != 0) this.comercialValue = command.comercialValue();
        if (command.interestRatePercentage() != null && command.interestRatePercentage() != 0) this.interestRatePercentage = command.interestRatePercentage();
        if (command.anualDiscountRatePercentage() != null && command.anualDiscountRatePercentage() != 0) this.anualDiscountRatePercentage = command.anualDiscountRatePercentage();
        if (command.premiumPercentage() != null && command.premiumPercentage() != 0) this.premiumPercentage = command.premiumPercentage();
        if (command.structuringPercentage() != null && command.structuringPercentage() != 0) this.structuringPercentage = command.structuringPercentage();
        if (command.placementPercentage() != null && command.placementPercentage() != 0) this.placementPercentage = command.placementPercentage();
        if (command.floatingRatePercentage() != null && command.floatingRatePercentage() != 0) this.floatingRatePercentage = command.floatingRatePercentage();
        if (command.CAVALIPercentage() != null && command.CAVALIPercentage() != 0) this.CAVALIPercentage = command.CAVALIPercentage();
    }

    public void generateCashFlowGracePeriods() {
        if (this.cashFlowGracePeriods == null) {
            this.cashFlowGracePeriods = new ArrayList<>();
        } else {
            this.cashFlowGracePeriods.clear();
        }
        var cuponFrequencyValue = 0;
        switch (this.cuponFrequency) {
            case MONTHLY -> cuponFrequencyValue = 30;
            case BIMONTHLY -> cuponFrequencyValue = 60;
            case TRIMESTRAL -> cuponFrequencyValue = 90;
            case QUADRIMONTHLY -> cuponFrequencyValue = 120;
            case SEMIANNUAL -> cuponFrequencyValue = 180;
            case ANNUAL -> cuponFrequencyValue = 360;
        }
        var periodsPerYear = this.daysPerYear / cuponFrequencyValue;
        var totalPeriods = this.years * periodsPerYear;

        for (int i = 0; i < totalPeriods; i++) {
            var cashFlowGracePeriod = new CashFlowGracePeriod();
            cashFlowGracePeriod.setBond(this);
            cashFlowGracePeriod.setPeriodNumber(i + 1); // Period numbers start from 1
            cashFlowGracePeriod.setGracePeriod(GracePeriod.S); // Valor por defecto
            this.cashFlowGracePeriods.add(cashFlowGracePeriod);
        }
    }

    public void generateCashFlows(){
        if (this.cashFlows == null) {
            this.cashFlows = new ArrayList<>();
        } else {
            this.cashFlows.clear();
        }
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
        var totalPeriods = this.years * periodsPerYear;
        var effectiveAnualRate = 0.0;
        switch (this.interestRateType) {
            case EFFECTIVE -> effectiveAnualRate = this.interestRatePercentage/100;
            case NOMINAL -> effectiveAnualRate = (Math.pow(1 + ((this.interestRatePercentage/100)/((double)this.daysPerYear/capitalizationDays)), (double)this.daysPerYear/capitalizationDays) - 1);
        }
        var effectivePeriodRate = (Math.pow((1+effectiveAnualRate), (double)cuponFrequencyValue/this.daysPerYear)) - 1;
        var periodCOK = (Math.pow((1+this.anualDiscountRatePercentage/100), (double)cuponFrequencyValue/this.daysPerYear)) - 1;
        var initialIssuerCosts = ((this.structuringPercentage+this.placementPercentage+this.floatingRatePercentage+this.CAVALIPercentage)/100) * this.comercialValue;
        var initialBondHolderCosts = ((this.floatingRatePercentage+this.CAVALIPercentage)/100) * this.comercialValue;

        System.out.println("cuponFrequencyValue: " + cuponFrequencyValue);
        System.out.println("capitalizationDays: " + capitalizationDays);
        System.out.println("periodsPerYear: " + periodsPerYear);
        System.out.println("totalPeriods: " + totalPeriods);
        System.out.println("effectiveAnualRate: " + effectiveAnualRate);
        System.out.println("effectivePeriodRate: " + effectivePeriodRate);
        System.out.println("periodCOK: " + periodCOK);
        System.out.println("initialIssuerCosts: " + initialIssuerCosts);
        System.out.println("initialBondHolderCosts: " + initialBondHolderCosts);
        for (int i = 0; i <= totalPeriods; i++) {
            var cashFlow = new CashFlow();
            cashFlow.setBond(this);
            cashFlow.setPeriodNumber(i);
            if(i == 0){
                cashFlow.setAssignedDate(this.issueDate);
                cashFlow.setIssuerFlow(this.comercialValue - initialIssuerCosts);
                cashFlow.setIssuerFlowWithShield(this.comercialValue - initialIssuerCosts);
                cashFlow.setBondHolderFlow(-this.comercialValue - initialBondHolderCosts);
            }else {
                var previousCashFlow = this.cashFlows.get(i - 1);
                //Assignated date
                // Calculate the assigned date based on the previous cash flow's assigned date and the cupon frequency
                var previousAssignedDate = previousCashFlow.getAssignedDate();
                var calendar = Calendar.getInstance();
                calendar.setTime(previousAssignedDate);
                calendar.add(Calendar.DAY_OF_YEAR, cuponFrequencyValue);
                var assignedDate = calendar.getTime();
                cashFlow.setAssignedDate(assignedDate);

                // Anual inflation
                // Set the annual inflation
                cashFlow.setAnualInflation(this.anualInflationPercentage/100);

                // Period inflation
                // Set the period inflation based on the cupon frequency
                var cashFlowAnualInflation = this.anualInflationPercentage/100;
                var periodInflation = Math.pow(1 + cashFlowAnualInflation,( (double) cuponFrequencyValue/this.daysPerYear))-1;
                cashFlow.setPeriodInflation(periodInflation);

                // Grace Period
                // Set the cupon grace period+
                int periodNumber = i;
                GracePeriod gracePeriod = this.cashFlowGracePeriods.stream()
                        .filter(gp -> gp.getPeriodNumber() != null && gp.getPeriodNumber().intValue() == periodNumber)
                        .findFirst()
                        .map(CashFlowGracePeriod::getGracePeriod)
                        .orElse(GracePeriod.S);
                cashFlow.setGracePeriod(gracePeriod);

                //Bond
                if(i == 1){
                    cashFlow.setBondValue(this.nominalValue);
                }else{
                    double bondValue;
                    if(previousCashFlow.getGracePeriod() == GracePeriod.T) {
                        bondValue = previousCashFlow.getIndexedBondValue() - previousCashFlow.getCupon();
                    }else{
                        bondValue = previousCashFlow.getIndexedBondValue() + previousCashFlow.getAmortization();
                    }
                    cashFlow.setBondValue(bondValue);
                }

                //Indexed Bond Value
                cashFlow.setIndexedBondValue(cashFlow.getBondValue() * (1 + cashFlow.getPeriodInflation()));

                //Cupon
                cashFlow.setCupon(cashFlow.getIndexedBondValue() * effectivePeriodRate * -1);

                //Payment
                switch (cashFlow.getGracePeriod()){
                    case T:
                        cashFlow.setPayment(0.0);
                        System.out.println("Payment is 0 due to grace period " + cashFlow.getGracePeriod() + " for period " + i);
                        break;
                    case P:
                        cashFlow.setPayment(cashFlow.getCupon());
                        System.out.println("Payment is cupon: " + cashFlow.getPayment() + " due to grace period " + cashFlow.getGracePeriod() + " for period " + i);
                        break;
                    case S:
                        double paymentNumerator = cashFlow.getIndexedBondValue() * effectivePeriodRate;
                        double paymentDenominator = 1 - Math.pow(1 + effectivePeriodRate, - (totalPeriods - i + 1));
                        double payment = paymentNumerator / paymentDenominator;
                        cashFlow.setPayment(payment * - 1);
                        System.out.println("Payment is calculated: " + cashFlow.getPayment() + " due to grace period " + cashFlow.getGracePeriod() + " for period " + i);
                        break;
                }

                //Amortization
                if(cashFlow.getGracePeriod() == GracePeriod.S) {
                    cashFlow.setAmortization(cashFlow.getPayment() - cashFlow.getCupon());
                }else {
                    cashFlow.setAmortization(0.0);
                }

                //Premium
                if(i == totalPeriods) {
                    cashFlow.setPremium(-1 * cashFlow.getIndexedBondValue() * this.premiumPercentage / 100);
                } else {
                    cashFlow.setPremium(0.0);
                }

                //Shield
                cashFlow.setShield(-1 * cashFlow.getCupon() * this.incomeTaxPercentage / 100);

                //Issuer Flow
                cashFlow.setIssuerFlow(cashFlow.getPayment() + cashFlow.getPremium());

                //Issuer Flow with Shield
                cashFlow.setIssuerFlowWithShield(cashFlow.getPayment() + cashFlow.getPremium() + cashFlow.getShield());

                //Bond Holder Flow
                cashFlow.setBondHolderFlow((cashFlow.getPayment() + cashFlow.getPremium()) * -1);

                //Updated Flow
                cashFlow.setUpdatedFlow(
                    cashFlow.getBondHolderFlow() * Math.pow(1 + periodCOK, -i)
                );

                //FA x Period
                cashFlow.setFAxPeriod(
                    cashFlow.getUpdatedFlow() * i * cuponFrequencyValue / this.daysPerYear
                );

                //P Factor
                cashFlow.setPFactor(
                    cashFlow.getUpdatedFlow() * i * (1 + i)
                );

            }
            cashFlows.add(cashFlow);
        }
    }
}
