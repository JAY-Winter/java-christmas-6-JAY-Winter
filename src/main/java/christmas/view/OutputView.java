package christmas.view;

import christmas.model.Badge;
import christmas.model.menu.Giveaway;
import christmas.model.order.Order;
import christmas.model.order.OrderMenu;
import christmas.model.order.OrderMenuItem;
import christmas.model.payment.DiscountDetail;
import christmas.model.payment.DiscountManager;
import java.util.List;
import java.util.Optional;

public class OutputView {

    public static void printOrderProcess(Order order) {
        printEventBenefits();
        printOrderedMenu(order.getOrderMenu());
        printOrderPricing(order);
        printDiscountDetails(order);
        printFinalPrice(order);
    }

    public static void printEventBenefits() {
        System.out.println("12월 26일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!");
    }

    public static void printOrderedMenu(OrderMenu orderMenu) {
        System.out.println("<주문 메뉴>");
        for (OrderMenuItem orderMenuItem : orderMenu.getOrderMenus()) {
            System.out.println(
                orderMenuItem.getMenu().getName() + " " + orderMenuItem.getQuantity() + "개");
        }
        System.out.println();
    }

    public static void printTotalPriceBeforeDiscount(double totalPriceBeforeDiscount) {
        System.out.println("<할인 전 총주문 금액>");
        System.out.println(totalPriceBeforeDiscount + "원");
        System.out.println();
    }

    private static void printOrderPricing(Order order) {
        double totalPrice = order.getOrderMenu().getTotalPriceBeforeDiscount();
        OutputView.printTotalPriceBeforeDiscount(totalPrice);
        OutputView.printGiveaway(totalPrice);
    }

    private static void printDiscountDetails(Order order) {
        DiscountManager discountManager = new DiscountManager();
        discountManager.configureDiscountStrategies(order);
        OutputView.printDiscountDetails(discountManager.getDiscountDetails(order));
    }

    private static void printFinalPrice(Order order) {
        DiscountManager discountManager = new DiscountManager();
        double totalDiscount = discountManager.applyDiscounts(order);
        OutputView.printTotalDiscountPrice(totalDiscount);
        OutputView.printExpectedPrice(
            order.getOrderMenu().getTotalPriceBeforeDiscount() - totalDiscount);
        OutputView.printBadge(totalDiscount);
    }

    public static void printGiveaway(double totalPriceBeforeDiscount) {
        System.out.println("<증정 메뉴>");
        String giveaway = Giveaway.getGiveaway(totalPriceBeforeDiscount);
        System.out.println(giveaway);
        System.out.println();
    }

    public static void printDiscountDetails(List<DiscountDetail> discountDetails) {
        System.out.println("<혜택 내역>");
        Optional<String> details = discountDetails.stream()
            .map(DiscountDetail::toString)
            .reduce((detail1, detail2) -> detail1 + "\n" + detail2);

        System.out.println(details.orElse("없음"));
        System.out.println();
    }

    public static void printTotalDiscountPrice(double totalDiscountPrice) {
        System.out.println("<총혜택 금액>");
        System.out.println(formatDiscountPrice(totalDiscountPrice));
        System.out.println();
    }

    public static void printExpectedPrice(double expectedPrice) {
        System.out.println("<할인 후 예상 결제 금액>");
        System.out.println(expectedPrice + "원");
        System.out.println();
    }

    public static void printBadge(double totalDiscountPrice) {
        System.out.println("<12월 이벤트 배지>");
        String badge = Badge.getBadge(totalDiscountPrice);
        System.out.println(badge);
        System.out.println();
    }

    private static String formatDiscountPrice(double totalDiscountPrice) {
        return totalDiscountPrice == 0 ? totalDiscountPrice + "원" : "-" + totalDiscountPrice + "원";
    }
}
