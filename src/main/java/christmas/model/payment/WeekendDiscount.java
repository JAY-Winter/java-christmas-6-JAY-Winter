package christmas.model.payment;

import christmas.model.order.Order;
import java.util.ArrayList;
import java.util.List;

public class WeekendDiscount implements DiscountStrategy {

    private static final double WEEKEND_DISCOUNT_AMOUNT = 2023;

    @Override
    public double calculateDiscount(Order order) {
        int mainMenuCount = order.getOrderMenu().getMainMenuCount();
        return mainMenuCount * WEEKEND_DISCOUNT_AMOUNT;
    }

    @Override
    public List<DiscountDetail> getDiscountDetails(Order order) {
        return new ArrayList<>(
            List.of(new DiscountDetail("주말 할인", calculateDiscount(order))));
    }
}