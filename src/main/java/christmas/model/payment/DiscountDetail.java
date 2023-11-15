package christmas.model.payment;

import christmas.model.benefit.BenefitStrategy;

public class DiscountDetail {

    private final String description;
    private final double amount;

    private final BenefitStrategy benefitStrategy;

    public DiscountDetail(String description, double amount, BenefitStrategy benefitStrategy) {
        this.description = description;
        this.amount = amount;
        this.benefitStrategy = benefitStrategy;
    }

    @Override
    public String toString() {
        return description + " : -" + amount + "원";
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }
}
