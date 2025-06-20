package com.example.evies_car_dealership.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/eviecardealership";
    private static String USER;
    private static String PASS;

    // Called once during app startup
    public static void configure(String user, String pass) {
        USER = user;
        PASS = pass;
    }

    // Call this to get a connection
    public static Connection getConnection() throws SQLException {
        if (USER == null || PASS == null) {
            throw new IllegalStateException("Database credentials not configured. Call DatabaseConnection.configure(user, pass) first.");
        }
        return DriverManager.getConnection(URL, USER, PASS);
    }
}

