package org.example.dao;

import org.example.models.Vehicle;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


public interface VehicleDao {

    //Queries
    List<Vehicle> getAllVehicles();
    Optional<Vehicle> getVehicleByVin(int vin);

    List<Vehicle> searchByPriceRange(BigDecimal min, BigDecimal max);
    List<Vehicle> searchByMakeModel(String make, String model);
    List<Vehicle> searchByYearRange(int startYear, int endYear);
    List<Vehicle> searchByColor(String color);
    List<Vehicle> searchByMileageRange(int minMileage, int maxMileage);
    List<Vehicle> searchByType(String type);

    //CRUD
    void addVehicle(Vehicle vehicle);
    void removeVehicle(int vin);
}
