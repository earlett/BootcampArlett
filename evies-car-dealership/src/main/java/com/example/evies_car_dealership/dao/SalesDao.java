package com.example.evies_car_dealership.dao;

import com.example.evies_car_dealership.model.SalesContract;

import java.util.List;

public interface SalesDao {
    void save(SalesContract contract);
    List<SalesContract> getAll();

    List<SalesContract> getByDealership(int id);
}