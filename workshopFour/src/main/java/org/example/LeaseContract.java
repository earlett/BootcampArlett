package org.example;

public class LeaseContract extends Contract {

    private static final double LEASE_FEE_RATE = 0.07;  // used by UI constructor
    private static final double RESIDUAL_RATE  = 0.50;  // used by UI constructor

    private final double leaseFee;
    private final double expectedEndingValue;

    /* ---------- constructor used by the UI flow ---------- */
    public LeaseContract(String date,
                         String customerName,
                         String customerEmail,
                         Vehicle vehicle) {
        super(date, customerName, customerEmail, vehicle);
        this.leaseFee            = vehicle.getPrice() * LEASE_FEE_RATE;
        this.expectedEndingValue = vehicle.getPrice() * RESIDUAL_RATE;
    }

    // constructor used when loading from CSV

    public LeaseContract(String[] p) {
        super(
                p[1],                       // contract date
                p[2],                       // customer name
                p[3],                       // email
                new Vehicle(                // reconstruct Vehicle
                        p[4],                               // VIN
                        Integer.parseInt(p[5]),             // year
                        p[6],                               // make
                        p[7],                               // model
                        "UNKNOWN",                          // color absent
                        VehicleType.valueOf(p[8]),          // type
                        Integer.parseInt(p[9]),             // odometer
                        Double.parseDouble(p[10])           // price
                )
        );
        this.expectedEndingValue = Double.parseDouble(p[11]);
        this.leaseFee = Double.parseDouble(p[12]);
    }

    // getters
    public double getLeaseFee()            { return leaseFee; }
    public double getExpectedEndingValue() { return expectedEndingValue; }
    public double getTotalPrice()          { return leaseFee; }

    // finance math
    @Override
    public double calculatePayment() {
        double depreciation = (getVehicle().getPrice() - expectedEndingValue) / 36.0;
        return depreciation + (leaseFee / 36.0);
    }

    @Override
    public String getType() { return "LEASE"; }
}
