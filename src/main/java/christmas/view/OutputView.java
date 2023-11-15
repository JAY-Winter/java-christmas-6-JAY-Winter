package christmas.view;

import christmas.model.Badge;
import christmas.model.giveaway.Giveaway;
import christmas.model.order.OrderMenu;
import christmas.model.payment.DiscountDetail;
import christmas.util.PriceFormat;
import java.util.List;

public class OutputView {

    public void printEventBenefits(int day) {
        System.out.printf("12월 %d일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!\n", day);
        System.out.println();
    }

    public void displayOrderedMenu(OrderMenu orderMenu) {
        System.out.println("<주문 메뉴>");
        orderMenu.getOrderMenus().forEach(System.out::println);
        System.out.println();
    }

    public void displayDiscountDetails(List<DiscountDetail> discountDetails) {
        System.out.println("<혜택 내역>");
        discountDetails.forEach(System.out::println);
        System.out.println();
    }

    public void printTotalPriceBeforeDiscount(double totalPriceBeforeDiscount) {
        System.out.println("<할인 전 총주문 금액>");
        System.out.println(PriceFormat.formatPrice(totalPriceBeforeDiscount));
        System.out.println();
    }

    public void printGiveaway(double totalPriceBeforeDiscount) {
        System.out.println("<증정 메뉴>");
        Giveaway test = Giveaway.getModel(totalPriceBeforeDiscount);
        System.out.println(test.getFormattedOutput());
        System.out.println();
    }

    public void printTotalDiscountPrice(double totalDiscountPrice) {
        System.out.println("<총혜택 금액>");
        System.out.println(formatDiscountPrice(totalDiscountPrice));
        System.out.println();
    }

    public void printExpectedPrice(double expectedPrice) {
        System.out.println("<할인 후 예상 결제 금액>");
        System.out.println(PriceFormat.formatPrice(expectedPrice));
        System.out.println();
    }

    public void printBadge(double totalDiscountPrice) {
        System.out.println("<12월 이벤트 배지>");
        System.out.println(Badge.getBadge(totalDiscountPrice));
        System.out.println();
    }

    private String formatDiscountPrice(double totalDiscountPrice) {
        return totalDiscountPrice == 0 ? PriceFormat.formatPrice(totalDiscountPrice)
            : "-" + PriceFormat.formatPrice(totalDiscountPrice);
    }
}
