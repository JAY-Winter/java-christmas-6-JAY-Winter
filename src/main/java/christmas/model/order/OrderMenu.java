package christmas.model.order;

import christmas.model.menu.AppetizerMenu;
import christmas.model.menu.DessertMenu;
import christmas.model.menu.DrinkMenu;
import christmas.model.menu.MainMenu;
import christmas.model.menu.Menu;
import christmas.util.ErrorMessage;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OrderMenu {

    private static final String NOT_FOUND_MENU_ERROR = "메뉴를 찾을 수 없습니다.";
    private static final String INVALID_MENU_ERROR = "유효하지 않은 주문입니다. 다시 입력해 주세요.";
    private static final String MINIMUM_QUANTITY_ERROR = "주문은 최소 1개 부터 가능합니다. 다시 입력해 주세요.";
    private static final String MAXIMUM_QUANTITY_ERROR = "주문 가능 개수는 20개 입니다. 다시 입력해 주세요.";
    private static final String ONLY_DRINK_MENU_ERROR = "음료만 주문 시 주문할 수 없습니다. 다시 입력해 주세요.";
    private static final String VALID_ORDER_INPUT_REGEX = "([가-힣a-zA-Z]+-\\d+)(,[가-힣a-zA-Z]+-\\d+)*";
    private final List<OrderMenuItem> orderMenus;

    public OrderMenu(List<OrderMenuItem> orderMenuItems) {
        this.orderMenus = orderMenuItems;
    }

    public static OrderMenu from(String orderMenus) {
        validate(orderMenus);
        return new OrderMenu(convert(getParsedInputWithComma(orderMenus)));
    }

    private static List<OrderMenuItem> convert(String[] orderMenus) {
        return Arrays.stream(orderMenus)
            .map(orderMenu -> {
                String[] parts = orderMenu.split("-");
                String name = parts[0].trim();
                int quantity = Integer.parseInt(parts[1].trim());
                return new OrderMenuItem(getMenu(name), quantity);
            })
            .collect(Collectors.toList());
    }

    private static Menu getMenu(String menuName) {
        Stream<Menu> combinedStream = getAllMenuItems();
        return combinedStream
            .filter(menu -> menu.getName().equals(menuName))
            .findFirst()
            .orElseThrow(() -> new ErrorMessage(NOT_FOUND_MENU_ERROR));
    }


    private static void validate(String items) {
        validateSpecialCharacter(items);
        String[] parsedItems = getParsedInputWithComma(items);
        validateOnlyDrink(parsedItems);
        validateValidMenu(parsedItems);
        validateDuplicateMenu(parsedItems);
        validateMinimumQuantity(parsedItems);
        validateMaximumQuantity(parsedItems);
    }

    private static String[] getParsedInputWithComma(String input) {
        validateSpecialCharacter(input);
        return input.trim().split(",");
    }

    private static void validateSpecialCharacter(String input) {
        if (!input.matches(VALID_ORDER_INPUT_REGEX)) {
            throw new ErrorMessage(INVALID_MENU_ERROR);
        }
    }

    private static Stream<Menu> getAllMenuItems() {
        return Stream.of(AppetizerMenu.values(), MainMenu.values(), DrinkMenu.values(),
                DessertMenu.values())
            .flatMap(Arrays::stream);
    }

    private static void validateValidMenu(String[] items) {
        for (String item : items) {
            String name = item.split("-")[0].trim();

            boolean isValid = getAllMenuItems()
                .map(Menu::getName)
                .anyMatch(menuName -> menuName.equals(name));

            if (!isValid) {
                throw new ErrorMessage(INVALID_MENU_ERROR);
            }
        }
    }

    private static void validateDuplicateMenu(String[] items) {
        Set<String> menuNames = new HashSet<>();

        Arrays.stream(items)
            .map(item -> item.split("-")[0].trim())
            .filter(name -> !menuNames.add(name))
            .findFirst()
            .ifPresent(name -> {
                throw new ErrorMessage(INVALID_MENU_ERROR);
            });
    }

    private static void validateMinimumQuantity(String[] items) {
        int MINIMUM_QUANTITY = 1;
        for (String item : items) {
            String[] parts = item.split("-");
            int quantity = Integer.parseInt(parts[1].trim());

            if (quantity < MINIMUM_QUANTITY) {
                throw new ErrorMessage(MINIMUM_QUANTITY_ERROR);
            }
        }
    }

    private static void validateMaximumQuantity(String[] items) {
        int MAXIMUM_QUANTITY = 20;
        int total_quantity = 0;
        for (String item : items) {
            String[] parts = item.split("-");
            int quantity = Integer.parseInt(parts[1].trim());
            total_quantity += quantity;
        }

        if (total_quantity > MAXIMUM_QUANTITY) {
            throw new ErrorMessage(MAXIMUM_QUANTITY_ERROR);
        }
    }

    private static void validateOnlyDrink(String[] items) {
        Set<String> drinkMenuNames = Arrays.stream(DrinkMenu.values())
            .map(DrinkMenu::getName)
            .collect(Collectors.toSet());

        boolean allItemsAreDrinks = Arrays.stream(items)
            .map(item -> item.split("-")[0])
            .allMatch(drinkMenuNames::contains);

        if (allItemsAreDrinks) {
            throw new ErrorMessage(ONLY_DRINK_MENU_ERROR);
        }
    }

    public List<OrderMenuItem> getOrderMenus() {
        return orderMenus;
    }

    public double getTotalPriceBeforeDiscount() {
        double total_price_before_discount = 0;
        for (OrderMenuItem orderMenuItem : orderMenus) {
            total_price_before_discount +=
                orderMenuItem.getMenu().getPrice() * orderMenuItem.getQuantity();
        }
        return total_price_before_discount;
    }

    public int getMainMenuCount() {
        return orderMenus.stream()
            .filter(item -> item.getMenu() instanceof MainMenu)
            .mapToInt(OrderMenuItem::getQuantity)
            .sum();
    }

    public int getDessertMenuCount() {
        return orderMenus.stream()
            .filter(item -> item.getMenu() instanceof DessertMenu)
            .mapToInt(OrderMenuItem::getQuantity)
            .sum();
    }
}
