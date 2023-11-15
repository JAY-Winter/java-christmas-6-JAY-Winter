package christmas.model.giveaway;

public class NoneStrategy implements GiveawayStrategy {

    @Override
    public String formatOutput(String name, int quantity) {
        return name;
    }
}
