package com.example.evies_car_dealership.dao;

import com.example.evies_car_dealership.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerDao {

    List<Customer> getAll();

    Optional<Customer> getByEmail(String email);

    int getOrCreate(Customer customer);

    boolean update(Customer customer);

    boolean deleteByEmail(String email);
}
