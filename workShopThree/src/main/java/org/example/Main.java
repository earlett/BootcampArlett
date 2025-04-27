package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Product> allProducts = FileLoader.readfile();  // Assuming you have a FileLoader class to load products
        ShoppingCart cart = new ShoppingCart();

        while (true) {
            System.out.println("Welcome to Evie's Shop!\n \nPlease select an option from the Menu below:");
            System.out.println("1. View all products.");
            System.out.println("2. Search by SKU");
            System.out.println("3. Search by Price range");
            System.out.println("4. Search by name");
            System.out.println("5. Add item to cart");
            System.out.println("6. Remove item from cart");
            System.out.println("7. View cart contents");
            System.out.println("8. Checkout");
            System.out.println("9. Exit \n");

            int choice = 0;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid option. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    displayAllProducts(allProducts);
                    break;
                case 2:
                    System.out.println("Please enter the item SKU to search:");
                    String skuInput = scanner.nextLine();
                    Product foundProduct = searchBySku(allProducts, skuInput);
                    if (foundProduct != null) {
                        System.out.println("Match found: " + foundProduct);
                    } else {
                        System.out.println("SKU not found: " + skuInput);
                    }
                    break;
                case 3:
                    System.out.println("Enter minimum price:");
                    double min = 0;
                    double max = 0;
                    try {
                        min = Double.parseDouble(scanner.nextLine());
                        System.out.println("Enter maximum price:");
                        max = Double.parseDouble(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter valid prices.");
                        continue;
                    }
                    List<Product> priceResults = displayByPriceRange(allProducts, min, max);
                    for (Product p : priceResults) {
                        System.out.println(p);
                    }
                    break;
                case 4:
                    System.out.println("Enter product name to search:");
                    String name = scanner.nextLine();
                    List<Product> nameResults = searchByName(allProducts, name);
                    for (Product p : nameResults) {
                        System.out.println(p);
                    }
                    break;
                case 5:
                    System.out.println("Enter SKU of product to add to cart:");
                    String skuToAdd = scanner.nextLine();
                    addItemToCart(allProducts, cart, skuToAdd);
                    break;
                case 6:
                    System.out.println("Enter SKU of product to remove from cart:");
                    String skuToRemove = scanner.nextLine();
                    removeItemFromCart(cart, skuToRemove);
                    break;
                case 7:
                    System.out.println("Viewing cart contents:");
                    viewCartContents(cart);
                    break;
                case 8:
                    checkout(cart);
                    break;
                case 9:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    public static void displayAllProducts(List<Product> products) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/products.csv"))) {
            bufferedReader.readLine();  // Skip header
            String input;
            while ((input = bufferedReader.readLine()) != null) {
                System.out.println(input);
            }
        } catch (IOException ex) {
            System.out.println("Error reading the file: " + ex.getMessage());
        }
    }


    public static Product searchBySku(List<Product> products, String sku) {
        for (Product product : products) {
            if (product.getsKU().toLowerCase().contains(sku.toLowerCase())) {
                return product;
            }
        }
        return null;
    }

    public static List<Product> searchByName(List<Product> products, String name) {
        List<Product> matches = new ArrayList<>();
        for (Product product : products) {
            if (product.getProductName().toLowerCase().contains(name.toLowerCase())) {
                matches.add(product);
            }
        }
        return matches;
    }

    public static List<Product> displayByPriceRange(List<Product> products, double min, double max) {
        List<Product> matches = new ArrayList<>();
        for (Product product : products) {
            double price = product.getPrice();
            if (price >= min && price <= max) {
                matches.add(product);
            }
        }
        return matches;
    }

    // Add item to cart
    public static void addItemToCart(List<Product> products, ShoppingCart cart, String sku) {
        Product product = searchBySku(products, sku);
        if (product != null) {
            cart.addProductToCart(product);
            System.out.println("Added to cart: " + product.getProductName());
        } else {
            System.out.println("Product not found.");
        }
    }

    public static void removeItemFromCart(ShoppingCart cart, String sku) {
        cart.removeProductFromCart(sku);
    }

    // View cart contents
    public static void viewCartContents(ShoppingCart cart) {
        List<Product> cartProducts = cart.getAllProducts();
        if (cartProducts.isEmpty()) {
            System.out.println("Your cart is empty.");
        } else {
            for (Product product : cartProducts) {
                System.out.println(product);
            }
        }
    }

    public static void checkout(ShoppingCart cart) {
        double total = cart.getCartTotal();
        System.out.println("Your total is: $" + total);
        System.out.println("Proceeding to checkout...");
    }
}
