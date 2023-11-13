package christmas.model;

public enum DrinkMenu implements Menu {

    ZERO_COKE("제로콜라", 3000, "제로콜라입니다"),
    RED_WINE("레드와인", 60000, "레드와인입니다"),
    CHAMPAGNE("샴페인", 25000, "샴페인입니다");

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
