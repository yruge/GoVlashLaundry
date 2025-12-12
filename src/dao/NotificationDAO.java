package dao;

import model.Notification;
import util.Connect;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotificationDAO {
    private Connect connect = Connect.getConnection();

    public boolean create(String id, String recipientId, String message) {
        String sql = "INSERT INTO notifications (notification_id, recipient_id, message, created_at, is_read) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connect.getCon().prepareStatement(sql)) {
            ps.setString(1, id);
            ps.setString(2, recipientId);
            ps.setString(3, message);
            ps.setTimestamp(4, new Timestamp(new Date().getTime()));
            ps.setBoolean(5, false);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public List<Notification> getByRecipient(String recipientId) {
        List<Notification> list = new ArrayList<>();
        String sql = "SELECT * FROM notifications WHERE recipient_id = ? ORDER BY created_at DESC";
        try (PreparedStatement ps = connect.getCon().prepareStatement(sql)) {
            ps.setString(1, recipientId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Notification n = new Notification();
                n.setNotificationId(rs.getString("notification_id"));
                n.setRecipientId(rs.getString("recipient_id"));
                n.setMessage(rs.getString("message"));
                n.setCreatedAt(rs.getTimestamp("created_at"));
                n.setRead(rs.getBoolean("is_read"));
                list.add(n);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public boolean markAsRead(String notificationId) {
        String sql = "UPDATE notifications SET is_read = true WHERE notification_id = ?";
        try (PreparedStatement ps = connect.getCon().prepareStatement(sql)) {
            ps.setString(1, notificationId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
}
