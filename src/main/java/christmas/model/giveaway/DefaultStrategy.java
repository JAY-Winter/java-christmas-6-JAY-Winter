package christmas.model.giveaway;

public class DefaultStrategy implements GiveawayStrategy {

    @Override
    public String formatOutput(String name, int quantity) {
        return name + " " + quantity + "개";
    }
}
