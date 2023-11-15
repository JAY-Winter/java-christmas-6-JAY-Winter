package christmas.dto;

public class OrderMenuDto {

    private final String name;

    private final int quantity;

    public OrderMenuDto(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return name + " " + quantity + "개";
    }
}
