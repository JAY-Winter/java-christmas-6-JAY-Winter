package christmas.model.payment;

public enum DiscountDescription {
    GIVEAWAY("증정 이벤트"),
    CHRISTMAS_DDAY("크리스마스 디데이 할인"),
    SPECIAL_DAY("특별 할인"),
    WEEKDAY("평일 할인"),
    WEEKEND("주말 할인");

    private final String description;

    DiscountDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
