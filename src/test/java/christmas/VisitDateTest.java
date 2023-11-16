package christmas;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import christmas.model.order.Order;
import christmas.model.order.OrderMenu;
import christmas.model.order.OrderMenuItem;
import christmas.model.order.VisitDate;
import christmas.model.payment.SpecialDays;
import christmas.model.payment.WeekdayDiscount;
import christmas.model.payment.WeekendDiscount;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class VisitDateTest {

    private List<OrderMenuItem> orderMenuItems = new ArrayList<>();

    @ParameterizedTest
    @ValueSource(strings = {"-1", "0", "32", "100", "1  "})
    void 방문날짜는_1_이상_31_이하여야합니다(String value) {
        assertThatThrownBy(() -> new VisitDate(value))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"3", "10", "17", "24", "25", "31"})
    void 방문날짜가_특별한_날일_경우(String value) {
        boolean result = SpecialDays.contains(new VisitDate(value));
        Assertions.assertThat(result).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "2", "4", "5", "30"})
    void 방문날짜가_특별한_날이_아닌_경우(String value) {
        boolean result = SpecialDays.contains(new VisitDate(value));
        Assertions.assertThat(result).isFalse();
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "2", "8", "9", "15", "16", "22", "23", "29", "30"})
    void 방문날짜가_주말인_경우(String value) {
        VisitDate visitDate = new VisitDate(value);
        Order order = new Order(new OrderMenu(orderMenuItems), visitDate);
        boolean result = new WeekendDiscount().isApply(order);
        Assertions.assertThat(result).isTrue();
    }

    @ParameterizedTest
    @ValueSource(strings = {"3", "4", "5", "6", "20", "31"})
    void 방문날짜가_주중인_경우(String value) {
        VisitDate visitDate = new VisitDate(value);
        Order order = new Order(new OrderMenu(orderMenuItems), visitDate);
        boolean result = new WeekdayDiscount().isApply(order);
        Assertions.assertThat(result).isTrue();
    }
}