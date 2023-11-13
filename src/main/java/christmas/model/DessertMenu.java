package christmas.model;

public enum DessertMenu implements Menu {
    CHOCOLATE_CAKE("초코케이크", 15000, "초코케이크입니다"),
    ICE_CREAM("아이스크림", 5500, "아이스크림입니다");

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
