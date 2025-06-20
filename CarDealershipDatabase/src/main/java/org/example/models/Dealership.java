package org.example.models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Dealership {
    private String name;
    private String address;
    private String phone;
    private List<Vehicle> inventory = new ArrayList<>();

    public Dealership(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getPhone() { return phone; }

    public void addVehicle(Vehicle v) {
        inventory.add(v);
    }

    public void removeVehicle(Vehicle v) {
        inventory.remove(v);
    }

    public List<Vehicle> getAllVehicles() {
        return new ArrayList<>(inventory);
    }

    public Vehicle getVehicleByVin(String vin) {
        return inventory.stream()
            .filter(v -> v.getVin().equals(vin))
            .findFirst().orElse(null);
    }

    public List<Vehicle> getVehiclesByPrice(double maxPrice) {
        return inventory.stream()
            .filter(v -> v.getPrice() <= maxPrice)
            .collect(Collectors.toList());
    }

    public List<Vehicle> getVehiclesByMakeModel(String make, String model) {
        return inventory.stream()
            .filter(v -> v.getMake().equalsIgnoreCase(make)
                      && v.getModel().equalsIgnoreCase(model))
            .collect(Collectors.toList());
    }

    public List<Vehicle> getVehiclesByYear(int year) {
        return inventory.stream()
            .filter(v -> v.getYear() == year)
            .collect(Collectors.toList());
    }

    public List<Vehicle> getVehiclesByColor(String color) {
        return inventory.stream()
            .filter(v -> v.getColor().equalsIgnoreCase(color))
            .collect(Collectors.toList());
    }

    public List<Vehicle> getVehiclesByMileage(int maxMiles) {
        return inventory.stream()
            .filter(v -> v.getOdometer() <= maxMiles)
            .collect(Collectors.toList());
    }

    public List<Vehicle> getVehiclesByType(VehicleType type) {
        return inventory.stream()
            .filter(v -> v.getType() == type)
            .collect(Collectors.toList());
    }
}
