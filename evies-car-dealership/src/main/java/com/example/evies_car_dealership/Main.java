package com.example.evies_car_dealership;

import com.example.evies_car_dealership.ui.UserInterface;
import com.example.evies_car_dealership.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
	public static void main(String[] args) {
		if (args.length < 2) {
			System.err.println("Usage: java Main <username> <password>");
			return;
		}

		String user = args[0];
		String pass = args[1];

		// Configure static DB credentials
		DatabaseConnection.configure(user, pass);

		// Test connection
		try (Connection conn = DatabaseConnection.getConnection()) {
			System.out.println("Connected to database: " + conn.getCatalog());
		} catch (SQLException ex) {
			System.err.println("Database connection failed: " + ex.getMessage());
			ex.printStackTrace();
			return;
		}

		// Launch UI
		UserInterface ui = new UserInterface();
		ui.display();
	}
}
