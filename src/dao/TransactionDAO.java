package dao;

import model.Transaction;
import util.Connect;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransactionDAO {
    private Connect connect = Connect.getConnection();

    public boolean create(Transaction t) {
        String sql = "INSERT INTO transactions (transaction_id, service_id, customer_id, receptionist_id, laundry_staff_id, transaction_date, status, total_weight, notes) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connect.getCon().prepareStatement(sql)) {
            ps.setString(1, t.getTransactionId());
            ps.setString(2, t.getServiceId());
            ps.setString(3, t.getCustomerId());
            ps.setString(4, t.getReceptionistId());
            ps.setString(5, t.getLaundryStaffId());
            ps.setTimestamp(6, new Timestamp(new Date().getTime()));
            ps.setString(7, t.getStatus());
            ps.setDouble(8, t.getTotalWeight());
            ps.setString(9, t.getNotes());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public List<Transaction> findAll() {
        List<Transaction> res = new ArrayList<>();
        String sql = "SELECT * FROM transactions ORDER BY transaction_date DESC";
        try (PreparedStatement ps = connect.getCon().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Transaction t = new Transaction();
                t.setTransactionId(rs.getString("transaction_id"));
                t.setServiceId(rs.getString("service_id"));
                t.setCustomerId(rs.getString("customer_id"));
                t.setReceptionistId(rs.getString("receptionist_id"));
                t.setLaundryStaffId(rs.getString("laundry_staff_id"));
                t.setTransactionDate(rs.getTimestamp("transaction_date"));
                t.setStatus(rs.getString("status"));
                t.setTotalWeight(rs.getDouble("total_weight"));
                t.setNotes(rs.getString("notes"));
                res.add(t);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return res;
    }

    public List<Transaction> findByStatus(String status) {
        List<Transaction> res = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE status = ? ORDER BY transaction_date DESC";
        try (PreparedStatement ps = connect.getCon().prepareStatement(sql)) {
            ps.setString(1, status);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Transaction t = new Transaction();
                t.setTransactionId(rs.getString("transaction_id"));
                t.setServiceId(rs.getString("service_id"));
                t.setCustomerId(rs.getString("customer_id"));
                t.setReceptionistId(rs.getString("receptionist_id"));
                t.setLaundryStaffId(rs.getString("laundry_staff_id"));
                t.setTransactionDate(rs.getTimestamp("transaction_date"));
                t.setStatus(rs.getString("status"));
                t.setTotalWeight(rs.getDouble("total_weight"));
                t.setNotes(rs.getString("notes"));
                res.add(t);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return res;
    }

    public Transaction findById(String id) {
        String sql = "SELECT * FROM transactions WHERE transaction_id = ?";
        try (PreparedStatement ps = connect.getCon().prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Transaction t = new Transaction();
                t.setTransactionId(rs.getString("transaction_id"));
                t.setServiceId(rs.getString("service_id"));
                t.setCustomerId(rs.getString("customer_id"));
                t.setReceptionistId(rs.getString("receptionist_id"));
                t.setLaundryStaffId(rs.getString("laundry_staff_id"));
                t.setTransactionDate(rs.getTimestamp("transaction_date"));
                t.setStatus(rs.getString("status"));
                t.setTotalWeight(rs.getDouble("total_weight"));
                t.setNotes(rs.getString("notes"));
                return t;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean assignReceptionist(String transactionId, String receptionistId) {
        String sql = "UPDATE transactions SET receptionist_id = ? WHERE transaction_id = ?";
        try (PreparedStatement ps = connect.getCon().prepareStatement(sql)) {
            ps.setString(1, receptionistId);
            ps.setString(2, transactionId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean assignLaundryStaffAndFinish(String transactionId, String laundryStaffId) {
        String sql = "UPDATE transactions SET laundry_staff_id = ?, status = 'Finished' WHERE transaction_id = ?";
        try (PreparedStatement ps = connect.getCon().prepareStatement(sql)) {
            ps.setString(1, laundryStaffId);
            ps.setString(2, transactionId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
}
