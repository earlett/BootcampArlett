package org.example;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class DealershipFileManager {

    private static final String FILE_PATH = "src/main/resources/inventory.csv";

    /* ---------- READ ---------- */
    public Dealership getDealership() {
        Dealership dealership = null;

        try (FileInputStream fs = new FileInputStream(FILE_PATH);
             Scanner scanner   = new Scanner(fs)) {

            // header: name|address|phone
            if (!scanner.hasNextLine()) {
                System.err.println("inventory.csv is empty!");
                return null;
            }

            String[] header = scanner.nextLine().split("\\|");
            if (header.length != 3) {
                System.err.println("Header line malformed in inventory.csv");
                return null;
            }
            dealership = new Dealership(
                    header[0].trim(), header[1].trim(), header[2].trim());

            // vehicles
            while (scanner.hasNextLine()) {
                String[] row = scanner.nextLine().split("\\|");
                if (row.length != 8) continue;           // skip bad rows

                int    vin   = Integer.parseInt(row[0]);
                int    year  = Integer.parseInt(row[1]);
                String make  = row[2];
                String model = row[3];
                VehicleType type  = VehicleType.valueOf(row[4].trim().toUpperCase());
                String color = row[5];
                int    odo   = Integer.parseInt(row[6]);
                double price = Double.parseDouble(row[7]);

                dealership.addVehicle(
                        new Vehicle(vin, year, make, model, color, type, odo, price));
            }
        }
        catch (FileNotFoundException e) {
            System.err.println("Could not find inventory.csv at " + FILE_PATH);
        }
        catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
        }

        return dealership;
    }

    /* ---------- WRITE ---------- */
    public void saveDealership(Dealership dealership) {
        if (dealership == null) return;

        try (FileWriter fw = new FileWriter(FILE_PATH, false)) {   // overwrite
            fw.write(String.format("%s|%s|%s%n",
                    dealership.getName(),
                    dealership.getAddress(),
                    dealership.getPhone()));

            for (Vehicle v : dealership.getAllVehicles()) {
                fw.write(String.format(
                        "%d|%d|%s|%s|%s|%s|%d|%.2f%n",
                        v.getVin(), v.getYear(), v.getMake(), v.getModel(),
                        v.getVehicleType(), v.getColor(),
                        v.getOdometer(), v.getPrice()));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
