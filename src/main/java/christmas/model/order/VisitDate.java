package christmas.model.order;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class VisitDate {

    private final LocalDate localDate;

    public VisitDate(String visitDate) {
        int day = parseVisitDateIntoIntoInt(visitDate);
        LocalDate localDate = LocalDate.of(2023, 12, day);

        this.localDate = localDate;
    }

    private int parseVisitDateIntoIntoInt(String visitDate) {
        validateInt(visitDate);
        int parsedVisitDate = Integer.parseInt(visitDate);
        validateRange(parsedVisitDate);

        return parsedVisitDate;
    }

    private void validateInt(String visitDate) {
        try {
            Integer.parseInt(visitDate);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.");
        }
    }

    private void validateRange(int date) {
        if (date < 1 || date > 31) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.");
        }
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public boolean isWeekend() {
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        return dayOfWeek == DayOfWeek.FRIDAY || dayOfWeek == DayOfWeek.SATURDAY;
    }
}
