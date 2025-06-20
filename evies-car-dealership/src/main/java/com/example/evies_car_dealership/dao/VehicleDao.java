package com.example.evies_car_dealership.dao;

import com.example.evies_car_dealership.model.Vehicle;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * DAO contract for Vehicle persistence.
 */
public interface VehicleDao {

    /* ─────── CRUD ─────── */

    void addVehicle(Vehicle vehicle);

    void removeVehicle(int vin);

    void update(Vehicle vehicle);

    List<Vehicle> getAllVehicles();

    Optional<Vehicle> getVehicleByVin(int vin);

    /* ─────── Search helpers ─────── */

    List<Vehicle> searchByPriceRange(BigDecimal min, BigDecimal max);

    List<Vehicle> searchByMakeModel(String make, String model);

    List<Vehicle> searchByYearRange(int startYear, int endYear);

    List<Vehicle> searchByColor(String color);

    List<Vehicle> searchByMileageRange(int minMileage, int maxMileage);

    List<Vehicle> searchByType(String type);
}
