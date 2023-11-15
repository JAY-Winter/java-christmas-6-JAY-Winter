package christmas.model.payment;

import christmas.model.benefit.DefaultStrategy;
import christmas.model.order.Order;
import java.util.ArrayList;
import java.util.List;

public class WeekdayDiscount implements DiscountStrategy {

    private static final double WEEKDAY_DISCOUNT_AMOUNT = 2023;

    @Override
    public double calculateDiscount(Order order) {
        int dessertMenuCount = order.getOrderMenu().getDessertMenuCount();
        return dessertMenuCount * WEEKDAY_DISCOUNT_AMOUNT;
    }

    @Override
    public List<DiscountDetail> getDiscountDetails(Order order) {
        return new ArrayList<>(
            List.of(new DiscountDetail("평일 할인", calculateDiscount(order), new DefaultStrategy())));
    }
}
