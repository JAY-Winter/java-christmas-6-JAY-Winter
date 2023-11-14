package christmas.model.payment;

import christmas.model.menu.Giveaway;
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
            List.of(new DiscountDetail("증정 이벤트", calculateDiscount(order))));
    }
}
