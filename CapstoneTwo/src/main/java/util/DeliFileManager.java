package util;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DeliFileManager {
    private static final String RECEIPT_FOLDER = "receipts";

    public static void saveToFile(String filename, String content) {
        createReceiptsFolder();

        try (FileWriter writer = new FileWriter(RECEIPT_FOLDER + "/" + filename)) {
            writer.write(content);
            System.out.println("Receipt saved: " + filename);
        } catch (IOException e) {
            System.out.println("Error saving file: " + e.getMessage());
        }
    }

    private static void createReceiptsFolder() {
        Path path = Paths.get(RECEIPT_FOLDER);
        if (!Files.exists(path)) {
            try {
                Files.createDirectory(path);
            } catch (IOException e) {
                System.out.println("Error creating receipts folder: " + e.getMessage());
            }
        }
    }
}
