package model;

import java.sql.Timestamp;

public class Notification {
    private int notificationID;
    private int recipientID;
    private String message;
    private Timestamp createdAt; // Menggunakan Timestamp untuk tanggal + jam
    private boolean isRead;

    public Notification(int notificationID, int recipientID, String message, Timestamp createdAt, boolean isRead) {
        this.notificationID = notificationID;
        this.recipientID = recipientID;
        this.message = message;
        this.createdAt = createdAt;
        this.isRead = isRead;
    }

    // --- Getters ---
    public int getNotificationID() { return notificationID; }
    public int getRecipientID() { return recipientID; }
    public String getMessage() { return message; }
    public Timestamp getCreatedAt() { return createdAt; }
    
    // Getter boolean biasanya menggunakan is...
    public boolean isRead() { return isRead; }
    
    public void setRead(boolean read) { isRead = read; }
}