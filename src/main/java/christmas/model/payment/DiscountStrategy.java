package christmas.model.payment;

import christmas.model.order.Order;
import java.util.List;

public interface DiscountStrategy {

    double calculateDiscount(Order order);

    List<DiscountDetail> getDiscountDetails(Order order);
}
