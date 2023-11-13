package christmas.model;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class GiveawayTest {

    @ParameterizedTest
    @CsvSource({
        "119000, 없음",
        "120000, 샴페인",
        "200000, 샴페인"
    })
    void 증정_테스트(double price, String expected) {
        assertThat(Giveaway.getGiveaway(price)).isEqualTo(expected);
    }

}