package org.example;

import java.io.FileWriter;
import java.io.IOException;

//for your info, delete before submitting
/**
 * Persists Sales and Lease contracts to a single pipe‑delimited CSV.
 * <p>
 * Column order (SALE lines, 18 columns total):
 * <pre>
 * TYPE | DATE | CUSTOMER | EMAIL | VIN | YEAR | MAKE | MODEL | VEHICLE_TYPE | COLOR |
 * ODOMETER | PRICE | SALES_TAX | RECORDING_FEE | PROCESSING_FEE | TOTAL | FINANCED | MONTHLY_PAYMENT
 * </pre>
 * Column order (LEASE lines, 16 columns total):
 * <pre>
 * TYPE | DATE | CUSTOMER | EMAIL | VIN | YEAR | MAKE | MODEL | VEHICLE_TYPE | COLOR |
 * ODOMETER | PRICE | RESIDUAL_VALUE | LEASE_FEE | TOTAL | MONTHLY_PAYMENT
 * </pre>
 */
public class ContractFileManager {

    private static final String FILE_PATH = "src/main/resources/contracts.csv";

    public void saveContract(Contract c) {
        try (FileWriter fw = new FileWriter(FILE_PATH, true)) {

            if (c instanceof SalesContract sc) {
                fw.write(String.format(
                        "%s|%s|%s|%s|%s|%d|%s|%s|%s|%s|%d|%.2f|%.2f|%.2f|%.2f|%.2f|%s|%.2f%n",
                        sc.getType(),
                        sc.getDate(),
                        sc.getCustomerName(),
                        sc.getCustomerEmail(),
                        v(sc).getVin(),
                        v(sc).getYear(),
                        v(sc).getMake(),
                        v(sc).getModel(),
                        v(sc).getVehicleType(),
                        v(sc).getColor(), // NEW ➜ color now included
                        v(sc).getOdometer(),
                        v(sc).getPrice(),
                        sc.getSalesTaxAmount(),
                        sc.getRecordingFee(),
                        sc.getProcessingFee(),
                        sc.getTotalPrice(),
                        sc.isFinanced() ? "YES" : "NO",
                        sc.getMonthlyPayment()));}
            else if (c instanceof LeaseContract lc) {
                fw.write(String.format(
                        "%s|%s|%s|%s|%s|%d|%s|%s|%s|%s|%d|%.2f|%.2f|%.2f|%.2f|%.2f%n",
                        lc.getType(),
                        lc.getDate(),
                        lc.getCustomerName(),
                        lc.getCustomerEmail(),
                        v(lc).getVin(),
                        v(lc).getYear(),
                        v(lc).getMake(),
                        v(lc).getModel(),
                        v(lc).getVehicleType(),
                        v(lc).getColor(), // NEW ➜ color now included
                        v(lc).getOdometer(),
                        v(lc).getPrice(),
                        lc.getExpectedEndingValue(),
                        lc.getLeaseFee(),
                        lc.getTotalPrice(),
                        lc.getMonthlyPayment()));
            }
        }
        catch (IOException ex) {
            System.out.println("Could not write contract: " + ex.getMessage());
        }
    }

    // Shortcut to keep the formatter lines tidy
    private Vehicle v(Contract c) {
        return c.getVehicleSold();
    }
}
