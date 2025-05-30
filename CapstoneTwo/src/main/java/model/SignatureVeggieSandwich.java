package model;

import core.Sandwich;

public class SignatureVeggieSandwich extends Sandwich {

    public SignatureVeggieSandwich(int size) {
        super(Bread.RYE, size);
        setupDefaultToppings();
        setToasted(false);
    }

    private void setupDefaultToppings() {
        // Add cheeses
        addToppings(new Cheese("cheddar", getSize()));
        addToppings(new Cheese("swiss", getSize()));

        // Add regular toppings
        addToppings(new RegularToppings("peppers"));
        addToppings(new RegularToppings("lettuce"));
        addToppings(new RegularToppings("tomatoes"));
        addToppings(new RegularToppings("jalapeños"));
        addToppings(new RegularToppings("cucumbers"));

        // Add sauce
        addToppings(new Sauces("thousand islands"));
    }

    public static String getDescription() {
        return "Signature Veggie Sandwich: Rye bread with cheddar, swiss, peppers, lettuce, tomatoes, jalapeños, cucumbers, thousand islands";
    }
}