package christmas.model;

public class OrderMenuItem {

    private final Menu menu;
    private final int quantity;


    public OrderMenuItem(Menu menu, int quantity) {
        this.menu = menu;
        this.quantity = quantity;
    }

    public Menu getMenu() {
        return menu;
    }

    public int getQuantity() {
        return quantity;
    }
}