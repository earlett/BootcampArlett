package org.example;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println();
            System.out.println("Banking System");
            System.out.println("1. Transactions");
            System.out.println("2. View Ledger");
            System.out.println("3. Search by Vendor");
            System.out.println("4. Search by Year");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            if (choice == 1) {
                handleTransactions(scanner);
            } else if (choice == 2) {
                TransactionFileManager.viewTransactions();
            } else if (choice == 3) {
                searchByVendor(scanner);
            } else if (choice == 4) {
                searchByYear(scanner);
            } else if (choice == 5) {
                System.out.println("Goodbye!");
                break;
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
    }

    private static void handleTransactions(Scanner scanner) {
        System.out.println();
        System.out.println("Transactions:");
        System.out.println("1. Credit (money in)");
        System.out.println("2. Debit (money out)");
        System.out.print("Choose type: ");

        int type;
        try {
            type = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Returning to main menu.");
            return;
        }

        System.out.print("Amount: ");
        double amount;
        try {
            amount = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid number. Returning to menu.");
            return;
        }

        // Debit amounts must be stored as negative
        if (type == 2 && amount > 0) {
            amount = -amount;
        }

        System.out.print("Payment method (e.g. Cash, Card, Online, etc.): ");
        String method = scanner.nextLine();

        System.out.print("Vendor: ");
        String vendor = scanner.nextLine();

        // Clear description for ledger display
        String description = (type == 1) ? "Credit (Money In)" : "Debit (Money Out)";
        Transaction transaction = new Transaction(
                LocalDate.now(),
                LocalTime.now(),
                description,
                vendor,
                amount,
                method
        );

        TransactionFileManager.appendTransaction(transaction);
        System.out.println("Transaction saved.");
    }

    private static void searchByVendor(Scanner scanner) {
        System.out.print("Vendor: ");
        String vendor = scanner.nextLine();
        List<Transaction> found = TransactionFileManager.searchByVendor(vendor);

        if (found.isEmpty()) {
            System.out.println("No results.");
        } else {
            for (Transaction t : found) {
                System.out.println(t);
            }
        }
    }

    private static void searchByYear(Scanner scanner) {
        System.out.print("Year: ");
        int year;
        try {
            year = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid year.");
            return;
        }

        List<Transaction> found = TransactionFileManager.searchByYear(year);

        if (found.isEmpty()) {
            System.out.println("No results.");
        } else {
            for (Transaction t : found) {
                System.out.println(t);
            }
        }
    }
}

