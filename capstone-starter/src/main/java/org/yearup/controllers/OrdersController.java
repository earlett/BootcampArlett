package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.*;
import org.yearup.models.*;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;

@RestController
@RequestMapping("orders")
@CrossOrigin
public class OrdersController {
    private OrderDao orderDao;
    private OrderLineItemDao orderLineItemDao;
    private ShoppingCartDao shoppingCartDao;
    private UserDao userDao;
    private ProfileDao profileDao;

    @Autowired
    public OrdersController(OrderDao orderDao, OrderLineItemDao orderLineItemDao,
                            ShoppingCartDao shoppingCartDao, UserDao userDao, ProfileDao profileDao) {
        this.orderDao = orderDao;
        this.orderLineItemDao = orderLineItemDao;
        this.shoppingCartDao = shoppingCartDao;
        this.userDao = userDao;
        this.profileDao = profileDao;
    }

    @PostMapping("")
    @PreAuthorize("isAuthenticated()")
    public Order checkout(Principal principal) {
        try {
            // Get the current user
            String userName = principal.getName();
            User user = userDao.getByUserName(userName);

            if (user == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
            }

            ShoppingCart cart = shoppingCartDao.getByUserId(user.getId());

            if (cart.getItems().isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Shopping cart is empty");
            }

            Profile profile = profileDao.getByUserId(user.getId());

            if (profile == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User profile not found. Please update your profile before checkout.");
            }

            // Create a new order
            Order order = new Order();
            order.setUserId(user.getId());
            order.setDate(LocalDate.now());
            order.setAddress(profile.getAddress());
            order.setCity(profile.getCity());
            order.setState(profile.getState());
            order.setZip(profile.getZip());

            // Calculate shipping (example: free shipping over $50 or otherwise $5)
            BigDecimal cartTotal = cart.getTotal();
            BigDecimal shippingThreshold = new BigDecimal("50.00");
            BigDecimal shippingCost = cartTotal.compareTo(shippingThreshold) >= 0
                    ? BigDecimal.ZERO
                    : new BigDecimal("5.00");
            order.setShippingAmount(shippingCost);

            // Save the order to sql database
            Order createdOrder = orderDao.create(order);

            // Create order line items for each cart item
            for (ShoppingCartItem cartItem : cart.getItems().values()) {
                OrderLineItem lineItem = new OrderLineItem();
                lineItem.setOrderId(createdOrder.getOrderId());
                lineItem.setProductId(cartItem.getProduct().getProductId());
                lineItem.setSalesPrice(cartItem.getProduct().getPrice());
                lineItem.setQuantity(cartItem.getQuantity());
                lineItem.setDiscount(cartItem.getDiscountPercent());

                orderLineItemDao.create(lineItem);
            }

            // Clear out the shopping cart
            shoppingCartDao.clearCart(user.getId());

            return createdOrder;

        } catch (ResponseStatusException e) {
            throw e;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Checkout failed. Please try again.");
        }
    }
}