package com.example.evies_car_dealership.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SalesContract extends Contract {

    private final BigDecimal salePrice;
    private final boolean    warrantyIncluded;
    private final String     paymentMethod;

    public SalesContract(int id, int vin, int dealershipId, int customerId,
                         LocalDate date, BigDecimal salePrice,
                         boolean warrantyIncluded, String paymentMethod) {
        super(id, vin, dealershipId, customerId, date);
        this.salePrice         = salePrice;
        this.warrantyIncluded  = warrantyIncluded;
        this.paymentMethod     = paymentMethod;
    }

    public BigDecimal getSalePrice()        { return salePrice; }
    public boolean    isWarrantyIncluded()  { return warrantyIncluded; }
    public String     getPaymentMethod()    { return paymentMethod; }

    @Override
    public BigDecimal calculatePayment() {
        // basic example: price + $300 doc fee + optional $1k warranty
        BigDecimal docFee = new BigDecimal("300.00");
        BigDecimal warranty = warrantyIncluded ? new BigDecimal("1000.00") : BigDecimal.ZERO;
        return salePrice.add(docFee).add(warranty);
    }

    @Override
    public String getType() { return "Sales"; }
}

