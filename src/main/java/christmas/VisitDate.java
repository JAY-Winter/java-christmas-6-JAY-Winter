package christmas;

public class VisitDate {

    private final int visitDate;

    public VisitDate(String visitDate) {
        this.visitDate = parseVisitDateIntoIntoInt(visitDate);
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
            System.out.println("[ERROR] 날짜 입력은 숫자만 가능합니다. 다시 입력해 주세요.");
        }
    }

    private void validateRange(int date) {
        if (date < 1 || date > 31) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.");
        }
    }
}
