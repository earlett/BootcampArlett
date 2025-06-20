package org.example.dao;

import org.example.models.Customer;

import java.util.Optional;

public interface CustomerDao {

    /** returns existing customer or empty */
    Optional<Customer> findByEmail(String email);

    /** inserts a customer and returns new ID */
    int insert(Customer c);

    /**
     * If a customer with the email exists, returns its ID;
     * otherwise inserts a new row and returns the generated ID.
     */
    default int getOrCreate(Customer c) {
        return findByEmail(c.getEmail())
                .map(Customer::getId)
                .orElseGet(() -> insert(c));
    }
}
