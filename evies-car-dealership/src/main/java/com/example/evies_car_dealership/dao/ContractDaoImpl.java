package com.example.evies_car_dealership.dao;

import com.example.evies_car_dealership.model.LeaseContract;
import com.example.evies_car_dealership.model.SalesContract;
import com.example.evies_car_dealership.util.DatabaseConnection;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


@Repository
public class ContractDaoImpl implements ContractDao {

    private SalesContract mapSales(ResultSet rs) throws SQLException {
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

    private LeaseContract mapLease(ResultSet rs) throws SQLException {
        return new LeaseContract(
                rs.getInt("CIN"),
                rs.getInt("VIN"),
                rs.getInt("DealershipID"),
                rs.getInt("CustomerID"),
                rs.getDate("LeaseStart").toLocalDate(),
                rs.getDate("LeaseEnd").toLocalDate(),
                rs.getBigDecimal("MonthlyPayment"),
                rs.getInt("MileageLimit"),
                rs.getString("PaymentMethod"));
    }


    @Override
    public void saveSalesContract(SalesContract c) {
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
            ps.setDate      (4, Date.valueOf(c.getContractDate())); // ← fixed
            ps.setBigDecimal(5, c.getSalePrice());
            ps.setBoolean   (6, c.isWarrantyIncluded());
            ps.setString    (7, c.getPaymentMethod());
            ps.executeUpdate();
        } catch (SQLException ex) { ex.printStackTrace(); }
    }

    @Override
    public void saveLeaseContract(LeaseContract contract) {

    }


    /* ─────────── queries ─────────── */

    @Override
    public List<SalesContract> getSalesContractsByDealershipId(int id) {
        List<SalesContract> list = new ArrayList<>();
        String sql = "SELECT * FROM SalesContracts WHERE DealershipID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapSales(rs));
            }
        } catch (SQLException ex) { ex.printStackTrace(); }
        return list;
    }

    @Override
    public List<LeaseContract> getLeaseContractsByDealershipId(int id) {
        List<LeaseContract> list = new ArrayList<>();
        String sql = "SELECT * FROM LeaseContracts WHERE DealershipID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapLease(rs));
            }
        } catch (SQLException ex) { ex.printStackTrace(); }
        return list;
    }
}
