package com.example.evies_car_dealership.dao;

import com.example.evies_car_dealership.model.LeaseContract;
import com.example.evies_car_dealership.util.DatabaseConnection;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * JDBC implementation of {@link LeaseDao}.
 */
@Repository
public class LeaseDaoImpl implements LeaseDao {

    /* ─────────── INSERT ─────────── */
    @Override
    public void save(LeaseContract c) {
        String sql = """
            INSERT INTO LeaseContracts
                   (VIN, DealershipID, CustomerID, LeaseStart,
                    LeaseEnd, MonthlyPayment, MileageLimit, PaymentMethod)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            """;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt       (1, c.getVin());
            ps.setInt       (2, c.getDealershipId());
            ps.setInt       (3, c.getCustomerId());
            ps.setDate      (4, Date.valueOf(c.getLeaseStart()));
            ps.setDate      (5, Date.valueOf(c.getLeaseEnd()));
            ps.setBigDecimal(6, c.getMonthlyPayment());
            ps.setInt       (7, c.getMileageLimit());
            ps.setString    (8, c.getPaymentMethod());

            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /* ─────────── SELECT ALL ─────────── */
    @Override
    public List<LeaseContract> getAll() {
        List<LeaseContract> list = new ArrayList<>();
        String sql = "SELECT * FROM LeaseContracts";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                LeaseContract c = new LeaseContract(
                        rs.getInt("CIN"),
                        rs.getInt("VIN"),
                        rs.getInt("DealershipID"),
                        rs.getInt("CustomerID"),
                        rs.getDate("LeaseStart").toLocalDate(),
                        rs.getDate("LeaseEnd").toLocalDate(),
                        rs.getBigDecimal("MonthlyPayment"),
                        rs.getInt("MileageLimit"),
                        rs.getString("PaymentMethod")
                );
                list.add(c);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }
}
