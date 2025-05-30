package builders;

import model.BLTWrapEviesWay;
import model.Italian;
import core.Sandwich;
import model.SignatureVeggieSandwich;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SignatureSandwichManager {
    private static final Map<String, String> SIGNATURE_SANDWICHES = new HashMap<>();

    static {
        SIGNATURE_SANDWICHES.put("blt", "BLT Wrap Evie's Way");
        SIGNATURE_SANDWICHES.put("italian", "Italian");
        SIGNATURE_SANDWICHES.put("veggie", "Signature Veggie Sandwich");
    }

    public static Sandwich createSignatureSandwich(String type, int size) {
        String normalizedType = type.toLowerCase().trim();

        return switch (normalizedType) {
            case "blt", "blt wrap", "evies way" -> new BLTWrapEviesWay(size);
            case "italian" -> new Italian(size);
            case "veggie", "veggie sandwich" -> new SignatureVeggieSandwich(size);
            default -> null;
        };
    }

    public static void displaySignatureSandwiches() {
        System.out.println("\n=== Signature Sandwiches ===");
        System.out.println("1. " + BLTWrapEviesWay.getDescription());
        System.out.println("2. " + Italian.getDescription());
        System.out.println("3. " + SignatureVeggieSandwich.getDescription());
        System.out.println("\nAll signature sandwiches can be customized by adding or removing toppings.");
    }

    public static Set<String> getAvailableTypes() {
        return SIGNATURE_SANDWICHES.keySet();
    }

    public static boolean isValidSignatureSandwich(String type) {
        String normalizedType = type.toLowerCase().trim();
        return SIGNATURE_SANDWICHES.containsKey(normalizedType) ||
                normalizedType.equals("blt wrap") ||
                normalizedType.equals("evies way") ||
                normalizedType.equals("veggie sandwich");
    }
}