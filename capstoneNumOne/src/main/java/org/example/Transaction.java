package org.example;

import java.time.LocalDate;
import java.time.LocalTime;

public class Transaction {
    private LocalDate date;
    private LocalTime time;
    private String description;
    private String vendor;
    private double amount;
    private String transactionType;

    public Transaction(LocalDate date, LocalTime time, String description, String vendor, double amount, String transactionType) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
        this.transactionType = transactionType;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }

    public String getVendor() {
        return vendor;
    }

    public double getAmount() {
        return amount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    @Override
    public String toString() {
        return String.format("%s|%s|%s|%s|%.2f|%s",
                date.toString(), time.toString(), description, vendor, amount, transactionType);
    }
}
