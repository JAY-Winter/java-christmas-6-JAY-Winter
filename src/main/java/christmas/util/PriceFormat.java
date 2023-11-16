package christmas.util;

public class PriceFormat {

    public static String formatPrice(double price) {
        return String.format("%,.0f원", price);
    }
}
