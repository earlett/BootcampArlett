package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Simple console front-end for the dealership application.
 */
public class UserInterface {

    private final Scanner scanner = new Scanner(System.in);

    /* persistence helpers */
    private final DealershipFileManager dfm = new DealershipFileManager();
    private final ContractFileManager  cfm = new ContractFileManager();

    /* loaded at startup */
    private Dealership dealership;

    /* ========== life-cycle ========== */

    public void init() {
        dealership = dfm.getDealership();
        if (dealership == null) {
            System.out.println("ERROR: inventory.csv missing or unreadable.");
            System.exit(1);
        }
    }

    public void display() {
        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1"  -> printVehicles(dealership.getAllVehicles());
                case "2"  -> processSearchPrice();
                case "3"  -> processSearchMakeModel();
                case "4"  -> processSearchYear();
                case "5"  -> processSearchColor();
                case "6"  -> processSearchMileage();
                case "7"  -> processSearchType();
                case "8"  -> processSearchVin();          // new option
                case "9"  -> processAddVehicle();
                case "10" -> processRemoveVehicle();
                case "11" -> processSellOrLease();
                case "A"  -> new AdminUserInterface(scanner).display();
                case "99" -> { dfm.saveDealership(dealership); running = false; }
                default   -> System.out.println("Invalid choice.");
            }
        }
    }

    /* ========== menu printer ========== */

    private void printMenu() {
        System.out.println("\n-- MAIN MENU --");
        System.out.println("1.  Show all vehicles");
        System.out.println("2.  Search by price");
        System.out.println("3.  Search by make/model");
        System.out.println("4.  Search by year");
        System.out.println("5.  Search by color");
        System.out.println("6.  Search by mileage");
        System.out.println("7.  Search by type");
        System.out.println("8.  Search by VIN");      // new line
        System.out.println("9.  Add vehicle");
        System.out.println("10. Remove vehicle");
        System.out.println("11. Sell / Lease vehicle");
        System.out.println("A.  Admin mode");
        System.out.println("99. Quit");
        System.out.print("Choice: ");
    }

    /* ========== search helpers ========== */

    private void printVehicles(List<Vehicle> list) {
        if (list.isEmpty()) {
            System.out.println("No vehicles found.");
        } else {
            list.forEach(System.out::println);
        }
    }

    private void processSearchPrice() {
        System.out.print("Enter max price: ");
        double max = Double.parseDouble(scanner.nextLine());
        printVehicles(dealership.getVehiclesByPrice(max));
    }

    private void processSearchMakeModel() {
        System.out.print("Enter make: ");
        String make = scanner.nextLine();
        System.out.print("Enter model: ");
        String model = scanner.nextLine();
        printVehicles(dealership.getVehiclesByMakeModel(make, model));
    }

    private void processSearchYear() {
        System.out.print("Enter year: ");
        int year = Integer.parseInt(scanner.nextLine());
        printVehicles(dealership.getVehiclesByYear(year));
    }

    private void processSearchColor() {
        System.out.print("Enter color: ");
        String color = scanner.nextLine();
        printVehicles(dealership.getVehiclesByColor(color));
    }

    private void processSearchMileage() {
        System.out.print("Enter max mileage: ");
        int miles = Integer.parseInt(scanner.nextLine());
        printVehicles(dealership.getVehiclesByMileage(miles));
    }

    private void processSearchType() {
        System.out.print("Enter vehicle type: ");
        String t = scanner.nextLine().toUpperCase();
        try {
            printVehicles(dealership.getVehiclesByType(VehicleType.valueOf(t)));
        } catch (IllegalArgumentException e) {
            System.out.println("Unknown vehicle type.");
        }
    }

    /* ---- NEW: search by VIN ---- */
    private void processSearchVin() {
        System.out.print("Enter VIN: ");
        String vin = scanner.nextLine().trim();
        Vehicle v = dealership.getVehicleByVin(vin);
        if (v == null) {
            System.out.println("No vehicle with that VIN.");
        } else {
            System.out.println(v);
        }
    }

    /* ========== add / remove ========== */

    private void processAddVehicle() {
        System.out.print("VIN: ");            String vin   = scanner.nextLine();
        System.out.print("Year: ");           int    year  = Integer.parseInt(scanner.nextLine());
        System.out.print("Make: ");           String make  = scanner.nextLine();
        System.out.print("Model: ");          String model = scanner.nextLine();
        System.out.print("Color: ");          String color = scanner.nextLine();
        System.out.print("Type: ");           VehicleType type = VehicleType.valueOf(scanner.nextLine().toUpperCase());
        System.out.print("Odometer: ");       int    odo   = Integer.parseInt(scanner.nextLine());
        System.out.print("Price: ");          double price = Double.parseDouble(scanner.nextLine());

        dealership.addVehicle(new Vehicle(vin, year, make, model, color, type, odo, price));
        dfm.saveDealership(dealership);
    }

    private void processRemoveVehicle() {
        System.out.print("Enter VIN to remove: ");
        String vin = scanner.nextLine().trim();
        Vehicle v = dealership.getVehicleByVin(vin);
        if (v == null) {
            System.out.println("No such vehicle.");
            return;
        }
        dealership.removeVehicle(v);
        dfm.saveDealership(dealership);
        System.out.println("Vehicle removed.");
    }

    /* ========== sell / lease ========== */

    private void processSellOrLease() {
        System.out.print("Enter VIN: ");
        String vin = scanner.nextLine().trim();
        Vehicle vehicle = dealership.getVehicleByVin(vin);
        if (vehicle == null) {
            System.out.println("Vehicle not found.");
            return;
        }

        System.out.print("Customer name: ");
        String name = scanner.nextLine();
        System.out.print("Customer email: ");
        String email = scanner.nextLine();
        System.out.print("sale or lease: ");
        String kind = scanner.nextLine().toLowerCase();

        Contract contract;
        if ("sale".equals(kind)) {
            ArrayList<AddOn> addOns = promptAddOns();
            contract = new SalesContract(java.time.LocalDate.now().toString(),
                    name, email, vehicle, addOns);
        } else if ("lease".equals(kind)) {
            int age = java.time.LocalDate.now().getYear() - vehicle.getYear();
            if (age > 3) {
                System.out.println("Vehicle too old for lease (>3 years).");
                return;
            }
            contract = new LeaseContract(java.time.LocalDate.now().toString(),
                    name, email, vehicle);
        } else {
            System.out.println("Invalid choice.");
            return;
        }

        cfm.saveContract(contract);
        dealership.removeVehicle(vehicle);
        dfm.saveDealership(dealership);
        System.out.printf("Contract saved. Monthly payment: %.2f%n",
                contract.calculatePayment());
    }

    /* helper for add-ons */
    private ArrayList<AddOn> promptAddOns() {
        ArrayList<AddOn> list = new ArrayList<>();
        AddOn[] options = AddOn.values();
        System.out.println("Select add-ons (comma-separated numbers or blank for none):");
        for (int i = 0; i < options.length; i++) {
            System.out.printf("%d. %s%n", i + 1, options[i]);
        }
        System.out.print("Choice: ");
        String[] toks = scanner.nextLine().split(",");
        for (String tk : toks) {
            try {
                int idx = Integer.parseInt(tk.trim()) - 1;
                if (idx >= 0 && idx < options.length) list.add(options[idx]);
            } catch (NumberFormatException ignored) { }
        }
        return list;
    }
}
