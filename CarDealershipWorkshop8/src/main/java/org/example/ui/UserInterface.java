package org.example.ui;

import org.example.dao.*;
import org.example.models.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Console UI – fully DAO-backed and now collects customer phone + address.
 */
public class UserInterface {

    private final Scanner scanner = new Scanner(System.in);

    /* DAOs */
    private final VehicleDao  vehicleDao  = new VehicleDaoImpl();
    private final ContractDao contractDao = new ContractDaoImpl();
    private final CustomerDao customerDao = new CustomerDaoImpl();

    /* ─────────────────── life-cycle ─────────────────── */

    public void display() {
        boolean running = true;
        while (running) {
            printMenu();
            switch (scanner.nextLine().trim()) {
                case "1"  -> printVehicles(vehicleDao.getAllVehicles());
                case "2"  -> processSearchPrice();
                case "3"  -> processSearchMakeModel();
                case "4"  -> processSearchYear();
                case "5"  -> processSearchColor();
                case "6"  -> processSearchMileage();
                case "7"  -> processSearchType();
                case "8"  -> processSearchVin();
                case "9"  -> processAddVehicle();
                case "10" -> processRemoveVehicle();
                case "11" -> processSellOrLease();
                case "A"  -> new AdminUserInterface(scanner).display();
                case "99" -> running = false;
                default   -> System.out.println("Invalid choice.");
            }
        }
    }

    /* ─────────────────── menu ─────────────────── */

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
                A.  Admin mode
                99. Quit
                Choice: """);
    }

    /* ─────────────────── search helpers ─────────────────── */

    private void printVehicles(List<Vehicle> list) {
        if (list.isEmpty()) System.out.println("No vehicles found.");
        else list.forEach(System.out::println);
    }

    private void processSearchPrice() {
        System.out.print("Enter max price: ");
        double max = Double.parseDouble(scanner.nextLine());
        printVehicles(vehicleDao.searchByPriceRange(
                java.math.BigDecimal.ZERO, java.math.BigDecimal.valueOf(max)));
    }

    private void processSearchMakeModel() {
        System.out.print("Enter make: ");
        String make = scanner.nextLine();
        System.out.print("Enter model: ");
        String model = scanner.nextLine();
        printVehicles(vehicleDao.searchByMakeModel(make, model));
    }

    private void processSearchYear() {
        System.out.print("Enter year: ");
        int year = Integer.parseInt(scanner.nextLine());
        printVehicles(vehicleDao.searchByYearRange(year, year));
    }

    private void processSearchColor() {
        System.out.print("Enter color: ");
        printVehicles(vehicleDao.searchByColor(scanner.nextLine()));
    }

    private void processSearchMileage() {
        System.out.print("Enter max mileage: ");
        int m = Integer.parseInt(scanner.nextLine());
        printVehicles(vehicleDao.searchByMileageRange(0, m));
    }

    private void processSearchType() {
        System.out.print("Enter vehicle type: ");
        try {
            VehicleType t = VehicleType.valueOf(scanner.nextLine().toUpperCase());
            printVehicles(vehicleDao.searchByType(t.name()));
        } catch (IllegalArgumentException e) {
            System.out.println("Unknown vehicle type.");
        }
    }

    private void processSearchVin() {
        System.out.print("Enter VIN: ");
        int vinInt;
        try { vinInt = Integer.parseInt(scanner.nextLine().trim()); }
        catch (NumberFormatException ex) { System.out.println("VIN must be numeric."); return; }

        Optional<Vehicle> v = vehicleDao.getVehicleByVin(vinInt);
        System.out.println(v.map(Object::toString).orElse("No vehicle with that VIN."));
    }

    /* ─────────────────── add / remove ─────────────────── */

    private void processAddVehicle() {
        try {
            System.out.print("VIN (numeric): ");   int vin = Integer.parseInt(scanner.nextLine());
            System.out.print("Year: ");            int year  = Integer.parseInt(scanner.nextLine());
            System.out.print("Make: ");            String make  = scanner.nextLine();
            System.out.print("Model: ");           String model = scanner.nextLine();
            System.out.print("Color: ");           String color = scanner.nextLine();
            System.out.print("Type: ");            VehicleType type = VehicleType.valueOf(scanner.nextLine().toUpperCase());
            System.out.print("Odometer: ");        int odo   = Integer.parseInt(scanner.nextLine());
            System.out.print("Price: ");           double price = Double.parseDouble(scanner.nextLine());

            vehicleDao.addVehicle(new Vehicle(
                    String.valueOf(vin), year, make, model, color, type, odo, price));
            System.out.println("Vehicle added.");
        } catch (Exception ex) {
            System.out.println("Invalid input — vehicle NOT added.");
        }
    }

    private void processRemoveVehicle() {
        System.out.print("Enter VIN to remove: ");
        try {
            int vin = Integer.parseInt(scanner.nextLine().trim());
            vehicleDao.removeVehicle(vin);
            System.out.println("Removed (if VIN existed).");
        } catch (NumberFormatException ex) {
            System.out.println("VIN must be numeric.");
        }
    }

    /* ─────────────────── sell / lease ─────────────────── */

    private void processSellOrLease() {
        System.out.print("Enter VIN: ");
        int vin;
        try { vin = Integer.parseInt(scanner.nextLine().trim()); }
        catch (NumberFormatException ex) { System.out.println("VIN must be numeric."); return; }

        Optional<Vehicle> opt = vehicleDao.getVehicleByVin(vin);
        if (opt.isEmpty()) { System.out.println("Vehicle not found."); return; }
        Vehicle vehicle = opt.get();

        /* customer info */
        System.out.print("Customer name   : "); String name    = scanner.nextLine();
        System.out.print("Customer email  : "); String email   = scanner.nextLine();
        System.out.print("Customer phone  : "); String phone   = scanner.nextLine();
        System.out.print("Customer address: "); String address = scanner.nextLine();

        customerDao.getOrCreate(new Customer(name, email, phone, address));

        System.out.print("sale or lease   : ");
        String kind = scanner.nextLine().toLowerCase();

        Contract contract;
        if ("sale".equals(kind)) {
            ArrayList<AddOn> addOns = promptAddOns();
            contract = new SalesContract(
                    java.time.LocalDate.now().toString(), name, email, vehicle, addOns);
            contractDao.saveSalesContract((SalesContract) contract);
        } else if ("lease".equals(kind)) {
            int age = java.time.LocalDate.now().getYear() - vehicle.getYear();
            if (age > 3) { System.out.println("Vehicle too old for lease (>3 years)."); return; }
            contract = new LeaseContract(
                    java.time.LocalDate.now().toString(), name, email, vehicle);
            contractDao.saveLeaseContract((LeaseContract) contract);
        } else {
            System.out.println("Invalid choice."); return;
        }

        vehicleDao.removeVehicle(vin);
        System.out.printf("Contract saved. Monthly payment: %.2f%n",
                contract.calculatePayment());
    }

    /* ─────────────────── helper: add-ons ─────────────────── */

    private ArrayList<AddOn> promptAddOns() {
        ArrayList<AddOn> list = new ArrayList<>();
        AddOn[] options = AddOn.values();
        System.out.println("Select add-ons (comma-separated numbers or blank for none):");
        for (int i = 0; i < options.length; i++)
            System.out.printf("%d. %s%n", i + 1, options[i]);

        System.out.print("Choice: ");
        for (String tk : scanner.nextLine().split(",")) {
            try {
                int idx = Integer.parseInt(tk.trim()) - 1;
                if (idx >= 0 && idx < options.length) list.add(options[idx]);
            } catch (NumberFormatException ignored) { }
        }
        return list;
    }
}
