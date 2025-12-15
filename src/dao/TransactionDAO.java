package dao;

import java.sql.*;
import java.util.ArrayList;
import database.Connect;
import model.Transaction;

public class TransactionDAO {
    private Connection con = Connect.getInstance().getConnection();

    // Helper untuk mapping ResultSet ke Object Transaction
    private Transaction mapTransaction(ResultSet rs) throws SQLException {
        // Handle Nullable Integers
        int rID = rs.getInt("receptionist_id");
        Integer receptionistID = rs.wasNull() ? null : rID;
        
        int sID = rs.getInt("laundry_staff_id");
        Integer staffID = rs.wasNull() ? null : sID;

        return new Transaction(
            rs.getInt("transaction_id"),
            rs.getInt("service_id"),
            rs.getInt("customer_id"),
            receptionistID,
            staffID,
            rs.getDate("transaction_date"),
            rs.getString("transaction_status"),
            rs.getDouble("total_weight"),
            rs.getString("transaction_notes"),
            rs.getString("service_name") // Ambil dari hasil JOIN
        );
    }

    public void insertTransaction(int serviceID, int customerID, double weight, String notes) {
        try {
            String query = "INSERT INTO transactions (service_id, customer_id, transaction_date, transaction_status, total_weight, transaction_notes) VALUES (?, ?, CURDATE(), 'Pending', ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, serviceID);
            ps.setInt(2, customerID);
            ps.setDouble(3, weight);
            ps.setString(4, notes);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Helper method untuk eksekusi query SELECT
    private ArrayList<Transaction> fetchTransactions(String query, Object... params) {
        ArrayList<Transaction> list = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement(query);
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(mapTransaction(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // --- Specific Queries ---

    public ArrayList<Transaction> getAllTransactions() {
        String query = "SELECT t.*, s.service_name FROM transactions t JOIN services s ON t.service_id = s.service_id";
        return fetchTransactions(query);
    }

    public ArrayList<Transaction> getTransactionsByCustomer(int customerID) {
        String query = "SELECT t.*, s.service_name FROM transactions t JOIN services s ON t.service_id = s.service_id WHERE customer_id = ? ORDER BY transaction_date DESC";
        return fetchTransactions(query, customerID);
    }

    public ArrayList<Transaction> getTransactionsByStatus(String status) {
        String query = "SELECT t.*, s.service_name FROM transactions t JOIN services s ON t.service_id = s.service_id WHERE transaction_status = ?";
        return fetchTransactions(query, status);
    }

    public ArrayList<Transaction> getAssignedTransactions(int staffID) {
        String query = "SELECT t.*, s.service_name FROM transactions t JOIN services s ON t.service_id = s.service_id WHERE laundry_staff_id = ? AND transaction_status = 'In Progress'";
        return fetchTransactions(query, staffID);
    }

    // --- Update Queries ---

    public void assignStaff(int transactionID, int receptionistID, int staffID) {
        try {
            String query = "UPDATE transactions SET receptionist_id = ?, laundry_staff_id = ?, transaction_status = 'In Progress' WHERE transaction_id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, receptionistID);
            ps.setInt(2, staffID);
            ps.setInt(3, transactionID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateStatus(int transactionID, String status) {
        try {
            String query = "UPDATE transactions SET transaction_status = ? WHERE transaction_id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, status);
            ps.setInt(2, transactionID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}