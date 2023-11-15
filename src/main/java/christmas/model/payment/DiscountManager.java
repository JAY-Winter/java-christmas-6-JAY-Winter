package christmas.model.payment;

import christmas.model.benefit.NoneStrategy;
import christmas.model.order.Order;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DiscountManager {

    private static final double STANDARD_BENEFIT_PRICE = 10000;
    private final List<DiscountStrategy> discountStrategies;
    private final List<DiscountStrategy> appliedStrategies = new ArrayList<>();

    public DiscountManager(List<DiscountStrategy> discountStrategies, Order order) {
        this.discountStrategies = discountStrategies;
        configure(order);
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

    private boolean isAbleToGetBenefit(double totalPriceBeforeDiscount) {
        return totalPriceBeforeDiscount >= STANDARD_BENEFIT_PRICE;
    }

    private void configure(Order order) {
        if (isAbleToGetBenefit(order.getOrderMenu().getTotalPriceBeforeDiscount())) {
            discountStrategies.stream()
                .filter(discountStrategy -> discountStrategy.isApply(order))
                .forEach(appliedStrategies::add);
        }
    }
}
