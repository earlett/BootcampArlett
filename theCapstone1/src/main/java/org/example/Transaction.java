package org.example;

import java.time.LocalDate;
import java.time.LocalTime;

public class Transaction {
    private LocalDate date;
    private LocalTime time;
    private String description;
    private String vendor;
    private double amount;
    private String method;

    public Transaction(LocalDate date, LocalTime time, String description,
                       String vendor, double amount, String method) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
        this.method = method;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getVendor() {
        return vendor;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return String.format(
                "%s|%s|%s|%s|%.2f|%s",
                date, time, description, vendor, amount, method
        );
    }
}
