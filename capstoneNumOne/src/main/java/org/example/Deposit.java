package org.example;

import java.time.LocalDate;
import java.time.LocalTime;

public class Deposit extends Transaction {
    private String depositMethod;

    public Deposit(LocalDate date, LocalTime time, String description, String vendor, double amount, String depositMethod) {
        super(date, time, description, vendor, amount, "Deposit");
        this.depositMethod = depositMethod;
    }

    public String getDepositMethod() {
        return depositMethod;
    }

    @Override
    public String toString() {
        return super.toString() + "|" + depositMethod;
    }
}

