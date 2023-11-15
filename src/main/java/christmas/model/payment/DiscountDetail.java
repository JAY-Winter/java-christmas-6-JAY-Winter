package christmas.model.payment;

import christmas.model.benefit.BenefitStrategy;
import christmas.util.PriceFormat;

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
        return description + " : -" + PriceFormat.formatPrice(amount);
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }
}
