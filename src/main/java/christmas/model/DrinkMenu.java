package christmas.model;

public enum DrinkMenu implements Menu {

    RED_WINE("레드와인", 8000, "레드와인입니다"),
    WHITE_WINE("화이트와인", 7000, "화이트와인입니다");

    private final String name;
    private final double price;
    private final String description;

    DrinkMenu(String name, double price, String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
