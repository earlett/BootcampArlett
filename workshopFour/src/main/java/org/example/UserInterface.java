package org.example;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;

public class UserInterface {

    private Dealership dealership;

    public UserInterface() {
        init();
    }

    /* ---------- initialization ---------- */

    private void init() {
        DealershipFileManager fileManager = new DealershipFileManager();
        this.dealership = fileManager.getDealership();

        if (this.dealership == null) {
            System.out.println("ERROR: The dealership data file is missing or malformed.");
            System.out.println("Please ensure the CSV exists and has the correct format.");
            System.out.println("The application cannot continue.");
            System.exit(1);
        }
    }

    /* ---------- main menu loop ---------- */

    public void display() {
        boolean isRunning = true;
        Scanner scanner = new Scanner(System.in);

        while (isRunning) {
            System.out.printf("%nWelcome to %s%n", dealership.getName());
            System.out.printf("Address: %s%n", dealership.getAddress());
            System.out.println("Choose an option:");
            System.out.println("99. Exit");
            System.out.println("1. List all vehicles");
            System.out.println("2. Search by price");
            System.out.println("3. Search by make/model");
            System.out.println("4. Search by year");
            System.out.println("5. Search by color");
            System.out.println("6. Search by mileage");
            System.out.println("7. Search by type");
            System.out.println("8. Add a vehicle");
            System.out.println("9. Remove a vehicle");
            System.out.println("10. Sell / Lease a vehicle");   // NEW line

            try {
                int choice = scanner.nextInt();

                switch (choice) {
                    case 99:
                        new DealershipFileManager().saveDealership(dealership);   // final flush
                        System.exit(99);
                        break;
                    case 1:  processGetAllVehiclesRequest();
                    break;
                    case 2:  processGetByPriceRequest();
                    break;
                    case 3:  processGetByMakeModelRequest();
                    break;
                    case 4:  processGetByYearRequest();
                    break;
                    case 5:  processGetByColorRequest();
                    break;
                    case 6:  processGetByMileageRequest();
                    break;
                    case 7:  processGetByVehicleTypeRequest();
                    break;
                    case 8:  processAddVehicleRequest();
                    break;
                    case 9:  processRemoveVehicleRequest();
                    break;
                    case 10: processSellOrLeaseVehicleRequest();
                    break;
                    default:
                        System.out.println("Not a menu option – try again!");
                }
            } catch (InputMismatchException ex) {
                System.out.println("Please enter a number.");
                scanner.nextLine();   // clear bad input
            }
        }
    }

    /* ---------- search helpers ---------- */

    public void processGetAllVehiclesRequest()   { displayVehicles(dealership.getAllVehicles()); }

    public void processGetByPriceRequest() {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.print("Min price: ");
            double min = sc.nextDouble();
            System.out.print("Max price: ");
            double max = sc.nextDouble();
            if (min <= max) {
                displayVehicles(dealership.getVehiclesByPrice(min, max));
            } else {
                System.out.println("Min cannot exceed max.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Enter numeric values for price.");
        }
    }

    public void processGetByMakeModelRequest() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Make: ");
        String make = sc.nextLine();
        System.out.print("Model: ");
        String model = sc.nextLine();
        displayVehicles(dealership.getVehiclesByMakeModel(make, model));
    }

    public void processGetByYearRequest() {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.print("Min year: ");
            int min = sc.nextInt();
            System.out.print("Max year: ");
            int max = sc.nextInt();
            if (min <= max) {
                displayVehicles(dealership.getVehiclesByYear(min, max));
            } else {
                System.out.println("Min cannot exceed max.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Enter whole numbers for year.");
        }
    }

    public void processGetByColorRequest() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Color: ");
        String color = sc.nextLine();
        displayVehicles(dealership.getVehiclesByColor(color));
    }

    public void processGetByMileageRequest() {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.print("Min mileage: ");
            int min = sc.nextInt();
            System.out.print("Max mileage: ");
            int max = sc.nextInt();
            if (min <= max) {
                displayVehicles(dealership.getVehiclesByMileage(min, max));
            } else {
                System.out.println("Min cannot exceed max.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Enter whole numbers for mileage.");
        }
    }

    public void processGetByVehicleTypeRequest() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Types: " + Arrays.toString(VehicleType.values()));
        System.out.print("Type: ");
        try {
            VehicleType type = VehicleType.valueOf(sc.nextLine().trim().toUpperCase());
            displayVehicles(dealership.getVehiclesByType(type));
        } catch (IllegalArgumentException e) {
            System.out.println("Unknown type.");
        }
    }

    /* ---------- mutate inventory ---------- */

    public void processAddVehicleRequest() {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.print("VIN: ");
            int vin = sc.nextInt();   sc.nextLine();
            System.out.print("Year: ");
            int year = sc.nextInt();  sc.nextLine();
            System.out.print("Make: ");
            String make = sc.nextLine();
            System.out.print("Model: ");
            String model = sc.nextLine();
            System.out.print("Color: ");
            String color = sc.nextLine();

            VehicleType type = null;
            while (type == null) {
                System.out.print("Type (see list above): ");
                try { type = VehicleType.valueOf(sc.nextLine().trim().toUpperCase()); }
                catch (IllegalArgumentException e) { System.out.println("Bad type, try again."); }
            }

            System.out.print("Odometer: ");
            int odo = sc.nextInt();   sc.nextLine();
            System.out.print("Price: ");
            double price = sc.nextDouble();

            Vehicle v = new Vehicle(vin, year, make, model, color, type, odo, price);
            dealership.addVehicle(v);

            // persist change immediately
            new DealershipFileManager().saveDealership(dealership);

            System.out.println("Vehicle added!");
        }
        catch (InputMismatchException e) {
            System.out.println("Bad input – vehicle NOT added.");
        }
    }

    public void processRemoveVehicleRequest() {
        Scanner sc = new Scanner(System.in);
        try {
            System.out.print("VIN to remove: ");
            int vin = sc.nextInt();

            Vehicle toRemove = null;
            for (Vehicle v : dealership.getAllVehicles()) {
                if (v.getVin() == vin) { toRemove = v; break;
                }
            }

            if (toRemove != null) {
                dealership.removeVehicle(toRemove);

                // persist removal immediately
                new DealershipFileManager().saveDealership(dealership);

                System.out.println("Removed.");
            }
            else {
                System.out.println("VIN not found.");
            }
        }
        catch (InputMismatchException e) {
            System.out.println("Enter a whole-number VIN.");
        }
    }

    //sell / lease helper

    private void processSellOrLeaseVehicleRequest() {
        Scanner sc = new Scanner(System.in);

        System.out.print("VIN of the vehicle: ");
        int vin = sc.nextInt();  sc.nextLine();

        Vehicle chosen = null;
        for (Vehicle v : dealership.getAllVehicles()) {
            if (v.getVin() == vin) { chosen = v; break;
            }
        }
        if (chosen == null) {
            System.out.println("VIN not found.");
            return;
        }

        // customer info
        System.out.print("Customer name: ");
        String name = sc.nextLine();
        System.out.print("Customer e-mail: ");
        String email = sc.nextLine();

        // contract type
        String kind;
        do {
            System.out.print("Type of contract (SALE / LEASE): ");
            kind = sc.nextLine().trim().toUpperCase();
        }
        while (!kind.equals("SALE") && !kind.equals("LEASE"));

        String today = LocalDate.now().toString().replaceAll("-", "");  // YYYYMMDD

        Contract contract;

        if (kind.equals("SALE")) {
            System.out.print("Finance the purchase? (yes / no): ");
            boolean finance = sc.nextLine().trim().equalsIgnoreCase("yes");
            contract = new SalesContract(today, name, email, chosen, finance);
        }
        else {
            int age = LocalDate.now().getYear() - chosen.getYear();
            if (age > 3) {
                System.out.println("Sorry – you can’t lease vehicles older than 3 years.");
                return;
            }
            contract = new LeaseContract(today, name, email, chosen);
        }

        /* persist the contract */
        new ContractFileManager().saveContract(contract);

        /* remove car from lot & persist inventory */
        dealership.removeVehicle(chosen);
        new DealershipFileManager().saveDealership(dealership);

        System.out.printf("%s recorded. Total: $%.2f  Monthly: $%.2f%n",
                contract.getType(), contract.getTotalPrice(), contract.getMonthlyPayment());
    }

    /* ---------- tiny helper ---------- */

    private void displayVehicles(List<Vehicle> list) {
        if (list.isEmpty()) {
            System.out.println("No matching vehicles.");
        }
        else {
            list.forEach(v -> System.out.println(v.toString()));
        }
    }
}
