package christmas.model.payment;

import christmas.model.benefit.NoneStrategy;
import christmas.model.order.Order;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DiscountManager {

    private final List<DiscountStrategy> discountStrategies;
    private final List<DiscountStrategy> appliedStrategies = new ArrayList<>();

    public DiscountManager(List<DiscountStrategy> discountStrategies) {
        this.discountStrategies = discountStrategies;
    }

    public void configure(Order order) {
        if (isAbleToGetBenefit(order.getOrderMenu().getTotalPriceBeforeDiscount())) {
            discountStrategies.stream()
                .filter(discountStrategy -> discountStrategy.isApply(order))
                .forEach(appliedStrategies::add);
        }
    }

    public double calculateTotalDiscountPrice(Order order) {
        return appliedStrategies.stream()
            .mapToDouble(strategy -> strategy.calculateDiscount(order))
            .sum();
    }

    public List<DiscountDetail> getDiscountDetails(Order order) {
        List<DiscountDetail> discountDetails = appliedStrategies.stream()
            .flatMap(strategy -> strategy.getDiscountDetails(order).stream())
            .collect(Collectors.toList());

        if (discountDetails.isEmpty()) {
            discountDetails.add(new DiscountDetail("없음", 0, new NoneStrategy()));
        }

        return discountDetails;
    }

    private static boolean isAbleToGetBenefit(double total_price_before_discount) {
        return total_price_before_discount >= 10000;
    }
}
