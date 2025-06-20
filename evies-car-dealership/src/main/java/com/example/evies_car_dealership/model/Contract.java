package com.example.evies_car_dealership.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;


public abstract class Contract {

    private final int id;            // primary key (SIN or CIN)
    private final int vin;
    private final int dealershipId;
    private final int customerId;
    private final LocalDate contractDate;

    protected Contract(String id,
                       String vin,
                       String dealershipId,
                       Vehicle customerId,
                       ArrayList<AddOn> contractDate) {

        this.id           = id;
        this.vin          = vin;
        this.dealershipId = dealershipId;
        this.customerId   = customerId;
        this.contractDate = contractDate;
    }

    public int       getId()           { return id; }
    public int       getVin()          { return vin; }
    public int       getDealershipId() { return dealershipId; }
    public int       getCustomerId()   { return customerId; }
    public LocalDate getContractDate() { return contractDate; }

    /* ───────────── abstract hooks ───────────── */

    /** Total or monthly payment, depending on subclass logic. */
    public abstract BigDecimal calculatePayment();

    /** Returns "Sale" or "Lease". */
    public abstract String getType();


    @Override
    public String toString() {
        return "%s Contract — VIN %d  $%s".formatted(
                getType(), vin, calculatePayment().toPlainString());
    }
}