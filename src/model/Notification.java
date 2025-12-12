package model;

import java.util.Date;

public class Notification {
    private String notificationId;
    private String recipientId;
    private String message;
    private Date createdAt;
    private boolean isRead;

    public Notification() {}

    // getters & setters
    public String getNotificationId() { return notificationId; }
    public void setNotificationId(String notificationId) { this.notificationId = notificationId; }
    public String getRecipientId() { return recipientId; }
    public void setRecipientId(String recipientId) { this.recipientId = recipientId; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public boolean isRead() { return isRead; }
    public void setRead(boolean isRead) { this.isRead = isRead; }
}
