package christmas.model;

import java.util.Arrays;
import java.util.function.BiFunction;

public enum Giveaway {
    CHAMPAGNE("샴페인", 120000, (price, giveaway) -> price >= Giveaway.minimumPrice),
    NONE("없음", 120000, (price, giveaway) -> price < Giveaway.minimumPrice);


    private final static double minimumPrice = 120000;
    private final String name;
    private final double standard_price;
    private final BiFunction<Double, Giveaway, Boolean> isConform;

    Giveaway(String name, double standardPrice, BiFunction<Double, Giveaway, Boolean> isConform) {
        this.name = name;
        this.standard_price = standardPrice;
        this.isConform = isConform;
    }

    public static String getGiveaway(double price) {
        return Arrays.stream(Giveaway.values())
            .filter(giveaway -> giveaway.isConform(price))
            .findFirst()
            .map(giveaway -> giveaway.name)
            .orElse(NONE.name);
    }

    private Boolean isConform(double standard_price) {
        return isConform.apply(standard_price, this);
    }
}
