package org.example;

import java.util.ArrayList;
import java.util.Scanner;

public class AdminUserInterface {
    private static final String PASSWORD = "evie123";

    private final Scanner scanner;
    private final ContractFileManager cfm = new ContractFileManager();

    public AdminUserInterface(Scanner scanner) {
        this.scanner = scanner;
    }

    public void display() {
        System.out.print("Enter admin password: ");
        if (!scanner.nextLine().equals(PASSWORD)) {
            System.out.println("Wrong password.");
            return;
        }
        boolean running = true;
        while (running) {
            System.out.println("\n-- ADMIN MENU --");
            System.out.println("1. List ALL contracts");
            System.out.println("2. List LAST 10 contracts");
            System.out.println("0. Back");
            System.out.print("Choice: ");
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> listContracts(Integer.MAX_VALUE);
                case "2" -> listContracts(10);
                case "0" -> running = false;
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    private void listContracts(int max) {
        ArrayList<Contract> list = cfm.loadContracts();
        if (list.isEmpty()) {
            System.out.println("No contracts.");
            return;
        }
        int start = Math.max(0, list.size() - max);
        for (int i = list.size() - 1; i >= start; i--) {
            System.out.println(list.get(i));
        }
    }
}
