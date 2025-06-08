package org.example;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Ledger ledger = Ledger.getInstance();

        while (true) {
            System.out.println("welcome to the banking system.");
            System.out.println("please select an option:");
            System.out.println("1. record a transaction");
            System.out.println("2. view ledger");
            System.out.println("3. exit");

            int choice = 0;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("invalid input. please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    recordTransaction(scanner, ledger);
                    break;
                case 2:
                    viewLedger(scanner, ledger);
                    break;
                case 3:
                    System.exit(0);
                    break;
                default:
                    System.out.println("invalid option. please try again.");
            }
        }
    }

    public static void recordTransaction(Scanner scanner, Ledger ledger) {
        System.out.println("--- record a transaction ---");

        System.out.println("is this a credit or debit? (enter 'credit' or 'debit'):");
        String type = scanner.nextLine().trim().toLowerCase();

        if (!type.equals("credit") && !type.equals("debit")) {
            System.out.println("invalid type. must be 'credit' or 'debit'.");
            return;
        }

        System.out.println("enter the amount:");
        double amount = 0;
        try {
            amount = Double.parseDouble(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("invalid amount.");
            return;
        }

        if (type.equals("debit")) {
            amount *= -1;
        }

        System.out.println("enter the transaction method (cash, check, credit, etc.):");
        String method = scanner.nextLine().trim();

        String vendor = "";
        if (!(type.equals("credit") && method.equalsIgnoreCase("cash"))) {
            System.out.println("enter the vendor name:");
            vendor = scanner.nextLine();
        }

        String description = type + " transaction";

        Transaction transaction = new Transaction(
                LocalDate.now(), LocalTime.now(),
                description, vendor, amount, method, type
        );

        ledger.addTransaction(transaction);
        System.out.println("transaction recorded successfully.");
        System.out.println(transaction);
    }

    public static void viewLedger(Scanner scanner, Ledger ledger) {
        while (true) {
            System.out.println("--- view ledger ---");
            System.out.println("please select an option:");
            System.out.println("1. view all transactions");
            System.out.println("2. view all deposits");
            System.out.println("3. view all withdrawals");
            System.out.println("4. search by vendor");
            System.out.println("5. search by year");
            System.out.println("6. back to main menu");

            int choice = 0;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("invalid input. please enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    ledger.displayLedger();
                    break;
                case 2:
                    ledger.displayDeposits();
                    break;
                case 3:
                    ledger.displayWithdrawals();
                    break;
                case 4:
                    searchTransactionByVendor(scanner, ledger);
                    break;
                case 5:
                    searchTransactionByYear(scanner, ledger);
                    break;
                case 6:
                    return;
                default:
                    System.out.println("invalid option. please try again.");
            }
        }
    }

    public static void searchTransactionByVendor(Scanner scanner, Ledger ledger) {
        System.out.println("enter vendor name:");
        String vendorName = scanner.nextLine();

        List<Transaction> transactions = ledger.searchByVendor(vendorName);
        if (transactions.isEmpty()) {
            System.out.println("no transactions found for vendor: " + vendorName);
        } else {
            for (Transaction transaction : transactions) {
                System.out.println(transaction);
            }
        }
    }

    public static void searchTransactionByYear(Scanner scanner, Ledger ledger) {
        System.out.println("enter the year:");
        int year = 0;
        try {
            year = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("invalid year input. please enter a valid year.");
            return;
        }

        List<Transaction> transactions = ledger.searchByYear(year);
        if (transactions.isEmpty()) {
            System.out.println("no transactions found for the year " + year);
        } else {
            for (Transaction transaction : transactions) {
                System.out.println(transaction);
            }
        }
    }
}




