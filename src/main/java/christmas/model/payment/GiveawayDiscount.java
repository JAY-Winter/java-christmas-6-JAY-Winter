package christmas.model.payment;

import christmas.model.benefit.DefaultStrategy;
import christmas.model.giveaway.Giveaway;
import christmas.model.order.Order;
import java.util.ArrayList;
import java.util.List;

public class GiveawayDiscount implements DiscountStrategy {

    @Override
    public double calculateDiscount(Order order) {
        String giveaway = Giveaway.getGiveaway(order.getOrderMenu().getTotalPriceBeforeDiscount());
        return Giveaway.getStandardPriceByName(giveaway);
    }

    @Override
    public List<DiscountDetail> getDiscountDetails(Order order) {
        return new ArrayList<>(
            List.of(new DiscountDetail(DiscountDescription.DISCOUNT_DESCRIPTION.getDescription(), calculateDiscount(order),
                new DefaultStrategy())));
    }

    @Override
    public boolean isApply(Order order) {
        return Giveaway.isEligibleForGiveaway(order.getOrderMenu().getTotalPriceBeforeDiscount());
    }
}
