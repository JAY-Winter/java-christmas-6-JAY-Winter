package christmas;

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
}