package userinterface;

import builders.SignatureSandwichManager;
import core.Customer;
import core.Order;
import core.Sandwich;
import model.*;
import util.DeliFileManager;

import java.text.SimpleDateFormat;
import java.util.*;

public class UserInterface {
    private final Scanner scanner = new Scanner(System.in);
    private Customer customer;
    private Order order;
    private boolean hasActiveOrder = false;

    public void display() {
        System.out.println("Welcome to Evie's Way Deli!");
        System.out.print("Please enter your name: ");
        String customerName = scanner.nextLine().trim();
        this.customer = new Customer(customerName);

        boolean isRunning = true;

        while (isRunning) {
            if (!hasActiveOrder) {
                // Home Screen - only show New Order and Exit
                System.out.printf("%nHello %s! What would you like to do?%n", customer.getName());
                System.out.println("1) New Order");
                System.out.println("0) Exit");
            } else {
                // Order Screen - show all order options
                System.out.printf("%nHello %s! What would you like to do?%n", customer.getName());
                System.out.println("1) Add Sandwich");
                System.out.println("2) Add Drink");
                System.out.println("3) Add Chips");
                System.out.println("4) Checkout");
                System.out.println("0) Cancel Order");
            }

            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());

                if (!hasActiveOrder) {
                    // Home Screen options
                    switch (choice) {
                        case 0 -> isRunning = false;
                        case 1 -> startNewOrder();
                        default -> System.out.println("Invalid option. Try again.");
                    }
                } else {
                    // Order Screen options
                    switch (choice) {
                        case 0 -> cancelOrder();
                        case 1 -> addSandwich();
                        case 2 -> addDrink();
                        case 3 -> addChips();
                        case 4 -> checkout();
                        default -> System.out.println("Invalid option. Try again.");
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }

        System.out.println("Thank you for visiting Evie's Way Deli!");
    }

    private void startNewOrder() {
        this.order = new Order(this.customer);
        this.hasActiveOrder = true;
        System.out.println("Started a new order!");
    }

    private void cancelOrder() {
        this.order = null;
        this.hasActiveOrder = false;
        System.out.println("Order cancelled. Returning to home screen.");
    }

    private void addSandwich() {
        System.out.println("\n=== Add Sandwich ===");
        System.out.println("1) Build Custom Sandwich");
        System.out.println("2) Choose Signature Sandwich");
        System.out.print("Select option (1 or 2): ");

        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());

            switch (choice) {
                case 1 -> buildCustomSandwich();
                case 2 -> chooseSignatureSandwich();
                default -> System.out.println("Invalid option. Please choose 1 or 2.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number (1 or 2).");
        }
    }

    private void buildCustomSandwich() {
        System.out.println("\n=== Build Custom Sandwich ===");

        // Select bread using Bread enum - show numbered options
        System.out.println("Select bread:");
        Bread[] breadOptions = Bread.values();
        for (int i = 0; i < breadOptions.length; i++) {
            System.out.printf("%d) %s%n", i + 1, breadOptions[i].name().toLowerCase());
        }
        System.out.printf("Enter number (1-%d) or type bread name: ", breadOptions.length);

        Bread bread = null;
        while (bread == null) {
            String breadInput = scanner.nextLine().trim();

            // Try to parse as number first
            try {
                int choice = Integer.parseInt(breadInput);
                if (choice >= 1 && choice <= breadOptions.length) {
                    bread = breadOptions[choice - 1];
                } else {
                    System.out.printf("Invalid number. Please choose 1-%d or type bread name: ", breadOptions.length);
                }
            } catch (NumberFormatException e) {
                // If not a number, try to parse as bread name
                bread = Bread.fromString(breadInput);
                if (bread == null) {
                    System.out.printf("Invalid bread type. Please choose number (1-%d) or type bread name (%s): ",
                            breadOptions.length, Bread.getAvailableOptions());
                }
            }
        }

        // Select size with validation
        System.out.print("Select size (4, 8, 12): ");
        int size = getSandwichSize();

        Sandwich sandwich = new Sandwich(bread, size);

        // Add all toppings using existing methods
        addMeats(sandwich, size);
        addCheeses(sandwich, size);
        addRegularToppings(sandwich);
        addSauces(sandwich);
        addSides(sandwich);

        // Toasted option
        System.out.print("Would you like it toasted? (yes/no): ");
        boolean toasted = scanner.nextLine().trim().equalsIgnoreCase("yes");
        sandwich.setToasted(toasted);

        order.addSandwich(sandwich);
        System.out.println("Custom sandwich added to your order!");
        System.out.printf("Sandwich cost: $%.2f%n", sandwich.calculateCost());
    }

    private void chooseSignatureSandwich() {
        System.out.println("\n=== Choose Signature Sandwich ===");
        SignatureSandwichManager.displaySignatureSandwiches();

        System.out.print("\nEnter signature sandwich type (blt, italian, veggie): ");
        String type = scanner.nextLine().trim();

        if (!SignatureSandwichManager.isValidSignatureSandwich(type)) {
            System.out.println("Invalid signature sandwich type.");
            return;
        }

        System.out.print("Select size (4, 8, 12): ");
        int size = getSandwichSize();

        Sandwich sandwich = SignatureSandwichManager.createSignatureSandwich(type, size);
        if (sandwich == null) {
            System.out.println("Error creating signature sandwich.");
            return;
        }

        System.out.println("\nSignature sandwich created with default toppings:");
        System.out.println(sandwich.getSummary());
        System.out.printf("Current cost: $%.2f%n", sandwich.calculateCost());

        System.out.print("\nWould you like to customize this sandwich? (yes/no): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("yes")) {
            customizeSignatureSandwich(sandwich);
        }

        order.addSandwich(sandwich);
        System.out.println("Signature sandwich added to your order!");
        System.out.printf("Final sandwich cost: $%.2f%n", sandwich.calculateCost());
    }

    private void customizeSignatureSandwich(Sandwich sandwich) {
        System.out.println("\n=== Customize Signature Sandwich ===");
        System.out.println("You can add more toppings to your signature sandwich.");

        boolean customizing = true;
        while (customizing) {
            System.out.println("\nCustomization options:");
            System.out.println("1) Add more meats");
            System.out.println("2) Add more cheeses");
            System.out.println("3) Add more regular toppings");
            System.out.println("4) Add more sauces");
            System.out.println("5) Add sides");
            System.out.println("6) Toggle toasted");
            System.out.println("7) Finish customizing");
            System.out.print("Select option: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());

                switch (choice) {
                    case 1 -> addMeats(sandwich, sandwich.getSize());
                    case 2 -> addCheeses(sandwich, sandwich.getSize());
                    case 3 -> addRegularToppings(sandwich);
                    case 4 -> addSauces(sandwich);
                    case 5 -> addSides(sandwich);
                    case 6 -> {
                        sandwich.setToasted(!sandwich.isToasted());
                        System.out.printf("Sandwich is now %s%n", sandwich.isToasted() ? "toasted" : "not toasted");
                    }
                    case 7 -> customizing = false;
                    default -> System.out.println("Invalid option. Please choose 1-7.");
                }

                if (customizing && choice >= 1 && choice <= 6) {
                    System.out.println("\nUpdated sandwich:");
                    System.out.println(sandwich.getSummary());
                    System.out.printf("Current cost: $%.2f%n", sandwich.calculateCost());
                }

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number (1-7).");
            }
        }
    }

    private int getSandwichSize() {
        while (true) {
            try {
                int size = Integer.parseInt(scanner.nextLine().trim());
                if (size == 4 || size == 8 || size == 12) {
                    return size;
                }
                System.out.print("Invalid size. Please choose 4, 8, or 12: ");
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number (4, 8, or 12): ");
            }
        }
    }

    private void addMeats(Sandwich sandwich, int size) {
        System.out.println("\nAvailable meats: steak, ham, salami, roast beef, chicken, bacon");
        System.out.println("Enter meats one at a time (type 'done' when finished):");

        while (true) {
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equalsIgnoreCase("done")) break;
            if (input.isEmpty()) continue;

            // Create meat with size-based pricing
            Meat meat = new Meat(input, size);
            sandwich.addToppings(meat);
            System.out.printf("Added %s ($%.2f)%n", input, meat.getCost());
        }

        // Ask for extra meat
        System.out.print("Would you like extra meat? (yes/no): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("yes")) {
            ExtraMeat extraMeat = ExtraMeat.fromSize(size);
            if (extraMeat != null) {
                sandwich.addToppings(new Toppings("extra meat", "meat", extraMeat.getCost()));
                System.out.printf("Added extra meat ($%.2f)%n", extraMeat.getCost());
            }
        }
    }

    private void addCheeses(Sandwich sandwich, int size) {
        System.out.println("\nAvailable cheeses: american, provolone, cheddar, swiss");
        System.out.println("Enter cheeses one at a time (type 'done' when finished):");

        while (true) {
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equalsIgnoreCase("done")) break;
            if (input.isEmpty()) continue;

            Cheese cheese = new Cheese(input, size);
            sandwich.addToppings(cheese);
            System.out.printf("Added %s cheese ($%.2f)%n", input, cheese.getCost());
        }

        // Ask for extra cheese
        System.out.print("Would you like extra cheese? (yes/no): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("yes")) {
            ExtraCheese extraCheese = ExtraCheese.fromSize(size);
            if (extraCheese != null) {
                sandwich.addToppings(new Toppings("extra cheese", "cheese", extraCheese.getCost()));
                System.out.printf("Added extra cheese ($%.2f)%n", extraCheese.getCost());
            }
        }
    }

    private void addRegularToppings(Sandwich sandwich) {
        System.out.printf("\nAvailable regular toppings (FREE): %s%n",
                String.join(", ", RegularToppings.getAvailableToppings()));
        System.out.println("Enter regular toppings one at a time (type 'done' when finished):");

        while (true) {
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equalsIgnoreCase("done")) break;
            if (input.isEmpty()) continue;

            try {
                sandwich.addToppings(new RegularToppings(input));
                System.out.printf("Added %s (FREE)%n", input);
            } catch (IllegalArgumentException e) {
                System.out.printf("Invalid regular topping. Available options: %s%n",
                        String.join(", ", RegularToppings.getAvailableToppings()));
            }
        }
    }

    private void addSauces(Sandwich sandwich) {
        System.out.println("\nAvailable sauces (FREE): mayo, mustard, ketchup, ranch, thousand islands, vinaigrette");
        System.out.println("Enter sauces one at a time (type 'done' when finished):");

        while (true) {
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equalsIgnoreCase("done")) break;
            if (input.isEmpty()) continue;

            try {
                sandwich.addToppings(new Sauces(input));
                System.out.printf("Added %s sauce (FREE)%n", input);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid sauce. Available options: mayo, mustard, ketchup, ranch, thousand islands, vinaigrette");
            }
        }
    }

    private void addSides(Sandwich sandwich) {
        System.out.printf("\nAvailable sides (FREE): %s%n", String.join(", ", Sides.SIDE_OPTIONS));
        System.out.println("Enter sides one at a time (type 'done' when finished):");

        while (true) {
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equalsIgnoreCase("done")) break;
            if (input.isEmpty()) continue;

            try {
                sandwich.addToppings(new Sides(input));
                System.out.printf("Added %s (FREE)%n", input);
            } catch (IllegalArgumentException e) {
                System.out.printf("Invalid side. Available options: %s%n", String.join(", ", Sides.SIDE_OPTIONS));
            }
        }
    }

    private void addDrink() {
        System.out.println("\n=== Add Drink ===");
        System.out.println("Drink sizes and prices:");
        for (Drink drink : Drink.values()) {
            System.out.printf("%c - %s%n", drink.getSize(), drink.toString());
        }
        System.out.print("Enter drink size (S, M, L): ");

        char sizeChar = scanner.nextLine().trim().toUpperCase().charAt(0);
        Drink drink = Drink.fromChar(sizeChar);
        if (drink != null) {
            order.addDrink(drink);
            System.out.printf("Added %s%n", drink.toString());
        } else {
            System.out.println("Invalid drink size.");
        }
    }

    private void addChips() {
        System.out.println("\n=== Add Chips ===");
        System.out.println("Chips cost $1.50");
        System.out.print("Would you like to add chips? (yes/no): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("yes")) {
            order.addChips(new Chips());
            System.out.println("Added chips ($1.50)");
        }
    }

    private void checkout() {
        System.out.println("\n===== CHECKOUT =====");
        System.out.println(order.getSummary());
        System.out.printf("Total Price: $%.2f%n", order.calculateTotal());
        System.out.println("====================");

        System.out.print("Confirm order? (yes/no): ");
        if (scanner.nextLine().trim().equalsIgnoreCase("yes")) {
            finalizeOrder();
        } else {
            System.out.println("Order not confirmed. Returning to order menu.");
        }
    }

    private void finalizeOrder() {
        String filename = "receipt_" + new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date()) + ".txt";
        DeliFileManager.saveToFile(filename, generateReceiptContent());

        System.out.println("\n Order confirmed!");
        System.out.println("Here is your receipt!");

        if (!order.getDrinks().isEmpty()) {
            System.out.println("You may pick your drink(s) at the fountain.");
        }
        if (!order.getChips().isEmpty()) {
            System.out.printf("You may grab %d bag(s) of chips from the shelf.%n", order.getChips().size());
        }
        System.out.println("Thank you for your purchase!");

        // Reset to home screen
        this.order = null;
        this.hasActiveOrder = false;
    }

    private String generateReceiptContent() {
        StringBuilder receipt = new StringBuilder();
        receipt.append("Evie's Way Deli RECEIPT\n");
        receipt.append("Date: ").append(new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new Date())).append("\n");
        receipt.append("Customer: ").append(customer.getName()).append("\n\n");
        receipt.append(order.getSummary());
        receipt.append("\n\nThank you for choosing Evie's Way Deli!");
        return receipt.toString();
    }
}