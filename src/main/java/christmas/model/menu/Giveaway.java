package christmas.model.menu;

import java.util.Arrays;
import java.util.function.BiFunction;

public enum Giveaway {
    CHAMPAGNE("샴페인", 25000, 1, (price, giveaway) -> price >= Giveaway.minimumPrice),
    NONE("없음", 25000, 0, (price, giveaway) -> price < Giveaway.minimumPrice);

    private final static double minimumPrice = 120000;
    private final String name;
    private final double standard_price;
    private final int quantity;
    private final BiFunction<Double, Giveaway, Boolean> isConform;

    Giveaway(String name, double standardPrice, int quantity,
        BiFunction<Double, Giveaway, Boolean> isConform) {
        this.name = name;
        this.standard_price = standardPrice;
        this.quantity = quantity;
        this.isConform = isConform;
    }

    public static boolean isEligibleForGiveaway(double price) {
        return getModel(price) != NONE;
    }

    public static String getGiveaway(double price) {
        return Arrays.stream(Giveaway.values())
            .filter(giveaway -> giveaway.isConform(price))
            .findFirst()
            .map(giveaway -> giveaway.name)
            .orElse(NONE.name);
    }

    public static Giveaway getModel(double price) {
        return Arrays.stream(Giveaway.values())
            .filter(giveaway -> giveaway.isConform(price))
            .findFirst()
            .orElse(NONE);
    }

    public static double getStandardPriceByName(String name) {
        return Arrays.stream(Giveaway.values())
            .filter(giveaway -> giveaway.name.equals(name))
            .findFirst()
            .map(giveaway -> giveaway.standard_price)
            .orElse((double) 0);
    }

    private Boolean isConform(double standard_price) {
        return isConform.apply(standard_price, this);
    }
}
