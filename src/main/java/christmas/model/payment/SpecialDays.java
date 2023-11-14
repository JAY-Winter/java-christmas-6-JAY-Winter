package christmas.model.payment;

import christmas.model.order.VisitDate;
import java.util.Arrays;

public enum SpecialDays {

    DAY_3(3), DAY_10(10), DAY_17(17), DAY_24(24), DAY_25(25), DAY_31(31);

    private final int dayOfMonth;

    SpecialDays(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public static boolean contains(VisitDate visitdate) {
        int dayofMonth = visitdate.getLocalDate().getDayOfMonth();
        return Arrays.stream(SpecialDays.values())
            .anyMatch(specialDay -> specialDay.getDayOfMonth() == dayofMonth);
    }
}
