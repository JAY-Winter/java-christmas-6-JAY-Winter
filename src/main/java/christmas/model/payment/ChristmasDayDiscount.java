package christmas.model.payment;

import christmas.model.benefit.DefaultStrategy;
import christmas.model.order.Order;
import christmas.model.order.VisitDate;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class ChristmasDayDiscount implements DiscountStrategy {

    private static final LocalDate START_DATE = LocalDate.of(2023, Month.DECEMBER, 1);
    private static final LocalDate END_DATE = LocalDate.of(2023, Month.DECEMBER, 25);
    private static final int START_DISCOUNT = 1000;
    private static final int DAILY_INCREMENT = 100;


    @Override
    public double calculateDiscount(Order order) {
        LocalDate orderDate = order.getVisitDate().getLocalDate();
        double totalDiscount = 0;

        if (isWithinEventPeriod(order.getVisitDate())) {
            int daysSinceStart = orderDate.getDayOfMonth() - START_DATE.getDayOfMonth();
            double discount = START_DISCOUNT + DAILY_INCREMENT * daysSinceStart;
            totalDiscount += discount;
        }

        return totalDiscount;
    }

    @Override
    public List<DiscountDetail> getDiscountDetails(Order order) {
        List<DiscountDetail> details = new ArrayList<>();
        LocalDate orderDate = order.getVisitDate().getLocalDate();

        if (isWithinEventPeriod(order.getVisitDate())) {
            int daysSinceStart = orderDate.getDayOfMonth() - START_DATE.getDayOfMonth();
            double discount = START_DISCOUNT + DAILY_INCREMENT * daysSinceStart;
            details.add(
                new DiscountDetail(DiscountDescription.CHRISTMAS_DDAY.getDescription(), discount,
                    new DefaultStrategy()));
        }

        return details;
    }

    @Override
    public boolean isApply(Order order) {
        return SpecialDays.contains(order.getVisitDate());
    }

    private static boolean isWithinEventPeriod(VisitDate visitDate) {
        return !visitDate.getLocalDate().isBefore(START_DATE) && !visitDate.getLocalDate()
            .isAfter(END_DATE);
    }
}
