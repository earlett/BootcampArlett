package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;


public class DealershipFileManager {
    private static final String FILE_PATH = "inventory.csv";

    /* ---------- public API ---------- */

    public Dealership getDealership() {
        File file = new File(FILE_PATH);
        if (!file.exists()) copyDefaultResource(file);
        return loadFromFile(file);
    }

    public void saveDealership(Dealership d) {
        if (d == null) return;
        try (PrintWriter out = new PrintWriter(new FileWriter(FILE_PATH))) {
            out.println(d.getName() + "|" + d.getAddress() + "|" + d.getPhone());
            for (Vehicle v : d.getAllVehicles()) out.println(v.toCsvString());
        } catch (IOException e) {
            System.err.println("Could not save inventory: " + e.getMessage());
        }
    }

    /* ---------- internals ---------- */

    private Dealership loadFromFile(File file) {
        try (Scanner in = new Scanner(file)) {
            if (!in.hasNextLine()) {
                System.err.println("ERROR: " + FILE_PATH + " is empty.");
                return null;
            }
            String[] header = in.nextLine().split("\\|");
            if (header.length < 3) {
                System.err.println("ERROR: Invalid header line.");
                return null;
            }
            Dealership d = new Dealership(header[0], header[1], header[2]);

            while (in.hasNextLine()) {
                String[] row = in.nextLine().trim().split("\\|");
                if (row.length > 8) continue;   // skip malformed rows

                String vin   = row[0];
                int year     = Integer.parseInt(row[1]);
                String make  = row[2];
                String model = row[3];

                // Columns 4 & 5: could be (type,color) OR (color,type).
                String col4 = row[4];
                String col5 = row[5];

                VehicleType type;
                String color;

                // try col4 as enum first
                try {
                    type  = VehicleType.valueOf(col4.toUpperCase());
                    color = col5;
                } catch (IllegalArgumentException ex) {
                    // swap: col5 must be the type
                    color = col4;
                    try {
                        type = VehicleType.valueOf(col5.toUpperCase());
                    } catch (IllegalArgumentException ex2) {
                        // unknown type—default
                        type = VehicleType.OTHER;
                    }
                }

                int odo      = Integer.parseInt(row[6]);
                double price = Double.parseDouble(row[7]);

                d.addVehicle(new Vehicle(vin, year, make, model, color, type, odo, price));
            }
            return d;
        } catch (FileNotFoundException e) {
            System.err.println("ERROR: " + FILE_PATH + " not found.");
            return null;
        }
    }

    private void copyDefaultResource(File target) {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(FILE_PATH)) {
            if (is == null) return;       // no bundled default
            try (OutputStream os = new FileOutputStream(target)) {
                is.transferTo(os);
            }
            System.out.println("inventory.csv copied from resources.");
        } catch (IOException e) {
            System.err.println("Cannot bootstrap inventory.csv: " + e.getMessage());
        }
    }
}
