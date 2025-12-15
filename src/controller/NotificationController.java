package controller;

import dao.NotificationDAO;
import model.Notification;
import java.util.ArrayList;

public class NotificationController {
    private NotificationDAO dao = new NotificationDAO();

    // [cite: 56] Admin sends notification
    public void sendNotification(int customerID, int transactionID) {
        String message = "Your order (ID: " + transactionID + ") is finished and ready for pickup. Thank you for choosing our service!";
        dao.insertNotification(customerID, message);
    }

    public ArrayList<Notification> getMyNotifications(int recipientID) {
        return dao.getByRecipient(recipientID);
    }

    public void readNotification(int notifID) {
        dao.markAsRead(notifID);
    }
    
    public void deleteNotification(int notifID) {
        dao.deleteNotification(notifID);
    }
}