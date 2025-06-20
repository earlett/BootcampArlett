package com.example.evies_car_dealership.dao;

import com.example.evies_car_dealership.model.LeaseContract;

import java.util.List;

public interface LeaseDao {
    void save(LeaseContract contract);
    List<LeaseContract> getAll();
}
