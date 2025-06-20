package com.example.evies_car_dealership.dao;

import com.example.evies_car_dealership.model.Customer;
import com.example.evies_car_dealership.util.DatabaseConnection;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CustomerDaoImpl implements CustomerDao {

    /* ─────────────────────────── private mapper ─────────────────────────── */
    private Customer map(ResultSet rs) throws SQLException {
        return new Customer(
                rs.getInt("CustomerID"),
                rs.getString("FullName"),
                rs.getString("Email"),
                rs.getString("Phone"),
                rs.getString("Address"));
    }

    /* ─────────────────────────── interface methods ──────────────────────── */

    /** SELECT * FROM Customer WHERE Email = ? */
    @Override
    public Optional<Customer> getByEmail(String email) {
        String sql = "SELECT * FROM Customer WHERE Email = ?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(map(rs)) : Optional.empty();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return Optional.empty();
        }
    }

    /** INSERT and return generated ID */
    @Override
    public int getOrCreate(Customer c) {
        // reuse getByEmail first
        Optional<Customer> existing = getByEmail(c.getEmail());
        if (existing.isPresent()) return existing.get().getId();

        String sql = "INSERT INTO Customer (FullName, Email, Phone, Address) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, c.getFullName());
            ps.setString(2, c.getEmail());
            ps.setString(3, c.getPhone());
            ps.setString(4, c.getAddress());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                return keys.next() ? keys.getInt(1) : -1;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    /** UPDATE by Email */
    @Override
    public boolean update(Customer c) {
        String sql = "UPDATE Customer SET FullName = ?, Phone = ?, Address = ? WHERE Email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getFullName());
            ps.setString(2, c.getPhone());
            ps.setString(3, c.getAddress());
            ps.setString(4, c.getEmail());
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /** DELETE by Email */
    @Override
    public boolean deleteByEmail(String email) {
        String sql = "DELETE FROM Customer WHERE Email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /** SELECT * FROM Customer */
    @Override
    public List<Customer> getAll() {
        List<Customer> list = new ArrayList<>();
        String sql = "SELECT * FROM Customer";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) list.add(map(rs));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }
}
