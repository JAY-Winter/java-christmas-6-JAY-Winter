package christmas.dto;

import christmas.util.PriceFormat;

public class DiscountDetailDto {

    private final String description;
    private final double amount;

    public DiscountDetailDto(String description, double amount) {
        this.description = description;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return description + " : -" + PriceFormat.formatPrice(amount);
    }
}
