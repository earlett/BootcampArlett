package org.example.dao;

import org.example.models.*;
import org.example.util.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * JDBC implementation of {@link ContractDao} that persists and retrieves
 * {@link SalesContract} and {@link LeaseContract} records.
 * <p>
 * NOTE: DealershipID is still hard-coded to {@code 1}.
 * CustomerID is now resolved via {@link CustomerDao#getOrCreate(Customer)}.
 */
public class ContractDaoImpl implements ContractDao {

    private final CustomerDao customerDao = new CustomerDaoImpl();

    /* ─────────────────────── INSERTS ─────────────────────── */

    @Override
    public void saveSalesContract(SalesContract sc) {

        int customerId = customerDao.getOrCreate(
                new Customer(sc.getCustomerName(), sc.getCustomerEmail()));

        String sql = """
            INSERT INTO SalesContracts
            (VIN, DealershipID, CustomerID, SaleDate, SalePrice,
             WarrantyIncluded, PaymentMethod)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt   (1, Integer.parseInt(sc.getVehicle().getVin()));
            ps.setInt   (2, 1);                       // TODO real dealership
            ps.setInt   (3, customerId);
            ps.setDate  (4, Date.valueOf(LocalDate.parse(sc.getContractDate())));
            ps.setDouble(5, sc.getTotalPrice());
            ps.setBoolean(6, false);                  // warranty not tracked yet
            ps.setString (7, "Credit");
            ps.executeUpdate();

        } catch (SQLException ex) { ex.printStackTrace(); }
    }

    @Override
    public void saveLeaseContract(LeaseContract lc) {

        int customerId = customerDao.getOrCreate(
                new Customer(lc.getCustomerName(), lc.getCustomerEmail()));

        String sql = """
            INSERT INTO Lease_Contracts
            (VIN, DealershipID, CustomerID, LeaseStart, LeaseEnd,
             MonthlyPayment, MileageLimit, PaymentMethod)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            """;

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, Integer.parseInt(lc.getVehicle().getVin()));
            ps.setInt(2, 1);               // TODO real dealership
            ps.setInt(3, customerId);

            LocalDate start = LocalDate.parse(lc.getContractDate());
            LocalDate end   = start.plusYears(3);

            ps.setDate  (4, Date.valueOf(start));
            ps.setDate  (5, Date.valueOf(end));
            ps.setDouble(6, lc.calculatePayment());
            ps.setInt   (7, lc.getVehicle().getOdometer() + 36_000);
            ps.setString(8, "Credit");
            ps.executeUpdate();

        } catch (SQLException ex) { ex.printStackTrace(); }
    }

    /* ─────────────────────── QUERIES ─────────────────────── */

    private static final String BASE_SELECT = """
        SELECT sc.*, cu.FullName, cu.Email,
               v.VIN, v.YearMade, v.Make, v.Model,
               v.Color, v.Type, v.Mileage, v.Price
        FROM %s sc
        JOIN Customers cu ON sc.CustomerID = cu.CustomerID
        JOIN Vehicle   v  ON sc.VIN        = v.VIN
        WHERE sc.DealershipID = ?
        """;

    @Override
    public List<SalesContract> getSalesContractsByDealershipId(int id) {
        List<SalesContract> list = new ArrayList<>();
        String sql = BASE_SELECT.formatted("SalesContracts");

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

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
        String sql = BASE_SELECT.formatted("Lease_Contracts");

        try (Connection c = DatabaseConnection.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(mapLease(rs));
            }
        } catch (SQLException ex) { ex.printStackTrace(); }
        return list;
    }

    /* ─────────────────────── Helpers ─────────────────────── */

    private Vehicle mapVehicle(ResultSet rs) throws SQLException {
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

    private SalesContract mapSales(ResultSet rs) throws SQLException {
        Vehicle v = mapVehicle(rs);
        return new SalesContract(
                rs.getString("SaleDate"),
                rs.getString("FullName"),
                rs.getString("Email"),
                v,
                new ArrayList<>()      // add-ons not stored yet
        );
    }

    private LeaseContract mapLease(ResultSet rs) throws SQLException {
        Vehicle v = mapVehicle(rs);
        return new LeaseContract(
                rs.getString("LeaseStart"),   // using start date as contract date
                rs.getString("FullName"),
                rs.getString("Email"),
                v
        );
    }
}
