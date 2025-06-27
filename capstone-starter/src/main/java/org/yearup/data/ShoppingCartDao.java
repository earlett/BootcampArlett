package org.yearup.data;

import org.yearup.models.ShoppingCart;

public interface ShoppingCartDao
{
    /**
     * Load an entire ShoppingCart (with full Product details) for the given user.
     */
    ShoppingCart getByUserId(int userId);

    /**
     * Add one unit of the given product to the cart.
     * If no row exists for (userId, productId), insert it; otherwise increment quantity.
     */
    void addProduct(int userId, int productId);

    /**
     * Set the quantity of a product already in the cart.
     */
    void updateQuantity(int userId, int productId, int quantity);

    /**
     * Remove all products from the given user's cart.
     */
    void clearCart(int userId);

    /**
     * Remove a specific product from the user's cart.
     */
    void removeProduct(int userId, int productId);
}