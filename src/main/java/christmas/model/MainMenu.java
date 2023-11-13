package christmas.model;

public enum MainMenu implements Menu {

    T_BORN_STAKE("티본스테이크", 55000, "티본스테이크입니다"),
    BBQ_RIBS("바비큐립", 54000, "바베큐립입니다"),
    SEAFOOD_PASTA("해산물파스타", 35000, "신선한 해산물이 들어간 파스타"),
    CHRISTMAS_PASTA("크리스마스파스타", 25000, "크리스마스 파스타입니다");

    private final String name;
    private final double price;
    private final String description;

    MainMenu(String name, double price, String description) {
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
