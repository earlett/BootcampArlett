package com.example.evies_car_dealership.dao;

import com.example.evies_car_dealership.model.SalesContract;
import com.example.evies_car_dealership.util.DatabaseConnection;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SalesDaoImpl implements SalesDao {

    @Override
    public void save(SalesContract c) {
        String sql = """
            INSERT INTO SalesContracts
              (VIN, DealershipID, CustomerID, SaleDate,
               SalePrice, WarrantyIncluded, PaymentMethod)
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt       (1, c.getVin());
            ps.setInt       (2, c.getDealershipId());
            ps.setInt       (3, c.getCustomerId());
            ps.setDate      (4, Date.valueOf(c.getContractDate()));
            ps.setBigDecimal(5, c.getSalePrice());
            ps.setBoolean   (6, c.isWarrantyIncluded());
            ps.setString    (7, c.getPaymentMethod());
            ps.executeUpdate();
        } catch (SQLException ex) { ex.printStackTrace(); }
    }

    /* ---------- SELECT helpers ---------- */
    private SalesContract map(ResultSet rs) throws SQLException {
        return new SalesContract(
                rs.getInt("SIN"),
                rs.getInt("VIN"),
                rs.getInt("DealershipID"),
                rs.getInt("CustomerID"),
                rs.getDate("SaleDate").toLocalDate(),
                rs.getBigDecimal("SalePrice"),
                rs.getBoolean("WarrantyIncluded"),
                rs.getString("PaymentMethod"));
    }

    @Override
    public List<SalesContract> getAll() {
        return query("SELECT * FROM SalesContracts", ps -> {});
    }

    @Override
    public List<SalesContract> getByDealership(int id) {
        return query("SELECT * FROM SalesContracts WHERE DealershipID = ?",
                ps -> ps.setInt(1, id));
    }

    /* ---------- reusable query helper ---------- */
    private interface SqlSetter { void set(PreparedStatement ps) throws SQLException; }

    private List<SalesContract> query(String sql, SqlSetter setter) {
        List<SalesContract> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            setter.set(ps);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(map(rs));
            }
        } catch (SQLException ex) { ex.printStackTrace(); }
        return list;
    }
}
