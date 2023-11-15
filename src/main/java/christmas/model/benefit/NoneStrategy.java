package christmas.model.benefit;

public class NoneStrategy implements BenefitStrategy{

    @Override
    public String formatOutput(String name, double price) {
        return name;
    }
}
