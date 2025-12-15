package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Notification;
import util.Connect;

public class NotificationDAO {

    public void createNotification(Notification n) {
        String query = "INSERT INTO notification (recipientID, notificationMessage, createdAt, isRead) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = Connect.getInstance().prepareStatement(query);
            ps.setInt(1, n.getRecipientID());
            ps.setString(2, n.getNotificationMessage());
            ps.setTimestamp(3, n.getCreatedAt());
            ps.setBoolean(4, n.isRead());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Notification> getByRecipient(int recipientID) {
        List<Notification> list = new ArrayList<>();
        String query = "SELECT * FROM notification WHERE recipientID = ? ORDER BY createdAt DESC";
        try {
            PreparedStatement ps = Connect.getInstance().prepareStatement(query);
            ps.setInt(1, recipientID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Notification(
                    rs.getInt("notificationID"),
                    rs.getInt("recipientID"),
                    rs.getString("notificationMessage"),
                    rs.getTimestamp("createdAt"),
                    rs.getBoolean("isRead")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void deleteNotification(int notificationID) {
        String query = "DELETE FROM notification WHERE notificationID = ?";
        try {
            PreparedStatement ps = Connect.getInstance().prepareStatement(query);
            ps.setInt(1, notificationID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void markAsRead(int notificationID) {
        String query = "UPDATE notification SET isRead = true WHERE notificationID = ?";
        try {
            PreparedStatement ps = Connect.getInstance().prepareStatement(query);
            ps.setInt(1, notificationID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}