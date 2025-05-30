package model;

public class Cheese extends Toppings {
    public Cheese(String name, int size) {
        super(name, "cheese", calculateCheeseCost(size));
    }

    private static double calculateCheeseCost(int size) {
        return switch (size) {
            case 4 -> 0.75;
            case 8 -> 1.50;
            case 12 -> 2.25;
            default -> 0.0;
        };
    }
}
