package model;

public class Chips {
    private static final double COST = 1.50;

    public double getCost() {
        return COST;
    }

    @Override
    public String toString() {
        return "Chips ($" + String.format("%.2f", COST) + ")";
    }
}

