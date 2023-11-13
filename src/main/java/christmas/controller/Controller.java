package christmas.controller;

import camp.nextstep.edu.missionutils.Console;
import christmas.Retry;
import christmas.model.OrderMenu;
import christmas.model.OrderMenuItem;
import christmas.model.VisitDate;

public class Controller {


    public void run() {
        VisitDate visitDate = Retry.retryOnException(() -> inputVisitDate());

        OrderMenu orderMenuItems = Retry.retryOnException(() -> inputOrderMenu());

        for (OrderMenuItem orderMenu : orderMenuItems.getOrderMenus()) {
            System.out.println("orderMenu = " + orderMenu.getMenu() + " : " + orderMenu.getQuantity() + " | " + orderMenu.getMenu().getPrice());
        }
    }


    private VisitDate inputVisitDate() {
        System.out.println("안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.");
        System.out.println("12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)");
        String input = Console.readLine();
        VisitDate visitDate = new VisitDate(input);
        return visitDate;
    }

    private OrderMenu inputOrderMenu() {
        System.out.println("주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)");
        String[] items = getParsedInputWithComma();
        return OrderMenu.from(items);
    }

    private String[] getParsedInputWithComma() {
        String input = Console.readLine();
        validateSpecialCharacter(input);
        String[] items = input.split(",");
        return items;
    }

    private void validateSpecialCharacter(String input) {
        String regex = "([가-힣a-zA-Z]+-\\d+)(,[가-힣a-zA-Z]+-\\d+)*";

        if (!input.matches(regex)) {
            throw new IllegalArgumentException("[ERROR] 메뉴 주문 형식에 맞게 주문해주세요.");
        }
    }
}
