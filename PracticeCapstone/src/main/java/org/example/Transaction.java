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
    private String method;

    public Transaction(LocalDate date, LocalTime time, String description, String vendor, double amount, String transactionType, String method) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
        this.transactionType = transactionType;
        this.method = method;
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

    public String getMethod() {
        return method;
    }

    public String toCsvString() {
        return String.format("%s|%s|%s|%s|%.2f|%s|%s",
                date.toString(), time.toString(), description, vendor, amount, transactionType, method);
    }

    @Override
    public String toString() {
        return String.format("%s|%s|%s|%s|%.2f|%s|%s",
                date.toString(), time.toString(), description, vendor, amount, transactionType, method);
    }
}

