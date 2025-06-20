package org.example.dao;

import org.example.models.Vehicle;
import org.example.models.VehicleType;
import org.example.util.DatabaseConnection;

import java.sql.*;
import java.util.*;
import java.math.BigDecimal;

public class VehicleDaoImpl implements VehicleDao {

    /* ========== helpers ========== */

    private Vehicle map(ResultSet rs) throws SQLException {
        return new Vehicle(
                String.valueOf(rs.getInt("VIN")),
                rs.getInt("YearMade"),
                rs.getString("Make"),
                rs.getString("Model"),
                rs.getString("Color"),
                VehicleType.valueOf(rs.getString("Type").toUpperCase()),
                rs.getInt("Mileage"),
                rs.getDouble("Price")
        );
    }

    private List<Vehicle> query(String sql, SqlSetter setter) {
        List<Vehicle> list = new ArrayList<>();
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            setter.set(ps);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }

    @FunctionalInterface
    private interface SqlSetter { void set(PreparedStatement ps) throws SQLException; }

    /* ========== DAO methods ========== */

    @Override
    public List<Vehicle> getAllVehicles() {
        return query("SELECT * FROM Vehicle WHERE HasSold = 0", ps -> {});
    }

    @Override
    public Optional<Vehicle> getVehicleByVin(int vin) {
        var list = query("SELECT * FROM Vehicle WHERE VIN = ?", ps -> ps.setInt(1, vin));
        return list.stream().findFirst();
    }

    @Override
    public List<Vehicle> searchByPriceRange(BigDecimal min, BigDecimal max) {
        return query("SELECT * FROM Vehicle WHERE Price BETWEEN ? AND ? AND HasSold = 0", ps -> {
            ps.setBigDecimal(1, min);
            ps.setBigDecimal(2, max);
        });
    }

    @Override
    public List<Vehicle> searchByMakeModel(String make, String model) {
        return query("SELECT * FROM Vehicle WHERE Make = ? AND Model = ? AND HasSold = 0", ps -> {
            ps.setString(1, make);
            ps.setString(2, model);
        });
    }

    @Override
    public List<Vehicle> searchByYearRange(int start, int end) {
        return query("SELECT * FROM Vehicle WHERE YearMade BETWEEN ? AND ? AND HasSold = 0", ps -> {
            ps.setInt(1, start);
            ps.setInt(2, end);
        });
    }

    @Override
    public List<Vehicle> searchByColor(String color) {
        return query("SELECT * FROM Vehicle WHERE Color = ? AND HasSold = 0", ps -> ps.setString(1, color));
    }

    @Override
    public List<Vehicle> searchByMileageRange(int min, int max) {
        return query("SELECT * FROM Vehicle WHERE Mileage BETWEEN ? AND ? AND HasSold = 0", ps -> {
            ps.setInt(1, min);
            ps.setInt(2, max);
        });
    }

    @Override
    public List<Vehicle> searchByType(String type) {
        return query("SELECT * FROM Vehicle WHERE Type = ? AND HasSold = 0", ps -> ps.setString(1, type));
    }

    @Override
    public void addVehicle(Vehicle v) {
        String sql = """
            INSERT INTO Vehicle
            (VIN, Make, Model, Price, YearMade, HasSold, Color, Mileage, Type)
            VALUES (?, ?, ?, ?, ?, 0, ?, ?, ?)
            """;
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt   (1, Integer.parseInt(v.getVin()));
            ps.setString(2, v.getMake());
            ps.setString(3, v.getModel());
            ps.setDouble(4, v.getPrice());
            ps.setInt   (5, v.getYear());
            ps.setString(6, v.getColor());
            ps.setInt   (7, v.getOdometer());
            ps.setString(8, v.getType().name());
            ps.executeUpdate();
        } catch (SQLException ex) { ex.printStackTrace(); }
    }

    /* ====== updated “soft-delete” ====== */
    @Override
    public void removeVehicle(int vin) {

        String deleteInventory = "DELETE FROM Inventory WHERE VIN = ?";
        String markSold        = "UPDATE Vehicle SET HasSold = 1 WHERE VIN = ?";

        try (Connection c = DatabaseConnection.getConnection()) {

            // 1. remove from Inventory
            try (PreparedStatement ps = c.prepareStatement(deleteInventory)) {
                ps.setInt(1, vin);
                ps.executeUpdate();
            }

            // 2. flag vehicle as sold
            try (PreparedStatement ps = c.prepareStatement(markSold)) {
                ps.setInt(1, vin);
                ps.executeUpdate();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
