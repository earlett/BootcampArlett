package com.example.evies_car_dealership.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class LeaseContract extends Contract {

    private final LocalDate leaseStart;
    private final LocalDate leaseEnd;
    private final BigDecimal monthlyPayment;
    private final int mileageLimit;
    private final String paymentMethod;

    public LeaseContract(int id, int vin, int dealershipId, int customerId,
                         LocalDate leaseStart, LocalDate leaseEnd,
                         BigDecimal monthlyPayment, int mileageLimit,
                         String paymentMethod) {
        super(id, vin, dealershipId, customerId, leaseStart);
        this.leaseStart = leaseStart;
        this.leaseEnd = leaseEnd;
        this.monthlyPayment = monthlyPayment;
        this.mileageLimit = mileageLimit;
        this.paymentMethod = paymentMethod;
    }

    public LocalDate getLeaseStart() {
        return leaseStart;
    }

    public LocalDate getLeaseEnd() {
        return leaseEnd;
    }

    public BigDecimal getMonthlyPayment() {
        return monthlyPayment;
    }

    public int getMileageLimit() {
        return mileageLimit;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    @Override
    public BigDecimal calculatePayment() {
        return monthlyPayment;  // monthly amount already provided
    }

    @Override
    public String getType() {
        return "Lease";
    }
}
