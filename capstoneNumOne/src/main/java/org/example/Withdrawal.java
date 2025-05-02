package org.example;

import java.time.LocalDate;
import java.time.LocalTime;

public class Withdrawal extends Transaction {
    private String withdrawalMethod;

    public Withdrawal(LocalDate date, LocalTime time, String description, String vendor, double amount, String withdrawalMethod) {
        super(date, time, description, vendor, amount, "Withdrawal");
        this.withdrawalMethod = withdrawalMethod;
    }

    public String getWithdrawalMethod() {
        return withdrawalMethod;
    }

    @Override
    public String toString() {
        return super.toString() + "|" + withdrawalMethod;
    }
}
