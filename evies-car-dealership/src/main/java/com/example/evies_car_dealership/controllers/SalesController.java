package com.example.evies_car_dealership.controllers;

import com.example.evies_car_dealership.dao.SalesDao;
import com.example.evies_car_dealership.model.SalesContract;
import com.example.evies_car_dealership.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalesDaoImpl implements SalesDao {
    @Override
    public void save(SalesContract c) {
        String sql = "INSERT INTO SalesContract (ContractDate, CustomerName, CustomerEmail, Vin, TotalPrice) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, c.getContractDate());
            stmt.setString(2, c.getCustomerName());
            stmt.setString(3, c.getCustomerEmail());
            stmt.setString(4, c.getVin());
            stmt.setDouble(5, c.calculateTotal());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<SalesContract> getAll() {
        List<SalesContract> list = new ArrayList<>();
        String sql = "SELECT * FROM SalesContract";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                SalesContract c = new SalesContract(
                        rs.getString("ContractDate"),
                        rs.getString("CustomerName"),
                        rs.getString("CustomerEmail"),
                        rs.getString("Vin"),
                        rs.getDouble("TotalPrice")
                );
                list.add(c);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }
}
