package org.example;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionFileManager {

    public static void appendTransaction(Transaction transaction) {
        String filePath = "src/main/resources/transactions.csv";
        File file = new File(filePath);

        try {
            // Step 1: Make sure the folder exists
            File folder = file.getParentFile();
            if (!folder.exists()) {
                folder.mkdirs();
            }

            // Step 2: Check if file exists and is empty
            boolean fileExists = file.exists();
            boolean isEmpty = !fileExists || file.length() == 0;

            // Step 3: Open file in append mode
            FileWriter writer = new FileWriter(file, true);

            // Step 4: Write header if it's a new or empty file
            if (isEmpty) {
                writer.write("date|time|description|vendor|amount|transactionType|method\n");
            }

            // Step 5: Write the transaction
            writer.write(transaction.toString() + "\n");

            // Step 6: Close the writer
            writer.close();

        } catch (IOException e) {
            System.out.println("Error writing the transaction.");
            e.printStackTrace();
        }
    }

    public static List<Transaction> readTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        String filePath = "src/main/resources/transactions.csv";
        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println("File not found.");
            return transactions;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            reader.readLine(); // Skip the header
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split("\\|");
                String type = fields[5];
                if (type.equals("Deposit")) {
                    transactions.add(new Deposit(
                            LocalDate.parse(fields[0]), LocalTime.parse(fields[1]),
                            fields[2], fields[3], Double.parseDouble(fields[4]),
                            fields[6]
                    ));
                } else if (type.equals("Withdrawal")) {
                    transactions.add(new Withdrawal(
                            LocalDate.parse(fields[0]), LocalTime.parse(fields[1]),
                            fields[2], fields[3], Double.parseDouble(fields[4]),
                            fields[6]
                    ));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the file.");
            e.printStackTrace();
        }

        return transactions;
    }

    public static List<Transaction> searchByVendor(String vendor) {
        List<Transaction> transactions = new ArrayList<>();
        for (Transaction transaction : readTransactions()) {
            if (transaction.getVendor().toLowerCase().contains(vendor.toLowerCase())) {
                transactions.add(transaction);
            }
        }
        return transactions;
    }

    public static List<Transaction> searchByYear(int year) {
        List<Transaction> transactions = new ArrayList<>();
        for (Transaction transaction : readTransactions()) {
            if (transaction.getDate().getYear() == year) {
                transactions.add(transaction);
            }
        }
        return transactions;
    }

    public static void viewTransactions() {
        List<Transaction> transactions = readTransactions();
        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            for (Transaction transaction : transactions) {
                System.out.println(transaction);
            }
        }
    }
}
