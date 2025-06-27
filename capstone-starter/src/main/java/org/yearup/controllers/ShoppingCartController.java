package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.ProductDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.data.UserDao;
import org.yearup.models.ShoppingCart;
import org.yearup.models.User;

import java.security.Principal;

@RestController
@RequestMapping("cart")
@CrossOrigin
public class ShoppingCartController {
    private ShoppingCartDao shoppingCartDao;
    private UserDao userDao;
    private ProductDao productDao;

    @Autowired
    public ShoppingCartController(ShoppingCartDao shoppingCartDao, UserDao userDao, ProductDao productDao) {
        this.shoppingCartDao = shoppingCartDao;
        this.userDao = userDao;
        this.productDao = productDao;
    }

    // GET /cart - Get current user's cart
    @GetMapping("")
    @PreAuthorize("isAuthenticated()")
    public ShoppingCart getCart(Principal principal) {
        try {
            // Get the currently logged-in user's username
            String userName = principal.getName();
            User user = userDao.getByUserName(userName);

            if (user == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
            }

            // Use the user's id to get their cart
            return shoppingCartDao.getByUserId(user.getId());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    // POST /cart/products/{productId} - Add product to cart
    @PostMapping("/products/{productId}")
    @PreAuthorize("isAuthenticated()")
    public ShoppingCart addToCart(@PathVariable int productId, Principal principal) {
        try {
            String userName = principal.getName();
            User user = userDao.getByUserName(userName);

            if (user == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
            }

            // Check if product exists
            var product = productDao.getById(productId);
            if (product == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
            }

            // Add the product to the cart
            shoppingCartDao.addProduct(user.getId(), productId);

            // Return the updated cart
            return shoppingCartDao.getByUserId(user.getId());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    // PUT /cart/products/{productId} - Update quantity
    @PutMapping("/products/{productId}")
    @PreAuthorize("isAuthenticated()")
    public ShoppingCart updateQuantity(@PathVariable int productId, @RequestParam int quantity, Principal principal) {
        try {
            String userName = principal.getName();
            User user = userDao.getByUserName(userName);

            if (user == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
            }

            if (quantity <= 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantity must be greater than 0");
            }

            // Update the quantity
            shoppingCartDao.updateQuantity(user.getId(), productId, quantity);

            // Return the updated cart
            return shoppingCartDao.getByUserId(user.getId());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    // DELETE /cart - Clear entire cart
    @DeleteMapping("")
    @PreAuthorize("isAuthenticated()")
    public ShoppingCart clearCart(Principal principal) {
        try {
            String userName = principal.getName();
            User user = userDao.getByUserName(userName);

            if (user == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
            }

            // Clear the cart
            shoppingCartDao.clearCart(user.getId());

            // Return empty cart
            return new ShoppingCart();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    // DELETE /cart/products/{productId} - Remove specific item
    @DeleteMapping("/products/{productId}")
    @PreAuthorize("isAuthenticated()")
    public ShoppingCart removeFromCart(@PathVariable int productId, Principal principal) {
        try {
            String userName = principal.getName();
            User user = userDao.getByUserName(userName);

            if (user == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
            }

            // We need to add this method to the DAO
            shoppingCartDao.removeProduct(user.getId(), productId);

            // Return the updated cart
            return shoppingCartDao.getByUserId(user.getId());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }
}