package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class ContractFileManager {
    private static final String FILE_PATH = "contracts.csv";

    public void saveContract(Contract c) {
        ensureFileExists();

        try (PrintWriter out = new PrintWriter(new FileWriter(FILE_PATH, true))) {
            if (c instanceof SalesContract s) {
                String addOns = String.join(";", s.getAddOns().stream()
                        .map(Enum::name)
                        .toList());
                out.printf("SALE|%s|%s|%s|%s|%.2f|%.2f|%.2f|%s%n",
                        s.getContractDate(), s.getCustomerName(), s.getCustomerEmail(),
                        s.getVehicle().toCsvString(),
                        s.getSalesTax(), s.getRecordingFee(), s.getProcessingFee(),
                        addOns);
            } else if (c instanceof LeaseContract l) {
                out.printf("LEASE|%s|%s|%s|%s|%.2f|%.2f%n",
                        l.getContractDate(), l.getCustomerName(), l.getCustomerEmail(),
                        l.getVehicle().toCsvString(),
                        l.getExpectedEndingValue(), l.getLeaseFee());
            }
        } catch (IOException e) {
            System.err.println("saveContract failed: " + e.getMessage());
        }
    }

    // load all contracts
    public ArrayList<Contract> loadContracts() {
        ensureFileExists();

        ArrayList<Contract> list = new ArrayList<>();
        try (Scanner in = new Scanner(new File(FILE_PATH))) {
            while (in.hasNextLine()) {
                String line = in.nextLine().trim();
                if (line.isEmpty() || !line.contains("|")) continue;
                String[] p = line.split("\\|");

                try {
                    switch (p[0]) {
                        case "SALE"  -> list.add(new SalesContract(p));
                        case "LEASE" -> list.add(new LeaseContract(p));
                        default      -> System.err.println("Unknown row skipped: " + line);
                    }
                } catch (Exception ex) {
                    System.err.println("Bad contract row skipped: " + line);
                }
            }
        } catch (FileNotFoundException ignored) { }
        return list;
    }

    // copy default contracts.csv from resources if missing
    private void ensureFileExists() {
        File target = new File(FILE_PATH);
        if (target.exists()) return;

        try (InputStream is = getClass().getClassLoader().getResourceAsStream(FILE_PATH)) {
            if (is == null) return;
            try (OutputStream os = new FileOutputStream(target)) {
                is.transferTo(os);
            }
            System.out.println("contracts.csv copied from resources.");
        } catch (IOException e) {
            System.err.println("Cannot bootstrap contracts.csv: " + e.getMessage());
        }
    }
}
