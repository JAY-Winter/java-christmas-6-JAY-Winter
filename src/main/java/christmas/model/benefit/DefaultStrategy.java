package christmas.model.benefit;

import christmas.util.PriceFormat;

public class DefaultStrategy implements BenefitStrategy {

    @Override
    public String formatOutput(String name, double price) {
        return name + ": " + "-" + PriceFormat.formatPrice(price);
    }
}
