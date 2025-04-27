package org.example;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private List<Product> products;

    // Constructor to initialize the shopping cart
    public ShoppingCart() {
        this.products = new ArrayList<>();
    }

    // Add product to the cart
    public void addProductToCart(Product product) {
        products.add(product);
        System.out.println("Product added to cart: " + product.getProductName());
    }

    // Remove product from the cart using SKU
    public void removeProductFromCart(String sku) {
        // Loop through the list of products to find the matching SKU
        for (Product product : products) {
            if (product.getsKU().equalsIgnoreCase(sku)) {
                products.remove(product);
                System.out.println("Product removed from cart: " + product.getProductName());
                return;
            }
        }
        System.out.println("Product with SKU " + sku + " not found in cart.");
    }

    // Get the total price of the cart
    public double getCartTotal() {
        double total = 0.0;
        for (Product product : products) {
            total += product.getPrice();
        }
        return total;
    }

    // Get all products in the cart (useful for displaying the cart contents)
    public List<Product> getAllProducts() {
        return new ArrayList<>(products);  // Return a copy of the list
    }
}
