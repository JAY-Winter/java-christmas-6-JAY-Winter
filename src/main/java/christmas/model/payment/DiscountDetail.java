package christmas.model.payment;

public class DiscountDetail {

    private final String description;
    private final double amount;

    public DiscountDetail(String description, double amount) {
        this.description = description;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return description + " : -" + amount + "원";
    }
}
