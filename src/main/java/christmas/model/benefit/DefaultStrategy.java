package christmas.model.benefit;

public class DefaultStrategy implements BenefitStrategy {

    @Override
    public String formatOutput(String name, double price) {
        return name + ": " + "-" + price + "원";
    }
}
