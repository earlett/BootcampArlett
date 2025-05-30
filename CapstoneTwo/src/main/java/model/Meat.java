package model;

public class Meat extends Toppings {

    public Meat(String name, double cost) {
        super(name, "meat", cost);
    }

    public Meat(String name, int size) {
        super(name, "meat", calculateMeatCost(size));
    }

    private static double calculateMeatCost(int size) {
        return switch (size) {
            case 4 -> 1.00;
            case 8 -> 2.00;
            case 12 -> 3.00;
            default -> 0.0;
        };
    }

    @Override
    public String toString() {
        return getName() + (getCost() > 0 ? " ($" + String.format("%.2f", getCost()) + ")" : "");
    }
}