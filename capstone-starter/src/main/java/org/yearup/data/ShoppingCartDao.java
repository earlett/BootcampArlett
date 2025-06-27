package org.yearup.data;

import org.yearup.models.ShoppingCart;

public interface ShoppingCartDao
{

    ShoppingCart getByUserId(int userId);


    void addProduct(int userId, int productId);


    void updateQuantity(int userId, int productId, int quantity);


    void clearCart(int userId);


    void removeProduct(int userId, int productId);
}