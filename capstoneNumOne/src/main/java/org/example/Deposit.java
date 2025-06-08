package org.example;

import java.time.LocalDate;
import java.time.LocalTime;

public class Deposit {
    private LocalDate date;
    private LocalTime time;
    private String description;
    private String vendor;
    private double amount;
    private String depositMethod;

    // Constructor for Cash Deposit (no vendor required)
    public Deposit(LocalDate date, LocalTime time, String description, double amount, String depositMethod) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = "N/A"; // No vendor for cash deposits
        this.amount = amount;
        this.depositMethod = depositMethod;
    }

    // Constructor for Check or Money Order Deposit (with vendor)
    public Deposit(LocalDate date, LocalTime time, String description, String vendor, double amount, String depositMethod) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
        this.depositMethod = depositMethod;
    }

    // Getters and Setters
    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDepositMethod() {
        return depositMethod;
    }

    public void setDepositMethod(String depositMethod) {
        this.depositMethod = depositMethod;
    }

    // toString method for deposit details
    @Override
    public String toString() {
        return "Deposit{" +
                "date=" + date +
                ", time=" + time +
                ", description='" + description + '\'' +
                ", vendor='" + vendor + '\'' +
                ", amount=" + String.format("%.2f", amount) +
                ", depositMethod='" + depositMethod + '\'' +
                '}';
    }
}
