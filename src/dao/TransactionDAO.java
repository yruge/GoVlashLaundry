package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Transaction;
import util.Connect;

public class TransactionDAO {

    public void createTransaction(Transaction t) {
        String query = "INSERT INTO transaction (serviceID, customerID, transactionDate, transactionStatus, totalWeight, transactionNotes) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = Connect.getInstance().prepareStatement(query);
            ps.setInt(1, t.getServiceID());
            ps.setInt(2, t.getCustomerID());
            ps.setDate(3, t.getTransactionDate());
            ps.setString(4, t.getTransactionStatus());
            ps.setDouble(5, t.getTotalWeight());
            ps.setString(6, t.getTransactionNotes());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Transaction> getAllTransactions() {
        List<Transaction> list = new ArrayList<>();
        String query = "SELECT * FROM transaction ORDER BY transactionDate DESC";
        try {
            PreparedStatement ps = Connect.getInstance().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapResultSetToTransaction(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Transaction> getTransactionsByStatus(String status) {
        List<Transaction> list = new ArrayList<>();
        String query = "SELECT * FROM transaction WHERE transactionStatus = ? ORDER BY transactionDate DESC";
        try {
            PreparedStatement ps = Connect.getInstance().prepareStatement(query);
            ps.setString(1, status);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapResultSetToTransaction(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void assignStaff(int transactionID, int receptionistID, int laundryStaffID) {
        String query = "UPDATE transaction SET receptionistID = ?, laundryStaffID = ? WHERE transactionID = ?";
        try {
            PreparedStatement ps = Connect.getInstance().prepareStatement(query);
            ps.setInt(1, receptionistID);
            ps.setInt(2, laundryStaffID);
            ps.setInt(3, transactionID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateStatus(int transactionID, String status) {
        String query = "UPDATE transaction SET transactionStatus = ? WHERE transactionID = ?";
        try {
            PreparedStatement ps = Connect.getInstance().prepareStatement(query);
            ps.setString(1, status);
            ps.setInt(2, transactionID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Transaction> getByLaundryStaff(int laundryStaffID) {
        List<Transaction> list = new ArrayList<>();
        String query = "SELECT * FROM transaction WHERE laundryStaffID = ? ORDER BY transactionDate DESC";
        try {
            PreparedStatement ps = Connect.getInstance().prepareStatement(query);
            ps.setInt(1, laundryStaffID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapResultSetToTransaction(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Transaction> getByCustomer(int customerID) {
        List<Transaction> list = new ArrayList<>();
        String query = "SELECT * FROM transaction WHERE customerID = ? ORDER BY transactionDate DESC";
        try {
            PreparedStatement ps = Connect.getInstance().prepareStatement(query);
            ps.setInt(1, customerID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapResultSetToTransaction(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    private Transaction mapResultSetToTransaction(ResultSet rs) throws SQLException {
        return new Transaction(
            rs.getInt("transactionID"),
            rs.getInt("serviceID"),
            rs.getInt("customerID"),
            rs.getInt("receptionistID"),
            rs.getInt("laundryStaffID"),
            rs.getDate("transactionDate"),
            rs.getString("transactionStatus"),
            rs.getDouble("totalWeight"),
            rs.getString("transactionNotes")
        );
    }
}