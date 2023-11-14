package christmas.view;

import camp.nextstep.edu.missionutils.Console;
import christmas.model.order.Order;
import christmas.model.order.OrderMenu;
import christmas.model.order.VisitDate;
import christmas.util.Retry;

public class InputView {

    public static Order inputOrder() {
        VisitDate visitDate = Retry.retryOnException(InputView::inputVisitDate);
        OrderMenu orderMenu = Retry.retryOnException(InputView::inputOrderMenu);
        Console.close();
        return new Order(orderMenu, visitDate);
    }

    public static VisitDate inputVisitDate() {
        System.out.println("안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.");
        System.out.println("12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)");
        String input = Console.readLine();
        VisitDate visitDate = new VisitDate(input);
        return visitDate;
    }

    public static OrderMenu inputOrderMenu() {
        System.out.println("주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)");
        String input = Console.readLine();
        return OrderMenu.from(input);
    }
}
