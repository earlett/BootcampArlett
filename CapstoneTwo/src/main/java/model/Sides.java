package model;

import java.util.List;

public class Sides extends Toppings {
    // Available side options from your requirements
    public static final List<String> SIDE_OPTIONS = List.of(
            "au jus", "sauce"
    );

    public Sides(String name) {
        super(name.toLowerCase().trim(), "side", 0.0);

        if (!SIDE_OPTIONS.contains(name.toLowerCase().trim())) {
            throw new IllegalArgumentException("Invalid side option: " + name +
                    ". Available sides: " + SIDE_OPTIONS);
        }
    }

}