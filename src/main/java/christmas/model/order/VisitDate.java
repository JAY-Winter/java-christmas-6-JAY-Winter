package christmas.model.order;

import christmas.util.ErrorMessage;
import java.time.LocalDate;

public class VisitDate {

    private static final String INVALID_DATE = "유효하지 않은 날짜입니다. 다시 입력해 주세요.";
    private static final int FIRST_DAY = 1;
    private static final int LAST_DAY = 31;
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
            throw new ErrorMessage(INVALID_DATE);
        }
    }

    private void validateRange(int date) {
        if (date < FIRST_DAY || date > LAST_DAY) {
            throw new ErrorMessage(INVALID_DATE);
        }
    }

    public LocalDate getLocalDate() {
        return localDate;
    }
}
