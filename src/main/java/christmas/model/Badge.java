package christmas.model;

import java.util.Arrays;
import java.util.function.BiFunction;

public enum Badge {
    SANTA("산타", 20000, (price, badge) -> price >= badge.price),
    TREE("트리", 10000, (price, badge) -> price >= badge.price),
    STAR("별", 5000, (price, badge) -> price >= badge.price),
    NONE("없음", 0, (price, badge) -> price < badge.price);

    private final String name;
    private final double price;
    private final BiFunction<Double, Badge, Boolean> isConform;

    Badge(String name, double price, BiFunction<Double, Badge, Boolean> isConform) {
        this.name = name;
        this.price = price;
        this.isConform = isConform;
    }

    public static String getBadge(double price) {
        return Arrays.stream(Badge.values())
            .filter(badge -> badge.isConform(price))
            .findFirst()
            .map(badge -> badge.name)
            .orElse(NONE.name);
    }

    private boolean isConform(double standardPrice) {
        return isConform.apply(standardPrice, this);
    }
}
