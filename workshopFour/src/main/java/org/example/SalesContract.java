package org.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class SalesContract extends Contract {

    // constants
    private static final double SALES_TAX_RATE     = 0.05;
    private static final double RECORDING_FEE      = 100.0;
    private static final double PROCESS_FEE_LT_10K = 295.0;
    private static final double PROCESS_FEE_GE_10K = 495.0;

    private final ArrayList<AddOn> addOns;

    // Constructors

    // UI flow
    public SalesContract(String date,
                         String customerName,
                         String customerEmail,
                         Vehicle vehicle,
                         ArrayList<AddOn> addOns) {
        super(date, customerName, customerEmail, vehicle);
        this.addOns = addOns == null ? new ArrayList<>() : addOns;
    }

    // CSV-array constructor used by ContractFileManager
    public SalesContract(String[] p) {
        super(
                p[1],          // date
                p[2],          // customer name
                p[3],          // email
                buildVehicle(p)
        );
        this.addOns = parseAddOns(Arrays.copyOfRange(p, 12, p.length));
    }

    // Helper methods

    // Build Vehicle regardless of whether color column is present
    private static Vehicle buildVehicle(String[] p) {
        String col8 = p[8].trim();
        boolean col8IsType;
        try { VehicleType.valueOf(col8.toUpperCase()); col8IsType = true; }
        catch (IllegalArgumentException ex) { col8IsType = false; }

        String color;
        VehicleType type;
        int odoIdx;
        int priceIdx;

        if (col8IsType) {
            // layout without color column
            type      = VehicleType.valueOf(col8.toUpperCase());
            color     = "UNKNOWN";
            odoIdx    = 9;
            priceIdx  = 10;
        } else {
            // layout with color column
            color     = col8;
            type      = VehicleType.valueOf(p[9].trim().toUpperCase());
            odoIdx    = 10;
            priceIdx  = 11;
        }

        return new Vehicle(
                p[4],                               // VIN
                Integer.parseInt(p[5]),             // year
                p[6],                               // make
                p[7],                               // model
                color,                              // color (or UNKNOWN)
                type,                               // vehicle type
                Integer.parseInt(p[odoIdx]),        // odometer
                Double.parseDouble(p[priceIdx])     // price
        );
    }

    // Extract add-ons from trailing columns
    private static ArrayList<AddOn> parseAddOns(String[] extras) {
        ArrayList<AddOn> list = new ArrayList<>();
        for (String col : extras) {
            String token = col.trim();
            if (token.isEmpty()) continue;
            if (token.contains(";") || ADDON_NAMES.contains(token.toUpperCase())) {
                for (String t : token.split(";")) {
                    try { list.add(AddOn.valueOf(t.trim().toUpperCase())); }
                    catch (IllegalArgumentException ignored) { }
                }
                break;              // first matching column is the add-on column
            }
        }
        return list;
    }

    private static final Set<String> ADDON_NAMES =
            Arrays.stream(AddOn.values())
                    .map(Enum::name)
                    .collect(Collectors.toSet());

    //Fee helpers

    public double getSalesTax()     { return getVehicle().getPrice() * SALES_TAX_RATE; }
    public double getRecordingFee() { return RECORDING_FEE; }

    public double getProcessingFee() {
        return getVehicle().getPrice() >= 10_000 ? PROCESS_FEE_GE_10K
                : PROCESS_FEE_LT_10K;
    }

    public ArrayList<AddOn> getAddOns() { return addOns; }

    // Totals

    public double getTotalPrice() {
        double base   = getVehicle().getPrice() + getSalesTax()
                + getRecordingFee() + getProcessingFee();
        double extras = addOns.stream().mapToDouble(a -> a.price).sum();
        return base + extras;
    }

    // Finance

    @Override
    public double calculatePayment() {
        double r  = 0.04 / 12;  // monthly rate
        int    n  = 60;         // 60 months
        double pv = getTotalPrice();
        return (pv * r) / (1 - Math.pow(1 + r, -n));
    }

    @Override
    public String getType() { return "SALE"; }
}
