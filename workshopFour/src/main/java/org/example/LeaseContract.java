package org.example;

public class LeaseContract extends Contract {
    private static final double LEASE_FEE_RATE = 0.07;
    private static final double RESIDUAL_RATE  = 0.50;

    private double expectedEndingValue;
    private double leaseFee;

    public LeaseContract(String date, String customerName, String customerEmail, Vehicle vehicleSold) {
        super(date, customerName, customerEmail, vehicleSold);
        calculatePayment();
    }

    @Override
    public void calculatePayment() {
        double price = getVehicleSold().getPrice();
        expectedEndingValue = price * RESIDUAL_RATE;
        leaseFee            = price * LEASE_FEE_RATE;

        double total = price + leaseFee;
        setTotalPrice(total);

        /* 4 % APR, 36 months */
        double monthlyRate = 0.04 / 12;
        int    months      = 36;

        /* pay only depreciation, not full price */
        double depreciation = price - expectedEndingValue;
        double payment = (depreciation * monthlyRate) /
                (1 - Math.pow(1 + monthlyRate, -months));

        setMonthlyPayment(payment);
    }

    public double getExpectedEndingValue() {
        return expectedEndingValue;
    }
    public double getLeaseFee() {
        return leaseFee;
    }

    @Override public String getType() {
        return "LEASE";
    }
}
