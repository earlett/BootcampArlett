package org.example;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Welcome to the Banking System.");
            System.out.println("Please select an option:");
            System.out.println("1. Add Deposit");
            System.out.println("2. Make Payment");
            System.out.println("3. View Ledger");
            System.out.println("4. Search Transaction by Vendor");
            System.out.println("5. Search Transaction by Year");
            System.out.println("6. Exit");

            int choice = 0;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    makeDeposit(scanner);
                    break;
                case 2:
                    makePayment(scanner);
                    break;
                case 3:
                    viewLedger();
                    break;
                case 4:
                    searchTransactionByVendor(scanner);
                    break;
                case 5:
                    searchTransactionByYear(scanner);
                    break;
                case 6:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    // Custom method to add a deposit
    public static void makeDeposit(Scanner scanner) {
        System.out.println("\n--- Make a Deposit ---");
        System.out.println("Enter the amount to deposit:");
        double amount = 0;
        try {
            amount = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid amount.");
            return;
        }

        System.out.println("Enter the deposit method (Cash, Check, Money Order):");
        String depositMethod = scanner.nextLine();

        System.out.println("Enter the vendor name:");
        String vendorName = scanner.nextLine();

        Deposit deposit = new Deposit(LocalDate.now(), LocalTime.now(), "Deposit Transaction", vendorName, amount, depositMethod);
        TransactionFileManager.appendTransaction(deposit);

        System.out.println("Deposit made successfully!");
    }

    // Custom method to make a payment (payment transaction)
    public static void makePayment(Scanner scanner) {
        System.out.println("\n--- Make a Payment ---");
        System.out.println("Enter the amount to pay:");
        double amount = 0;
        try {
            amount = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid amount.");
            return;
        }

        System.out.println("Enter the payment method (Cash, Credit, Debit):");
        String paymentMethod = scanner.nextLine();

        System.out.println("Enter the vendor name:");
        String vendorName = scanner.nextLine();

        // Adjust amount to be negative for withdrawals (debited from the account)
        amount = -1 * amount;  // Negating the amount

        Withdrawal payment = new Withdrawal(LocalDate.now(), LocalTime.now(), "Payment Transaction", vendorName, amount, paymentMethod);
        TransactionFileManager.appendTransaction(payment);

        System.out.println("Payment made successfully!");
    }

    // Custom method to view ledger (all transactions)
    public static void viewLedger() {
        System.out.println("\n--- View Ledger ---");
        TransactionFileManager.viewTransactions();
    }

    // Custom method to search transactions by vendor
    public static void searchTransactionByVendor(Scanner scanner) {
        System.out.println("\n--- Search Transaction by Vendor ---");
        System.out.println("Enter vendor name:");
        String vendorName = scanner.nextLine();

        List<Transaction> transactions = TransactionFileManager.searchByVendor(vendorName);
        if (transactions.isEmpty()) {
            System.out.println("No transactions found for vendor: " + vendorName);
        } else {
            for (Transaction transaction : transactions) {
                System.out.println(transaction);
            }
        }
    }

    // Custom method to search transactions by year
    public static void searchTransactionByYear(Scanner scanner) {
        System.out.println("\n--- Search Transaction by Year ---");
        System.out.println("Enter the year:");
        int year = 0;
        try {
            year = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid year input. Please enter a valid year.");
            return;
        }

        List<Transaction> transactions = TransactionFileManager.searchByYear(year);
        if (transactions.isEmpty()) {
            System.out.println("No transactions found for the year " + year);
        } else {
            for (Transaction transaction : transactions) {
                System.out.println(transaction);
            }
        }
    }
}
