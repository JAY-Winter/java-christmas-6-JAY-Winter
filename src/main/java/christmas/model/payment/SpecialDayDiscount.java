package christmas.model.payment;

import christmas.model.benefit.DefaultStrategy;
import christmas.model.order.Order;
import java.util.ArrayList;
import java.util.List;

public class SpecialDayDiscount implements DiscountStrategy {

    private static final int SPECIAL_DAY_DISCOUNT = 1000;

    @Override
    public double calculateDiscount(Order order) {
        if (SpecialDays.contains(order.getVisitDate())) {
            return SPECIAL_DAY_DISCOUNT;
        }
        return 0;
    }

    @Override
    public List<DiscountDetail> getDiscountDetails(Order order) {
        List<DiscountDetail> details = new ArrayList<>();

        if (SpecialDays.contains(order.getVisitDate())) {
            details.add(new DiscountDetail(DiscountDescription.SPECIAL_DAY.getDescription(),
                SPECIAL_DAY_DISCOUNT, new DefaultStrategy()));
        }
        return details;
    }

    @Override
    public boolean isApply(Order order) {
        return SpecialDays.contains(order.getVisitDate());
    }
}
