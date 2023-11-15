package christmas.model;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import christmas.model.order.OrderMenu;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class OrderMenuTest {

    @Test
    void 메뉴판에_존재하는_메뉴만_입력했을시() {
        String validMenus = "해산물파스타-2,레드와인-1,초코케이크-1";
        assertThatNoException().isThrownBy(() -> {
            OrderMenu.from(validMenus);
        });
    }

    @Test
    void 고객이_메뉴판에_없는_메뉴를_입력한_경우() {
        String invalidMenus = "불고기파스타-1,해산물파스타-1,레드와인-2";
        assertThatThrownBy(() -> OrderMenu.from(invalidMenus))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 음료만_주문한_경우() {
        String invalidMenus = "제로콜라-1,레드와인-2";
        assertThatThrownBy(() -> OrderMenu.from(invalidMenus))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 최대주문_개수는_20개입니다() {
        String invalidMenus = "해산물파스타-2,레드와인-10,초코케이크-10";
        assertThatThrownBy(() -> OrderMenu.from(invalidMenus))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 중복_메뉴를_입력한_경우() {
        String invalidMenus = "해산물파스타-2,레드와인-2,초코케이크-2,레드와인-2,초코케이크-2";
        assertThatThrownBy(() -> OrderMenu.from(invalidMenus))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @MethodSource("provideInvalidInputs")
    void 입력_메뉴_형식이_예시와_다른_경우(String input) {
        assertThatThrownBy(() -> OrderMenu.from(input))
            .isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream<String> provideInvalidInputs() {
        return Stream.of(
            "해산물파스타-ㄱㄴㄷ",
            "해산물파스타-0,레드와인-0,초코케이크-0",
            "해산물파스타**2,레드와인%^1,초코케이크-1"
        );
    }
}