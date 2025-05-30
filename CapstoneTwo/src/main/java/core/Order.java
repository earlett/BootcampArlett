package core;

import model.Chips;
import model.Drink;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private final List<Sandwich> sandwiches = new ArrayList<>();
    private final List<Drink> drinks = new ArrayList<>();
    private final List<Chips> chips = new ArrayList<>();

    public Order(Customer customer) {
        // Optionally store the customer
    }

    public void addSandwich(Sandwich sandwich) {
        sandwiches.add(sandwich);
    }

    public void addDrink(Drink drink) {
        drinks.add(drink);
    }

    public void addChips(Chips chip) {
        chips.add(chip);
    }
    public List<Chips> getChips() {
        return chips;
    }

    public List<Drink> getDrinks() {
        return drinks;
    }

    public List<Sandwich> getSandwiches() {
        return sandwiches;
    }

    public double calculateTotal() {
        double total = 0.0;
        for (Sandwich s : sandwiches) total += s.calculateCost();
        for (Drink d : drinks) total += d.getCost();
        for (Chips c : chips) total += c.getCost();
        return total;
    }


    public String getSummary() {
        StringBuilder sb = new StringBuilder("Order Summary:\n\n");
        int i = 1;
        for (Sandwich s : sandwiches) {
            sb.append("Sandwich ").append(i++).append(":\n").append(s.getSummary()).append("\n\n");
        }
        if (!drinks.isEmpty()) {
            sb.append("Drinks:\n");
            for (Drink d : drinks) sb.append("  - ").append(d).append("\n");
        }
        if (!chips.isEmpty()) {
            sb.append("Chips:\n");
            for (Chips c : chips) sb.append("  - ").append(c).append("\n");
        }
        sb.append("\nTotal Cost: $").append(String.format("%.2f", calculateTotal()));
        return sb.toString();
    }
}