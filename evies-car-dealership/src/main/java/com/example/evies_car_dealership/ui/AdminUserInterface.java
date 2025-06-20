package com.example.evies_car_dealership.ui;

import com.example.evies_car_dealership.dao.ContractDao;
import com.example.evies_car_dealership.dao.ContractDaoImpl;
import com.example.evies_car_dealership.model.Contract;
import com.example.evies_car_dealership.model.LeaseContract;
import com.example.evies_car_dealership.model.SalesContract;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdminUserInterface {

    private static final String PASSWORD = "evie123";

    private final Scanner scanner;
    private final ContractDao dao = new ContractDaoImpl();   // <-- NEW

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
                default  -> System.out.println("Invalid choice.");
            }
        }
    }

    /* -------------------------------------------------- */

    private void listContracts(int max) {
        // hard-coded dealership 1; change later if you support multiple
        List<SalesContract> sales  = dao.getSalesContractsByDealershipId(1);
        List<LeaseContract> leases = dao.getLeaseContractsByDealershipId(1);

        ArrayList<Contract> combined = new ArrayList<>();
        combined.addAll(sales);
        combined.addAll(leases);

        if (combined.isEmpty()) {
            System.out.println("No contracts.");
            return;
        }

        // newest first
        combined.sort((a, b) -> b.getContractDate().compareTo(a.getContractDate()));

        int count = 0;
        for (Contract c : combined) {
            if (++count > max) break;
            System.out.println(c);
        }
    }
}
