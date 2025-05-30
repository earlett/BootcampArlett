package model;

import core.Sandwich;

public class BLTWrapEviesWay extends Sandwich {

    public BLTWrapEviesWay(int size) {
        super(Bread.WRAP, size);
        setupDefaultToppings();
        setToasted(true);
    }

    private void setupDefaultToppings() {
        // Add bacon
        addToppings(new Meat("bacon", getSize()));

        // Add cheese
        addToppings(new Cheese("swiss", getSize()));

        // Add regular toppings
        addToppings(new RegularToppings("lettuce"));
        addToppings(new RegularToppings("jalapeños"));
        addToppings(new RegularToppings("cucumbers"));
        addToppings(new RegularToppings("onions"));
        addToppings(new RegularToppings("tomatoes"));

        // Add sauce
        addToppings(new Sauces("vinaigrette"));
    }

    public static String getDescription() {
        return "BLT Wrap Evie's Way: Wrap with bacon, swiss cheese, lettuce, jalapeños, cucumbers, onions, tomatoes, vinaigrette (toasted)";
    }
}