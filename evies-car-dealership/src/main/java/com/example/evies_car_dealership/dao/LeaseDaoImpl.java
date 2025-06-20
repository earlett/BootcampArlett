package com.example.evies_car_dealership.dao;

import com.example.evies_car_dealership.model.LeaseContract;
import com.example.evies_car_dealership.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LeaseDaoImpl implements LeaseDao {
    @Override
    public void save(LeaseContract c) {
        String sql = "INSERT INTO LeaseContract (ContractDate, CustomerName, CustomerEmail, Vin, MonthlyPayment) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, c.getContractDate());
            stmt.setString(2, c.getCustomerName());
            stmt.setString(3, c.getCustomerEmail());
            stmt.setString(4, c.getVin());
            stmt.setDouble(5, c.calculateMonthlyPayment());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public List<LeaseContract> getAll() {
        List<LeaseContract> list = new ArrayList<>();
        String sql = "SELECT * FROM LeaseContract";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                LeaseContract c = new LeaseContract(
                        rs.getString("ContractDate"),
                        rs.getString("CustomerName"),
                        rs.getString("CustomerEmail"),
                        rs.getString("Vin"),
                        rs.getDouble("MonthlyPayment")
                );
                list.add(c);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }
}