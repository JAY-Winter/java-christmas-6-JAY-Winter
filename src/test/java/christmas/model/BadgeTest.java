package christmas.model;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class BadgeTest {

    @ParameterizedTest
    @CsvSource({
        "20000,산타",
        "10000,트리",
        "5000,별",
        "4900,없음"
    })
    void 뱃지_테스트(double price, String expected) {
        assertThat(Badge.getBadge(price)).isEqualTo(expected);
    }
}