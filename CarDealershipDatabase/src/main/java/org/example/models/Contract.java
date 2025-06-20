package org.example.models;

public abstract class Contract {
    protected String contractDate;
    protected String customerName;
    protected String customerEmail;
    protected Vehicle vehicle;

    public Contract(String contractDate,
                    String customerName,
                    String customerEmail,
                    Vehicle vehicle) {
        this.contractDate = contractDate;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.vehicle = vehicle;
    }

    public String getContractDate() {
        return contractDate;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public abstract double calculatePayment();
    public abstract String getType();

    @Override
    public String toString() {
        return getType() + " Contract — " + vehicle.getVin()
             + " $" + String.format("%.2f", calculatePayment());
    }
}
