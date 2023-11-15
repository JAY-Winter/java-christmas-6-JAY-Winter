package christmas;

import christmas.model.order.VisitDate;
import christmas.model.payment.SpecialDays;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class VisitDateTest {

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
}