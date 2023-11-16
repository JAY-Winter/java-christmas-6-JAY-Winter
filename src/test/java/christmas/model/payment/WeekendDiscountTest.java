package christmas.model.payment;

import christmas.model.menu.MainMenu;
import christmas.model.menu.Menu;
import christmas.model.order.Order;
import christmas.model.order.OrderMenu;
import christmas.model.order.OrderMenuItem;
import christmas.model.order.VisitDate;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WeekendDiscountTest {

    private final WeekendDiscount weekendDiscount = new WeekendDiscount();
    private static List<OrderMenuItem> orderMenuItems;


    private Order order;

    @BeforeEach
    void init() {
        order = getOrder();
    }

    @Test
    void 주말이고_메인메뉴가_1개_있을_때_할인가격은_2023원() {
        double result = weekendDiscount.calculateDiscount(order);
        Assertions.assertThat(result).isEqualTo(2023);
    }

    @Test
    void 주말이고_메인메뉴가_2개_있을_때_할인가격은_4046원() {
        Menu menu = MainMenu.CHRISTMAS_PASTA;
        int quantity = 1;
        OrderMenuItem orderMenuItem = new OrderMenuItem(menu, quantity);
        orderMenuItems.add(orderMenuItem);

        double result = weekendDiscount.calculateDiscount(order);

        Assertions.assertThat(result).isEqualTo(4046);
    }

    private static Order getOrder() {
        VisitDate visitDate = new VisitDate("1");

        Menu menu = MainMenu.BBQ_RIBS;
        int quantity = 1;
        OrderMenuItem orderMenuItem = new OrderMenuItem(menu, quantity);

        orderMenuItems = new ArrayList<>();
        orderMenuItems.add(orderMenuItem);

        OrderMenu orderMenu = new OrderMenu(orderMenuItems);
        Order order = new Order(orderMenu, visitDate);
        return order;
    }
}