package org.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL  =
            "jdbc:mysql://localhost:3306/eviecardealership?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USER = "root";          // or “dealership”
    private static final String PASS = "Gorijas90!3";

    /** Grab a new connection (caller should close it). */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
