package com.example.evies_car_dealership.dao;

import com.example.evies_car_dealership.model.Vehicle;
import com.example.evies_car_dealership.model.VehicleType;
import com.example.evies_car_dealership.util.DatabaseConnection;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class VehicleDaoImpl implements VehicleDao {

    /* ───────── private mapper ───────── */
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

    @FunctionalInterface
    private interface SqlSetter { void set(PreparedStatement ps) throws SQLException; }

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

    /* ───────── CRUD ───────── */

    @Override
    public void addVehicle(Vehicle v) {
        String sql = """
            INSERT INTO Vehicle (VIN, Make, Model, Price, YearMade, HasSold, Color, Mileage, Type)
            VALUES (?, ?, ?, ?, ?, 0, ?, ?, ?)
            """;
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt   (1, Integer.parseInt(v.getVin()));   // ← fixed
            ps.setString(2, v.getMake());
            ps.setString(3, v.getModel());
            ps.setDouble(4, v.getPrice());
            ps.setInt   (5, v.getYear());
            ps.setString(6, v.getColor());
            ps.setInt   (7, v.getOdometer());
            ps.setString(8, v.getType().name());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void update(Vehicle v) {
        String sql = """
            UPDATE Vehicle
               SET Make = ?, Model = ?, Price = ?, YearMade = ?,
                   Color = ?, Mileage = ?, Type = ?
             WHERE VIN = ?
            """;
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, v.getMake());
            ps.setString(2, v.getModel());
            ps.setDouble(3, v.getPrice());
            ps.setInt   (4, v.getYear());
            ps.setString(5, v.getColor());
            ps.setInt   (6, v.getOdometer());
            ps.setString(7, v.getType().name());
            ps.setInt   (8, Integer.parseInt(v.getVin()));   // ← fixed
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void removeVehicle(int vin) {
        String sql = "UPDATE Vehicle SET HasSold = 1 WHERE VIN = ?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, vin);
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<Vehicle> getAllVehicles() {
        return query("SELECT * FROM Vehicle WHERE HasSold = 0", ps -> {});
    }

    @Override
    public Optional<Vehicle> getVehicleByVin(int vin) {
        var list = query("SELECT * FROM Vehicle WHERE VIN = ? AND HasSold = 0",
                ps -> ps.setInt(1, vin));
        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    /* ───────── search helpers ───────── */

    @Override
    public List<Vehicle> searchByPriceRange(BigDecimal min, BigDecimal max) {
        return query("SELECT * FROM Vehicle WHERE Price BETWEEN ? AND ? AND HasSold = 0",
                ps -> {
                    ps.setBigDecimal(1, min);
                    ps.setBigDecimal(2, max);
                });
    }

    @Override
    public List<Vehicle> searchByMakeModel(String make, String model) {
        return query("SELECT * FROM Vehicle WHERE Make = ? AND Model = ? AND HasSold = 0",
                ps -> {
                    ps.setString(1, make);
                    ps.setString(2, model);
                });
    }

    @Override
    public List<Vehicle> searchByYearRange(int startYear, int endYear) {
        return query("SELECT * FROM Vehicle WHERE YearMade BETWEEN ? AND ? AND HasSold = 0",
                ps -> {
                    ps.setInt(1, startYear);
                    ps.setInt(2, endYear);
                });
    }

    @Override
    public List<Vehicle> searchByColor(String color) {
        return query("SELECT * FROM Vehicle WHERE Color = ? AND HasSold = 0",
                ps -> ps.setString(1, color));
    }

    @Override
    public List<Vehicle> searchByMileageRange(int minMileage, int maxMileage) {
        return query("SELECT * FROM Vehicle WHERE Mileage BETWEEN ? AND ? AND HasSold = 0",
                ps -> {
                    ps.setInt(1, minMileage);
                    ps.setInt(2, maxMileage);
                });
    }

    @Override
    public List<Vehicle> searchByType(String type) {
        return query("SELECT * FROM Vehicle WHERE Type = ? AND HasSold = 0",
                ps -> ps.setString(1, type));
    }
}
