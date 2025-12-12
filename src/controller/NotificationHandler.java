package controller;

import dao.NotificationDAO;
import model.Notification;

import java.util.List;
import java.util.UUID;

public class NotificationHandler {
    private NotificationDAO dao = new NotificationDAO();

    public boolean createFor(String recipientId, String message) {
        return dao.create(UUID.randomUUID().toString(), recipientId, message);
    }

    public List<Notification> listFor(String recipientId) {
        return dao.getByRecipient(recipientId);
    }

    public boolean markRead(String notificationId) {
        return dao.markAsRead(notificationId);
    }
}
