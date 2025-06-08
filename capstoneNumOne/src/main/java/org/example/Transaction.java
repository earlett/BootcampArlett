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
    private String type;  // "debit" or "credit"

    // Constructor
    public Transaction(LocalDate date, LocalTime time, String description, String vendor,
                       double amount, String method, String type) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
        this.method = method;
        this.type = type;
    }

    // Getter and Setter for date
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    // Getter and Setter for time
    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    // Getter and Setter for description
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Getter and Setter for vendor
    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    // Getter and Setter for amount
    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    // Getter and Setter for method
    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    // Getter and Setter for type
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    // toString override for readable output
    @Override
    public String toString() {
        return String.format(
                "Transaction{date=%s, time=%s, description='%s', vendor='%s', amount=%.2f, method='%s', type='%s'}",
                date, time, description, vendor, amount, method, type
        );
    }
}



