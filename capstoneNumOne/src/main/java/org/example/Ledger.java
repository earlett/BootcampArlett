package org.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class Ledger {
    private static Ledger instance;  // Singleton instance

    private List<Transaction> transactions = new ArrayList<>();

    // Private constructor to prevent direct instantiation
    private Ledger() {}

    // Public method to return the singleton instance
    public static Ledger getInstance() {
        if (instance == null) {
            instance = new Ledger();
        }
        return instance;
    }

    // Adds a transaction to the ledger
    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    // Display all transactions
    public void displayLedger() {
        if (transactions.isEmpty()) {
            System.out.println("No transactions recorded.");
        } else {
            for (Transaction transaction : transactions) {
                System.out.println(transaction);
            }
        }
    }

    // Display only deposit transactions
    public void displayDeposits() {
        List<Transaction> deposits = transactions.stream()
                .filter(t -> t.getType().equalsIgnoreCase("credit"))
                .collect(Collectors.toList());

        if (deposits.isEmpty()) {
            System.out.println("No deposits found.");
        } else {
            for (Transaction t : deposits) {
                System.out.println(t);
            }
        }
    }

    // Display only withdrawal transactions
    public void displayWithdrawals() {
        List<Transaction> withdrawals = transactions.stream()
                .filter(t -> t.getType().equalsIgnoreCase("debit"))
                .collect(Collectors.toList());

        if (withdrawals.isEmpty()) {
            System.out.println("No withdrawals found.");
        } else {
            for (Transaction t : withdrawals) {
                System.out.println(t);
            }
        }
    }

    // Search transactions by vendor
    public List<Transaction> searchByVendor(String vendor) {
        return transactions.stream()
                .filter(t -> t.getVendor().equalsIgnoreCase(vendor))
                .collect(Collectors.toList());
    }

    // Search transactions by year
    public List<Transaction> searchByYear(int year) {
        return transactions.stream()
                .filter(t -> t.getDate().getYear() == year)
                .collect(Collectors.toList());
    }

    // Generate a report based on a filtered list of transactions
    public static void generateReport(List<Transaction> filtered) {
        double totalDeposits = filtered.stream()
                .filter(t -> t.getType().equalsIgnoreCase("credit"))
                .mapToDouble(Transaction::getAmount)
                .sum();

        double totalWithdrawals = filtered.stream()
                .filter(t -> t.getType().equalsIgnoreCase("debit"))
                .mapToDouble(t -> Math.abs(t.getAmount))
                .sum();

        double balance = totalDeposits - totalWithdrawals;

        System.out.println("Total Deposits: $" + totalDeposits);
        System.out.println("Total Withdrawals: $" + totalWithdrawals);
        System.out.println("Net Balance: $" + balance);
        System.out.println("--- Matching Transactions ---");
        filtered.forEach(System.out::println);
    }

    // Getter used for external access (e.g., from AppLedger)
    public List<Transaction> getTransactions() {
        return transactions;
    }
}
