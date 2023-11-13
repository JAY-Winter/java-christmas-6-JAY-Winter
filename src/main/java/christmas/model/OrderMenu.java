package christmas.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OrderMenu {

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
            .orElseThrow(() -> new IllegalArgumentException("[ERROR] 메뉴를 찾을 수 없습니다."));
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
        String[] items = input.split(",");
        return items;
    }

    private static void validateSpecialCharacter(String input) {
        String regex = "([가-힣a-zA-Z]+-\\d+)(,[가-힣a-zA-Z]+-\\d+)*";

        if (!input.matches(regex)) {
            throw new IllegalArgumentException("[ERROR] 메뉴 주문 형식에 맞게 주문해주세요.");
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
                throw new IllegalArgumentException("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
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
                throw new IllegalArgumentException("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
            });
    }

    private static void validateMinimumQuantity(String[] items) {
        int MINIMUM_QUANTITY = 1;
        for (String item : items) {
            String[] parts = item.split("-");
            int quantity = Integer.parseInt(parts[1].trim());

            if (quantity < MINIMUM_QUANTITY) {
                throw new IllegalArgumentException(
                    "[ERROR] 주문은 최소 1개 부터 가능합니다. 다시 입력해 주세요.");
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
            throw new IllegalArgumentException(
                "[ERROR] 주문 가능 개수는 20개 입니다. 다시 입력해 주세요.");
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
            throw new IllegalArgumentException("[ERROR] 음료만 주문 시 주문할 수 없습니다. 다시 입력해 주세요.");
        }
    }

    public List<OrderMenuItem> getOrderMenus() {
        return orderMenus;
    }

    public double getTotalPriceBeforeDiscount(OrderMenu orderMenuItems) {
        double total_price_before_discount = 0;
        for (OrderMenuItem orderMenuItem : orderMenuItems.getOrderMenus()) {
            total_price_before_discount +=
                orderMenuItem.getMenu().getPrice() * orderMenuItem.getQuantity();
        }
        return total_price_before_discount;
    }
}
