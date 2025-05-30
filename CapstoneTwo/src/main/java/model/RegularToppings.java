package model;

import java.util.List;

public class RegularToppings extends Toppings {
    private static final List<String> REGULAR_TOPPING_OPTIONS = List.of(
            "lettuce", "peppers", "onions", "tomatoes", "jalapeños",
            "cucumbers", "pickles", "guacamole", "mushrooms"
    );

    public RegularToppings(String name) {
        super(name.toLowerCase().trim(), "regular", 0.0);
        if (!REGULAR_TOPPING_OPTIONS.contains(name.toLowerCase().trim())) {
            throw new IllegalArgumentException("Invalid regular topping: " + name);
        }
    }

    public static List<String> getAvailableToppings() {
        return REGULAR_TOPPING_OPTIONS;
    }
}