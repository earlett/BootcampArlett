package model;

import pricing.Priceable;

public class Toppings implements Priceable {
    private String name;
    private String type;
    private double cost;

    public Toppings(String name, String type, double cost) {
        this.name = name;
        this.type = type;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    @Override
    public double getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return name + (cost > 0 ? " ($" + String.format("%.2f", cost) + ")" : "");
    }
}
