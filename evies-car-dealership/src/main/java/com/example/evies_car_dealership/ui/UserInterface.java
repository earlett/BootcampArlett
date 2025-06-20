package com.example.evies_car_dealership.ui;

import com.example.evies_car_dealership.model.Vehicle;
import com.example.evies_car_dealership.dao.ContractDao;
import com.example.evies_car_dealership.dao.CustomerDao;
import com.example.evies_car_dealership.dao.VehicleDao;
import com.example.evies_car_dealership.model.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class UserInterface {

    private final Scanner scanner = new Scanner(System.in);

    private final VehicleDao vehicleDao;
    private final ContractDao contractDao;
    private final CustomerDao customerDao;

    public UserInterface() {
        this.vehicleDao = vehicleDao;
        this.contractDao = contractDao;
        this.customerDao = customerDao;
    }

    public void display() {
        boolean running = true;
        while (running) {
            printMenu();
            switch (scanner.nextLine().trim()) {
                case "1" -> printVehicles(vehicleDao.getAllVehicles());
                case "2" -> processSearchPrice();
                case "3" -> processSearchMakeModel();
                case "4" -> processSearchYear();
                case "5" -> processSearchColor();
                case "6" -> processSearchMileage();
                case "7" -> processSearchType();
                case "8" -> processSearchVin();
                case "9" -> processAddVehicle();
                case "10" -> processRemoveVehicle();
                case "11" -> processSellOrLease();
                case "99" -> running = false;
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void printMenu() {
        System.out.println("""
            \n-- MAIN MENU --
            1.  Show all vehicles
            2.  Search by price
            3.  Search by make/model
            4.  Search by year
            5.  Search by color
            6.  Search by mileage
            7.  Search by type
            8.  Search by VIN
            9.  Add vehicle
            10. Remove vehicle
            11. Sell / Lease vehicle
            99. Quit
            Choice: """);
    }

    private void printVehicles(List<Vehicle> list) {
        if (list.isEmpty()) {
            System.out.println("No vehicles found.");
        } else {
            System.out.println("\n=== VEHICLES ===");
            list.forEach(System.out::println);
        }
    }

    private void processSearchPrice() {
        System.out.print("Enter minimum price (or 0 for no minimum): ");
        double min = Double.parseDouble(scanner.nextLine());
        System.out.print("Enter maximum price: ");
        double max = Double.parseDouble(scanner.nextLine());
        printVehicles(vehicleDao.searchByPriceRange(BigDecimal.valueOf(min), BigDecimal.valueOf(max)));
    }

    private void processSearchMakeModel() {
        System.out.print("Enter make: ");
        String make = scanner.nextLine();
        System.out.print("Enter model: ");
        String model = scanner.nextLine();
        printVehicles(vehicleDao.searchByMakeModel(make, model));
    }

    private void processSearchYear() {
        System.out.print("Enter start year: ");
        int startYear = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter end year: ");
        int endYear = Integer.parseInt(scanner.nextLine());
        printVehicles(vehicleDao.searchByYearRange(startYear, endYear));
    }

    private void processSearchColor() {
        System.out.print("Enter color: ");
        printVehicles(vehicleDao.searchByColor(scanner.nextLine()));
    }

    private void processSearchMileage() {
        System.out.print("Enter minimum mileage (or 0 for no minimum): ");
        int min = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter maximum mileage: ");
        int max = Integer.parseInt(scanner.nextLine());
        printVehicles(vehicleDao.searchByMileageRange(min, max));
    }

    private void processSearchType() {
        System.out.println("Available vehicle types:");
        for (VehicleType type : VehicleType.values()) {
            System.out.println("- " + type.name());
        }
        System.out.print("Enter vehicle type: ");
        try {
            VehicleType type = VehicleType.valueOf(scanner.nextLine().toUpperCase());
            printVehicles(vehicleDao.searchByType(type.name()));
        } catch (IllegalArgumentException e) {
            System.out.println("Unknown vehicle type.");
        }
    }

    private void processSearchVin() {
        System.out.print("Enter VIN: ");
        int vin;
        try {
            vin = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException ex) {
            System.out.println("VIN must be numeric.");
            return;
        }

        Optional<Vehicle> vehicle = vehicleDao.getVehicleByVin(vin);
        if (vehicle.isPresent()) {
            System.out.println("\n=== VEHICLE FOUND ===");
            System.out.println(vehicle.get());
        } else {
            System.out.println("No vehicle found with that VIN.");
        }
    }

    private void processAddVehicle() {
        try {
            System.out.print("VIN (numeric): ");
            String vin = scanner.nextLine();
            System.out.print("Year: ");
            int year = Integer.parseInt(scanner.nextLine());
            System.out.print("Make: ");
            String make = scanner.nextLine();
            System.out.print("Model: ");
            String model = scanner.nextLine();
            System.out.print("Color: ");
            String color = scanner.nextLine();

            System.out.println("Available vehicle types:");
            for (VehicleType type : VehicleType.values()) {
                System.out.println("- " + type.name());
            }
            System.out.print("Type: ");
            VehicleType type = VehicleType.valueOf(scanner.nextLine().toUpperCase());

            System.out.print("Odometer: ");
            int odo = Integer.parseInt(scanner.nextLine());
            System.out.print("Price: ");
            double price = Double.parseDouble(scanner.nextLine());

            Vehicle vehicle = new Vehicle(vin, year, make, model, color, type, odo, price);
            vehicleDao.addVehicle(vehicle);
            System.out.println("Vehicle added successfully!");
        } catch (Exception e) {
            System.out.println("Invalid input — vehicle NOT added. Error: " + e.getMessage());
        }
    }

    private void processRemoveVehicle() {
        System.out.print("Enter VIN to remove: ");
        try {
            int vin = Integer.parseInt(scanner.nextLine().trim());

            // Check if vehicle exists first
            Optional<Vehicle> vehicle = vehicleDao.getVehicleByVin(vin);
            if (vehicle.isEmpty()) {
                System.out.println("Vehicle with VIN " + vin + " not found.");
                return;
            }

            vehicleDao.removeVehicle(vin);
            System.out.println("Vehicle marked as sold/removed successfully.");
        } catch (NumberFormatException e) {
            System.out.println("VIN must be numeric.");
        }
    }

    private void processSellOrLease() {
        System.out.print("Enter VIN: ");
        int vin;
        try {
            vin = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException ex) {
            System.out.println("VIN must be numeric.");
            return;
        }

        Optional<Vehicle> opt = vehicleDao.getVehicleByVin(vin);
        if (opt.isEmpty()) {
            System.out.println("Vehicle not found.");
            return;
        }
        Vehicle vehicle = opt.get();

        System.out.println("\n=== SELECTED VEHICLE ===");
        System.out.println(vehicle);

        // Customer info
        System.out.println("\n=== CUSTOMER INFORMATION ===");
        System.out.print("Customer name: ");
        String name = scanner.nextLine();
        System.out.print("Customer email: ");
        String email = scanner.nextLine();
        System.out.print("Customer phone: ");
        String phone = scanner.nextLine();
        System.out.print("Customer address: ");
        String address = scanner.nextLine();

        // Create or get customer
        Customer customer = new Customer(name, email, phone, address);
        int customerId = customerDao.getOrCreate(customer);

        System.out.print("\nTransaction type (sale/lease): ");
        String kind = scanner.nextLine().toLowerCase();

        Contract contract;
        if ("sale".equals(kind)) {
            ArrayList<AddOn> addOns = promptAddOns();
            contract = new SalesContract(
                    java.time.LocalDate.now().toString(),
                    name, email, vehicle, addOns
            );
            contractDao.saveSalesContract((SalesContract) contract);
            System.out.println("Sales contract saved successfully!");
        } else if ("lease".equals(kind)) {
            int age = java.time.LocalDate.now().getYear() - vehicle.getYear();
            if (age > 3) {
                System.out.println("Vehicle too old for lease (>3 years). Cannot process lease.");
                return;
            }
            contract = new LeaseContract(
                    java.time.LocalDate.now().toString(),
                    name, email, vehicle
            );
            contractDao.saveLeaseContract((LeaseContract) contract);
            System.out.println("Lease contract saved successfully!");
        } else {
            System.out.println("Invalid choice. Please enter 'sale' or 'lease'.");
            return;
        }

        // Mark vehicle as sold/leased
        vehicleDao.removeVehicle(vin);

        System.out.printf("Contract completed! Monthly payment: $%.2f%n", contract.calculatePayment());
        System.out.printf("Total price: $%.2f%n", contract.getTotalPrice());
    }

    private ArrayList<AddOn> promptAddOns() {
        ArrayList<AddOn> list = new ArrayList<>();
        AddOn[] options = AddOn.values();

        System.out.println("\n=== AVAILABLE ADD-ONS ===");
        for (int i = 0; i < options.length; i++) {
            System.out.printf("%d. %s - $%.2f%n", i + 1, options[i].getName(), options[i].getPrice());
        }

        System.out.print("\nSelect add-ons (comma-separated numbers, or press Enter for none): ");
        String input = scanner.nextLine().trim();

        if (input.isEmpty()) {
            System.out.println("No add-ons selected.");
            return list;
        }

        for (String token : input.split(",")) {
            try {
                int idx = Integer.parseInt(token.trim()) - 1;
                if (idx >= 0 && idx < options.length) {
                    list.add(options[idx]);
                    System.out.println("Added: " + options[idx].getName());
                } else {
                    System.out.println("Invalid option: " + (idx + 1));
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input: " + token);
            }
        }

        if (!list.isEmpty()) {
            double addOnTotal = list.stream().mapToDouble(AddOn::getPrice).sum();
            System.out.printf("Total add-ons cost: $%.2f%n", addOnTotal);
        }

        return list;
    }
}