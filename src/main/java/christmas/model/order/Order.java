package christmas.model.order;

public class Order {

    private final OrderMenu orderMenu;
    private final VisitDate visitDate;

    public Order(OrderMenu orderMenu, VisitDate visitDate) {
        this.orderMenu = orderMenu;
        this.visitDate = visitDate;
    }

    public OrderMenu getOrderMenu() {
        return orderMenu;
    }

    public VisitDate getVisitDate() {
        return visitDate;
    }
}
