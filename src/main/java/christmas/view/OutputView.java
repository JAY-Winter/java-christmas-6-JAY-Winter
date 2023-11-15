package christmas.view;

import christmas.dto.DiscountDetailDto;
import christmas.dto.OrderMenuDto;
import christmas.model.Badge;
import christmas.model.giveaway.Giveaway;
import christmas.model.order.Order;
import christmas.model.order.OrderMenu;
import christmas.model.order.OrderMenuItem;
import christmas.model.payment.DiscountDetail;
import christmas.model.payment.DiscountManager;
import christmas.util.PriceFormat;
import java.util.List;

public class OutputView {

    public static void displayOrderedMenu(List<OrderMenuDto> orderMenuDtos) {
        System.out.println("<주문 메뉴>");
        for (OrderMenuDto dto : orderMenuDtos) {
            System.out.println(dto);
        }
        System.out.println();
    }


    public static void displayDiscountDetails(List<DiscountDetail> discountDetails) {
        System.out.println("<혜택 내역>");
        for (DiscountDetail discountDetail : discountDetails) {
            System.out.println(discountDetail);
        }

        System.out.println();
    }


    public void printOrderProcess(Order order) {
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
            OrderMenuDto orderMenuDto
                = new OrderMenuDto(orderMenuItem.getMenu().getName(),
                orderMenuItem.getQuantity());

            System.out.println(orderMenuDto);
        }
        System.out.println();
    }

    public static void printTotalPriceBeforeDiscount(double totalPriceBeforeDiscount) {
        System.out.println("<할인 전 총주문 금액>");
        System.out.println(PriceFormat.formatPrice(totalPriceBeforeDiscount));
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
        Giveaway test = Giveaway.getModel(totalPriceBeforeDiscount);
        System.out.println(test.getFormattedOutput());
        System.out.println();
    }

    public static void printDiscountDetails(List<DiscountDetail> discountDetails) {
        System.out.println("<혜택 내역>");
        for (DiscountDetail discountDetail : discountDetails) {
            DiscountDetailDto discountDetailDto = new DiscountDetailDto(
                discountDetail.getDescription(), discountDetail.getAmount());
            System.out.println(discountDetailDto);
        }
        System.out.println();
    }

    public static void printTotalDiscountPrice(double totalDiscountPrice) {
        System.out.println("<총혜택 금액>");
        System.out.println(formatDiscountPrice(totalDiscountPrice));
        System.out.println();
    }

    public static void printExpectedPrice(double expectedPrice) {
        System.out.println("<할인 후 예상 결제 금액>");
        System.out.println(PriceFormat.formatPrice(expectedPrice));
        System.out.println();
    }

    public static void printBadge(double totalDiscountPrice) {
        System.out.println("<12월 이벤트 배지>");
        String badge = Badge.getBadge(totalDiscountPrice);
        System.out.println(badge);
        System.out.println();
    }

    private static String formatDiscountPrice(double totalDiscountPrice) {
        return totalDiscountPrice == 0 ? PriceFormat.formatPrice(totalDiscountPrice)
            : "-" + PriceFormat.formatPrice(totalDiscountPrice);
    }
}
