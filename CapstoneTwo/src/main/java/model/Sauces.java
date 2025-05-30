package model;

import java.util.List;

public class Sauces extends Toppings {
    private static final List<String> SAUCE_OPTIONS = List.of(
            "mayo", "mustard", "ketchup", "ranch", "thousand islands", "vinaigrette"
    );

    public Sauces(String name) {
        super(name.toLowerCase().trim(), "sauce", 0.0);
        if (!SAUCE_OPTIONS.contains(name.toLowerCase().trim())) {
            throw new IllegalArgumentException("Invalid sauce: " + name);
        }
    }
}