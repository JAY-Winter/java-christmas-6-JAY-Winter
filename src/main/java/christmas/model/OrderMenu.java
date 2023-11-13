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

    public static OrderMenu from(String[] orderMenus) {
        validate(orderMenus);
        return new OrderMenu(convert(orderMenus));
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
        Stream<Menu> combinedStream = Stream.concat(Arrays.stream(MainMenu.values()),
            Stream.concat(Arrays.stream(DrinkMenu.values()),
                Arrays.stream(DessertMenu.values())));
        return combinedStream
            .filter(menu -> menu.getName().equals(menuName))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("[ERROR] 메뉴를 찾을 수 없습니다."));
    }


    private static void validate(String[] items) {
        validateValidMenu(items);
        validateDuplicateMenu(items);
        validateMinimumQuantity(items);
        validateMaximumQuantity(items);
    }

    private static void validateValidMenu(String[] items) {
        for (String item : items) {
            String name = item.split("-")[0].trim();

            boolean isValid = Stream.concat(Arrays.stream(MainMenu.values()),
                    Stream.concat(Arrays.stream(DrinkMenu.values()),
                        Arrays.stream(DessertMenu.values())))
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

    public List<OrderMenuItem> getOrderMenus() {
        return orderMenus;
    }
}
