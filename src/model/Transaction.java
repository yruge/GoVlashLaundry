package model;

import java.util.Date;

public class Transaction {
    private String transactionId;
    private String serviceId;
    private String customerId;
    private String receptionistId;
    private String laundryStaffId;
    private Date transactionDate;
    private String status; // Pending / Finished
    private double totalWeight;
    private String notes;

    public Transaction() {}

    // getters & setters
    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    public String getServiceId() { return serviceId; }
    public void setServiceId(String serviceId) { this.serviceId = serviceId; }
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public String getReceptionistId() { return receptionistId; }
    public void setReceptionistId(String receptionistId) { this.receptionistId = receptionistId; }
    public String getLaundryStaffId() { return laundryStaffId; }
    public void setLaundryStaffId(String laundryStaffId) { this.laundryStaffId = laundryStaffId; }
    public Date getTransactionDate() { return transactionDate; }
    public void setTransactionDate(Date transactionDate) { this.transactionDate = transactionDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public double getTotalWeight() { return totalWeight; }
    public void setTotalWeight(double totalWeight) { this.totalWeight = totalWeight; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}
