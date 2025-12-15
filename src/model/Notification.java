package model;

import java.sql.Timestamp;
import java.util.List;

public class Notification {
    private int notificationID;
    private int recipientID;
    private String notificationMessage;
    private Timestamp createdAt;
    private boolean isRead;

    public Notification(int notificationID, int recipientID, String notificationMessage, Timestamp createdAt, boolean isRead) {
        this.notificationID = notificationID;
        this.recipientID = recipientID;
        this.notificationMessage = notificationMessage;
        this.createdAt = createdAt;
        this.isRead = isRead;
    }

    public int getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(int notificationID) {
        this.notificationID = notificationID;
    }

    public int getRecipientID() {
        return recipientID;
    }

    public void setRecipientID(int recipientID) {
        this.recipientID = recipientID;
    }

    public String getNotificationMessage() {
        return notificationMessage;
    }

    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean isRead) {
        this.isRead = isRead;
    }
}