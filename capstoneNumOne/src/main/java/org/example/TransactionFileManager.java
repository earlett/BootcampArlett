package org.example;

import java.util.ArrayList;
import java.util.List;

public class TransactionFileManager {

    private static final List<Transaction> transactions = new ArrayList<>();

    public static void appendTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public static void viewTransactions() {
        if (transactions.isEmpty()) {
            System.out.println("No transactions recorded.");
        } else {
            for (Transaction t : transactions) {
                System.out.println(t);
            }
        }
    }

    public static List<Transaction> searchByVendor(String vendorName) {
        List<Transaction> result = new ArrayList<>();
        for (Transaction t : transactions) {
            if (t.getVendor() != null && t.getVendor().equalsIgnoreCase(vendorName)) {
                result.add(t);
            }
        }
        return result;
    }

    public static List<Transaction> searchByYear(int year) {
        List<Transaction> result = new ArrayList<>();
        for (Transaction t : transactions) {
            if (t.getDate().getYear() == year) {
                result.add(t);
            }
        }
        return result;
    }
}

