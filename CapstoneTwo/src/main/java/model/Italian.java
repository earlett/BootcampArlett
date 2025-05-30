package model;

import core.Sandwich;

public class Italian extends Sandwich {

    public Italian(int size) {
        super(Bread.WHITE, size);
        setupDefaultToppings();
        setToasted(false);
    }

    private void setupDefaultToppings() {
        // Add meats
        addToppings(new Meat("ham", getSize()));
        addToppings(new Meat("salami", getSize()));

        // Add cheese
        addToppings(new Cheese("provolone", getSize()));

        // Add regular toppings
        addToppings(new RegularToppings("lettuce"));
        addToppings(new RegularToppings("onions"));
        addToppings(new RegularToppings("tomatoes"));

        // Add sauce
        addToppings(new Sauces("vinaigrette"));
    }

    public static String getDescription() {
        return "Italian: White bread with ham, salami, provolone cheese, lettuce, onions, tomatoes, vinaigrette";
    }
}