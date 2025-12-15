package controller;

import dao.NotificationDAO;
import model.Notification;
import java.sql.Timestamp;
import java.util.List;

public class NotificationController {

    private NotificationDAO notificationDAO = new NotificationDAO();

    public void sendNotification(int recipientID, String message) {
        
        Timestamp now = new Timestamp(System.currentTimeMillis());
        
        Notification notif = new Notification(0, recipientID, message, now, false);
        
        notificationDAO.createNotification(notif);
    }

    public List<Notification> getNotificationsByRecipientID(int recipientID) {
        return notificationDAO.getByRecipient(recipientID);
    }

    public void deleteNotification(int notificationID) {
        notificationDAO.deleteNotification(notificationID);
    }

    public void markAsRead(int notificationID) {
        notificationDAO.markAsRead(notificationID);
    }
}