package org.example;

public class SalesContract extends Contract {
    private static final double SALES_TAX_RATE = 0.05;
    private static final double RECORDING_FEE  = 100.0;

    private double salesTaxAmount;
    private double recordingFee;
    private double processingFee;
    private boolean isFinanced;

    public SalesContract(String date, String customerName, String customerEmail, Vehicle vehicleSold, boolean isFinanced) {
        super(date, customerName, customerEmail, vehicleSold);
        this.isFinanced = isFinanced;
        calculatePayment();
    }

    @Override
    public void calculatePayment() {
        double price = getVehicleSold().getPrice();
        salesTaxAmount = price * SALES_TAX_RATE;
        recordingFee   = RECORDING_FEE;
        processingFee  = price < 10_000 ? 295.0 : 495.0;

        double total = price + salesTaxAmount + recordingFee + processingFee;
        setTotalPrice(total);

        if (!isFinanced) {
            setMonthlyPayment(0.0);
            return;
        }

        //finance options
        double annualRate = price >= 10_000 ? 0.0425 : 0.0525;
        int    months     = price >= 10_000 ? 48       : 24;

        double monthlyRate = annualRate / 12;
        double payment = (price * monthlyRate) /
                (1 - Math.pow(1 + monthlyRate, -months));

        setMonthlyPayment(payment);
    }

    // extra getters a ContractFileManager will need
    public double getSalesTaxAmount() {
        return salesTaxAmount;
    }
    public double getRecordingFee() {
        return recordingFee;
    }
    public double getProcessingFee() {
        return processingFee;
    }
    public boolean isFinanced() {
        return isFinanced;
    }

    @Override public String getType() {
        return "SALE";
    }
}
