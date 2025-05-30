package core;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Receipt {
    private Order order;

    public Receipt(Order order) {
        this.order = order;
    }

    public void saveReceipt() {
        // Generate timestamp for the filename
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));
        String filename = "receipts/" + timestamp + ".txt";

        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("RECEIPT - " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss")) + "\n");
            writer.write(order.getSummary()); // Uses the `Order` class's summary method
            writer.write("\nTotal Cost: $" + order.calculateTotal());
            writer.write("\nThank you for ordering from DELI-cious!\n");
        } catch (IOException e) {
            System.out.println("Error saving receipt: " + e.getMessage());
        }
    }
}
