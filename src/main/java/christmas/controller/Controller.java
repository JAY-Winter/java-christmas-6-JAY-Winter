package christmas.controller;

import christmas.model.order.Order;
import christmas.util.Retry;
import christmas.view.InputView;
import christmas.view.OutputView;

public class Controller {

    public void run() {
        Order order = Retry.retryOnException(InputView::inputOrder);
        processOrder(order);
    }

    private void processOrder(Order order) {
        OutputView.printOrderProcess(order);
    }
}
