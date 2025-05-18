package org.example;

public abstract class Contract {
    private String date;          // YYYYMMDD
    private String customerName;
    private String customerEmail;
    private Vehicle vehicleSold;
    private double totalPrice;
    private double monthlyPayment;

    public Contract(String date, String customerName, String customerEmail, Vehicle vehicleSold) {
        this.date = date;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.vehicleSold = vehicleSold;
    }

    public String getDate() { return date; }
    public String getCustomerName() { return customerName; }
    public String getCustomerEmail() { return customerEmail; }
    public Vehicle getVehicleSold() { return vehicleSold; }

    public double getTotalPrice()      { return totalPrice; }
    protected void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }

    public double getMonthlyPayment()  { return monthlyPayment; }
    protected void setMonthlyPayment(double monthlyPayment) { this.monthlyPayment = monthlyPayment; }

    public abstract void calculatePayment();     // sets both totals
    public abstract String getType();            // "SALE" or "LEASE"
}
