package christmas.model;

import java.time.LocalDate;
import java.time.Month;

public class DiscountCalculator {

    private static final LocalDate START_DATE = LocalDate.of(2023, Month.DECEMBER, 1);
    private static final LocalDate END_DATE = LocalDate.of(2023, Month.DECEMBER, 25);
    private static final int START_DISCOUNT = 1000;
    private static final int DAILY_INCREMENT = 100;

    public static double calculateDiscount(LocalDate date) {
        if (!isWithinEventPeriod(date)) {
            return 0;
        }
        return START_DISCOUNT + DAILY_INCREMENT * (date.getDayOfMonth()
            - START_DATE.getDayOfMonth());
    }

    private static boolean isWithinEventPeriod(LocalDate date) {
        return !date.isBefore(START_DATE) && !date.isAfter(END_DATE);
    }
}
