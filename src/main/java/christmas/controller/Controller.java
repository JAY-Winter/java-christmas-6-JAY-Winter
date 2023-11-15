package christmas.controller;

import christmas.model.order.Order;
import christmas.util.Retry;
import christmas.view.InputView;
import christmas.view.OutputView;

public class Controller {

    private final InputView inputView;
    private final OutputView outputView;

    public Controller(InputView inputView, OutputView outputView) {
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        Order order = Retry.retryOnException(inputView::inputOrder);
        processOrder(order);
    }

    private void processOrder(Order order) {
        outputView.printOrderProcess(order);
    }
}
