package christmas.model.payment;

import christmas.model.giveaway.Giveaway;
import christmas.model.order.Order;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DiscountManager {

    private static final List<DiscountStrategy> discountStrategies = new ArrayList<>();

    public double applyDiscounts(Order order) {
        return discountStrategies.stream()
            .mapToDouble(strategy -> strategy.calculateDiscount(order))
            .sum();
    }

    public List<DiscountDetail> getDiscountDetails(Order order) {
        return discountStrategies.stream()
            .flatMap(strategy -> strategy.getDiscountDetails(order).stream())
            .collect(Collectors.toList());
    }

    public void configureDiscountStrategies(Order order) {
        if (isAbleToGetBenefit(order.getOrderMenu().getTotalPriceBeforeDiscount())) {
            isWeekend(order);
            isWeekday(order);
            isSpecialDay(order);
            isGiveaway(order);
        }
    }

    private static boolean isAbleToGetBenefit(double total_price_before_discount) {
        return total_price_before_discount >= 10000;
    }

    private static void isGiveaway(Order order) {
        if (Giveaway.isEligibleForGiveaway(order.getOrderMenu().getTotalPriceBeforeDiscount())) {
            discountStrategies.add(new GiveawayDiscount());
        }
    }

    private static void isWeekday(Order order) {
        if (!order.getVisitDate().isWeekend()) {
            discountStrategies.add(new WeekdayDiscount());
        }
    }

    private static void isWeekend(Order order
    ) {
        if (order.getVisitDate().isWeekend()) {
            discountStrategies.add(new WeekendDiscount());
        }
    }

    private static void isSpecialDay(Order order) {
        if (order.getVisitDate().getLocalDate().getDayOfMonth() < 31) {
            discountStrategies.add(new SpecialDiscount());
        }
    }
}
