package org.example.dao;

import org.example.models.Customer;
import org.example.util.DatabaseConnection;

import java.sql.*;
import java.util.Optional;

public class CustomerDaoImpl implements CustomerDao {

    @Override
    public Optional<Customer> findByEmail(String email) {
        String sql = "SELECT * FROM Customers WHERE Email = ?";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(map(rs));
                }
            }
        } catch (SQLException ex) { ex.printStackTrace(); }
        return Optional.empty();
    }

    @Override
    public int insert(Customer cu) {
        String sql = "INSERT INTO Customers (FullName, Email, Phone, Address) VALUES (?, ?, ?, ?)";
        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, cu.getFullName());
            ps.setString(2, cu.getEmail());
            ps.setString(3, cu.getPhone());
            ps.setString(4, cu.getAddress());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
        } catch (SQLException ex) { ex.printStackTrace(); }
        return -1;
    }

    /* helper */
    private Customer map(ResultSet rs) throws SQLException {
        return new Customer(
                rs.getInt   ("CustomerID"),
                rs.getString("FullName"),
                rs.getString("Email"),
                rs.getString("Phone"),
                rs.getString("Address")
        );
    }
}
