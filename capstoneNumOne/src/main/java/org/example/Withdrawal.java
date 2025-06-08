package org.example;

import java.time.LocalDate;
import java.time.LocalTime;

public class Withdrawal {
    private LocalDate date;
    private LocalTime time;
    private String description;
    private String vendor;
    private double amount;
    private String withdrawalMethod;
    private String type = "Withdrawal";  // Default type for withdrawals

    // Constructor for Withdrawal
    public Withdrawal(LocalDate date, LocalTime time, String description, String vendor, double amount, String withdrawalMethod) {
        this.date = date;
        this.time = time;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
        this.withdrawalMethod = withdrawalMethod;
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

    public String getWithdrawalMethod() {
        return withdrawalMethod;
    }

    public void setWithdrawalMethod(String withdrawalMethod) {
        this.withdrawalMethod = withdrawalMethod;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    // toString method to print the withdrawal details
    @Override
    public String toString() {
        return "Withdrawal{" +
                "date=" + date +
                ", time=" + time +
                ", description='" + description + '\'' +
                ", vendor='" + vendor + '\'' +
                ", amount=" + amount +
                ", withdrawalMethod='" + withdrawalMethod + '\'' +
                '}';
    }
}
