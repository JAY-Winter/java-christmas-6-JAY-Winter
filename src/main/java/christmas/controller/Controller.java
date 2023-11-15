package christmas.controller;

import christmas.dto.OrderMenuDto;
import christmas.model.order.Order;
import christmas.model.order.OrderMenu;
import christmas.model.order.OrderMenuItem;
import christmas.model.payment.DiscountManager;
import christmas.model.payment.DiscountStrategy;
import christmas.model.payment.GiveawayDiscount;
import christmas.model.payment.SpecialDiscount;
import christmas.model.payment.WeekdayDiscount;
import christmas.model.payment.WeekendDiscount;
import christmas.util.Retry;
import christmas.view.InputView;
import christmas.view.OutputView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Controller {

    private final InputView inputView;
    private final OutputView outputView;

    public Controller(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        Order order = Retry.retryOnException(inputView::inputOrder);

        List<DiscountStrategy> discountStrategies;
        discountStrategies = new ArrayList<>(
            Arrays.asList(new GiveawayDiscount(), new SpecialDiscount(), new WeekendDiscount(),
                new WeekdayDiscount()));

        DiscountManager discountManager = new DiscountManager(discountStrategies);
        discountManager.configure(order);

        processOrderOverView(order);

        processDiscountedOrderView(order, discountManager);
    }

    private void processOrderOverView(Order order) {
        // 혜택 미리보기
        outputView.printEventBenefits(order.getVisitDate().getLocalDate().getDayOfMonth());

        // 주문 메뉴
        outputView.displayOrderedMenu(getOrderMenuDtos(order.getOrderMenu()));

        // 할인 전 총주문 금액
        outputView.printTotalPriceBeforeDiscount(
            order.getOrderMenu().getTotalPriceBeforeDiscount());
    }

    private void processDiscountedOrderView(Order order, DiscountManager discountManager) {
        // 증정 메뉴
        outputView.printGiveaway(order.getOrderMenu().getTotalPriceBeforeDiscount());

        // 혜택 내역
        outputView.displayDiscountDetails(discountManager.getDiscountDetails(order));

        // 총혜택 금액
        outputView.printTotalDiscountPrice(discountManager.calculateTotalDiscountPrice(order));

        // 할인 후 예상 결제 금액
        outputView.printExpectedPrice(getExpectedPrice(order, discountManager));

        // 12월 이벤트 배지
        outputView.printBadge(discountManager.calculateTotalDiscountPrice(order));
    }

    private List<OrderMenuDto> getOrderMenuDtos(OrderMenu orderMenu) {
        List<OrderMenuDto> dtos = new ArrayList<>();
        for (OrderMenuItem item : orderMenu.getOrderMenus()) {
            OrderMenuDto dto = new OrderMenuDto(item.getMenu().getName(), item.getQuantity());
            dtos.add(dto);
        }
        return dtos;
    }

    private static double getExpectedPrice(Order order, DiscountManager discountManager) {
        return order.getOrderMenu().getTotalPriceBeforeDiscount()
            - discountManager.calculateTotalDiscountPrice(
            order);
    }
}
