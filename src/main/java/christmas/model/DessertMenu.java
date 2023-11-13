package christmas.model;

public enum DessertMenu implements Menu {
    CHOCOLATE_CAKE("초코케이크", 6000, "초코케이크입니다"),
    CHEESE_CAKE("치즈케이크", 5000, "치즈케이크입니다");

    private final String name;
    private final double price;
    private final String description;


    DessertMenu(String name, double price, String description) {
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
