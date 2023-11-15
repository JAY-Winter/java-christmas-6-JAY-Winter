package christmas.model.payment;

import christmas.model.benefit.NoneStrategy;
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
        List<DiscountDetail> discountDetails = discountStrategies.stream()
            .flatMap(strategy -> strategy.getDiscountDetails(order).stream())
            .collect(Collectors.toList());

        if (discountDetails.isEmpty()) {
            discountDetails.add(new DiscountDetail("없음", 0, new NoneStrategy()));
        }

        return discountDetails;
    }

    public void configureDiscountStrategies(Order order) {
        if (isAbleToGetBenefit(order.getOrderMenu().getTotalPriceBeforeDiscount())) {
            checkWeekendStrategy(order);
            checkWeekdayStrategy(order);
            checkSpecialDayStrategy(order);
            checkGiveawayStrategy(order);
        }
    }

    private static boolean isAbleToGetBenefit(double total_price_before_discount) {
        return total_price_before_discount >= 10000;
    }

    private static void checkGiveawayStrategy(Order order) {
        if (Giveaway.isEligibleForGiveaway(order.getOrderMenu().getTotalPriceBeforeDiscount())) {
            discountStrategies.add(new GiveawayDiscount());
        }
    }

    private static void checkWeekdayStrategy(Order order) {
        if (WeekdayDiscount.isWeekday(order.getVisitDate().getLocalDate().getDayOfWeek())) {
            discountStrategies.add(new WeekdayDiscount());
        }
    }

    private static void checkWeekendStrategy(Order order) {
        if (WeekendDiscount.isWeekend(order.getVisitDate().getLocalDate().getDayOfWeek())) {
            discountStrategies.add(new WeekendDiscount());
        }
    }

    private static void checkSpecialDayStrategy(Order order) {
        if (SpecialDiscount.isSpecialDay(order.getVisitDate())) {
            discountStrategies.add(new SpecialDiscount());
        }
    }
}
