package christmas.model.payment;

import christmas.model.benefit.DefaultStrategy;
import christmas.model.order.Order;
import java.time.DayOfWeek;
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
            List.of(new DiscountDetail(DiscountDescription.WEEKEND.getDescription(), calculateDiscount(order), new DefaultStrategy())));
    }

    @Override
    public boolean isApply(Order order) {
        DayOfWeek dayOfWeek = order.getVisitDate().getLocalDate().getDayOfWeek();
        return dayOfWeek == DayOfWeek.FRIDAY || dayOfWeek == DayOfWeek.SATURDAY;
    }
}
