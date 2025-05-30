package builders;

import core.Sandwich;
import model.Toppings;

import java.util.ArrayList;
import java.util.List;

public class SandwichBuilder {
    private String bread;
    private int size;
    private List<Toppings> toppings = new ArrayList<>();
    private boolean toasted = false;

    public SandwichBuilder setBread(String bread) {
        this.bread = bread;
        return this;
    }

    public SandwichBuilder setSize(int size) {
        this.size = size;
        return this;
    }

    public SandwichBuilder addTopping(Toppings topping) {
        this.toppings.add(topping);
        return this;
    }

    public Sandwich build() {
        Sandwich sandwich = new Sandwich(bread, size);
        sandwich.setToasted(toasted);
        toppings.forEach(sandwich::addToppings);
        return sandwich;
    }
}
