package christmas.model;

public enum AppetizerMenu implements Menu {
    MUSHROOM_SOUP("양송이수프", 6000, "양송이수프입니다"),
    TAPAS("타파스", 5500, "타파스입니다"),
    CAESAR_SALAD("시저샐러드", 8000, "시저샐러드입니다");

    private final String name;
    private final double price;
    private final String description;

    AppetizerMenu(String name, double price, String description) {
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
