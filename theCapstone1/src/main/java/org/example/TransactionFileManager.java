package org.example;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TransactionFileManager {
    private static final String FILE = "ledger.csv";

    public static void appendTransaction(Transaction tx) {
        boolean isNew = !new File(FILE).exists();
        try (PrintWriter out = new PrintWriter(new FileWriter(FILE, true))) {
            if (isNew) {
                out.println("date|time|description|vendor|amount|method");
            }
            out.println(tx.toString());
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }

    public static List<Transaction> readAll() {
        List<Transaction> list = new ArrayList<>();
        File file = new File(FILE);
        if (!file.exists()) {
            return list;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            reader.readLine(); // skip header
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length < 6) continue;
                LocalDate date = LocalDate.parse(parts[0]);
                LocalTime time = LocalTime.parse(parts[1]);
                String description = parts[2];
                String vendor = parts[3];
                double amount = Double.parseDouble(parts[4]);
                String method = parts[5];
                Transaction t = new Transaction(date, time, description, vendor, amount, method);
                list.add(t);
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        // newest first
        Collections.reverse(list);
        return list;
    }

    public static void viewTransactions() {
        List<Transaction> transactions = readAll();
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            for (Transaction t : transactions) {
                System.out.println(t);
            }
        }
    }

    public static List<Transaction> searchByVendor(String vendor) {
        List<Transaction> found = new ArrayList<>();
        for (Transaction t : readAll()) {
            if (t.getVendor().equalsIgnoreCase(vendor)) {
                found.add(t);
            }
        }
        return found;
    }

    public static List<Transaction> searchByYear(int year) {
        List<Transaction> found = new ArrayList<>();
        for (Transaction t : readAll()) {
            if (t.getDate().getYear() == year) {
                found.add(t);
            }
        }
        return found;
    }
}
