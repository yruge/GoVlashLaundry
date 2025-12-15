package dao;

import java.sql.*;
import java.util.ArrayList;
import database.Connect;
import model.Notification;

public class NotificationDAO {
    private Connection con = Connect.getInstance().getConnection();

    public void insertNotification(int recipientID, String message) {
        try {
            // created_at otomatis diisi oleh database (TIMESTAMP DEFAULT CURRENT_TIMESTAMP)
            // is_read default 0 (false)
            String query = "INSERT INTO notifications (recipient_id, notification_message, is_read) VALUES (?, ?, 0)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, recipientID);
            ps.setString(2, message);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Notification> getByRecipient(int recipientID) {
        ArrayList<Notification> list = new ArrayList<>();
        try {
            String query = "SELECT * FROM notifications WHERE recipient_id = ? ORDER BY created_at DESC";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, recipientID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Notification(
                    rs.getInt("notification_id"),
                    rs.getInt("recipient_id"),
                    rs.getString("notification_message"),
                    rs.getTimestamp("created_at"),
                    rs.getBoolean("is_read")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void markAsRead(int notificationID) {
        try {
            String query = "UPDATE notifications SET is_read = 1 WHERE notification_id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, notificationID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteNotification(int notificationID) {
        try {
            String query = "DELETE FROM notifications WHERE notification_id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, notificationID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}