package christmas.controller;

import christmas.dto.DiscountDetailDto;
import christmas.dto.OrderMenuDto;
import christmas.model.benefit.NoneStrategy;
import christmas.model.order.Order;
import christmas.model.order.OrderMenu;
import christmas.model.order.OrderMenuItem;
import christmas.model.payment.DiscountDetail;
import christmas.model.payment.DiscountManager;
import christmas.util.Retry;
import christmas.view.InputView;
import christmas.view.OutputView;
import java.util.ArrayList;
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
        DiscountManager discountManager = new DiscountManager();
        discountManager.configureDiscountStrategies(order);

        // 주문 메뉴

        OutputView.displayOrderedMenu(getOrderMenuDtos(order.getOrderMenu()));
        // 할인 전 총주문 금액
        OutputView.printTotalPriceBeforeDiscount(

        order.getOrderMenu().getTotalPriceBeforeDiscount());
        // 증정 메뉴
        OutputView.printGiveaway(order.getOrderMenu().getTotalPriceBeforeDiscount());

        // 혜택 내역
        processDiscountDetails(discountManager.getDiscountDetails(order));

        getDiscountDetails(discountManager.getDiscountDetails(order));

        // 총혜택 금액
        OutputView.printTotalDiscountPrice(discountManager.applyDiscounts(order));

        // 할인 후 예상 결제 금액
        OutputView.printExpectedPrice(
            order.getOrderMenu().getTotalPriceBeforeDiscount() - discountManager.applyDiscounts(
                order));

        // 12월 이벤트 배지
        OutputView.printBadge(discountManager.applyDiscounts(order));
    }

    public void processDiscountDetails(List<DiscountDetail> discountDetails) {
        if (discountDetails.isEmpty()) {
            discountDetails.add(new DiscountDetail("없음", 0, new NoneStrategy()));
        }
        OutputView.displayDiscountDetails(discountDetails);
    }


    private void processOrder(Order order) {
        outputView.printOrderProcess(order);
    }

    public List<OrderMenuDto> getOrderMenuDtos(OrderMenu orderMenu) {
        List<OrderMenuDto> dtos = new ArrayList<>();
        for (OrderMenuItem item : orderMenu.getOrderMenus()) {
            OrderMenuDto dto = new OrderMenuDto(item.getMenu().getName(), item.getQuantity());
            dtos.add(dto);
        }
        return dtos;
    }

    public List<DiscountDetailDto> getDiscountDetails(List<DiscountDetail> discountDetails) {
        List<DiscountDetailDto> dtos = new ArrayList<>();
        for (DiscountDetail discountDetail : discountDetails) {
            DiscountDetailDto dto = new DiscountDetailDto(
                discountDetail.getDescription(), discountDetail.getAmount());
            dtos.add(dto);
        }
        return dtos;
    }
}
