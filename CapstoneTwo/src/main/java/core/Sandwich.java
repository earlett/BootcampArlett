package core;

import model.Bread;
import model.Toppings;

import java.util.ArrayList;
import java.util.List;

public class Sandwich {
    private final Bread bread;
    private final int size;
    private final List<Toppings> toppings;
    private boolean toasted;

    public Sandwich(Bread bread, int size) {
        this.bread = bread;
        this.size = size;
        this.toppings = new ArrayList<>();
    }

    // Constructor for backward compatibility
    public Sandwich(String breadType, int size) {
        this.bread = Bread.fromString(breadType);
        this.size = size;
        this.toppings = new ArrayList<>();
    }

    public void setToasted(boolean toasted) {
        this.toasted = toasted;
    }

    public void addToppings(Toppings topping) {
        toppings.add(topping);
    }

    public Bread getBread() {
        return bread;
    }

    public int getSize() {
        return size;
    }

    public boolean isToasted() {
        return toasted;
    }

    public List<Toppings> getToppings() {
        return new ArrayList<>(toppings); // Return defensive copy
    }

    public double calculateCost() {
        double basePrice = switch (size) {
            case 4 -> 5.50;
            case 8 -> 7.00;
            case 12 -> 8.50;
            default -> 0.0;
        };

        double toppingsCost = toppings.stream().mapToDouble(Toppings::getCost).sum();
        return basePrice + toppingsCost;
    }

    public String getSummary() {
        StringBuilder sb = new StringBuilder();
        sb.append("Bread: ").append(bread.getName()).append("\n");
        sb.append("Size: ").append(size).append("\"\n");
        sb.append("Toasted: ").append(toasted ? "Yes" : "No").append("\n");
        sb.append("Toppings: \n");
        for (Toppings t : toppings) {
            sb.append("  - ").append(t).append("\n");
        }
        sb.append("Cost: $").append(String.format("%.2f", calculateCost()));
        return sb.toString();
    }
}