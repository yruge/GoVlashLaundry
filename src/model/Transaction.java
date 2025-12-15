package model;

import java.sql.Date;

public class Transaction {
    private int transactionID;
    private int serviceID;
    private int customerID;
    // Menggunakan Integer (Wrapper) agar bisa menampung null (jika belum di-assign)
    private Integer receptionistID; 
    private Integer laundryStaffID; 
    
    private Date transactionDate;
    private String transactionStatus;
    private double totalWeight;
    private String transactionNotes;
    
    // Field Tambahan untuk Keperluan Tampilan (Hasil Join di DAO)
    private String serviceName; 

    // Constructor Lengkap
    public Transaction(int transactionID, int serviceID, int customerID, Integer receptionistID, Integer laundryStaffID, 
                       Date transactionDate, String transactionStatus, double totalWeight, String transactionNotes, String serviceName) {
        this.transactionID = transactionID;
        this.serviceID = serviceID;
        this.customerID = customerID;
        this.receptionistID = receptionistID;
        this.laundryStaffID = laundryStaffID;
        this.transactionDate = transactionDate;
        this.transactionStatus = transactionStatus;
        this.totalWeight = totalWeight;
        this.transactionNotes = transactionNotes;
        this.serviceName = serviceName;
    }

    // --- Getters and Setters ---
    public int getTransactionID() { return transactionID; }
    public int getServiceID() { return serviceID; }
    public int getCustomerID() { return customerID; }
    
    public Integer getReceptionistID() { return receptionistID; }
    public Integer getLaundryStaffID() { return laundryStaffID; }
    
    public Date getTransactionDate() { return transactionDate; }
    public String getTransactionStatus() { return transactionStatus; }
    public double getTotalWeight() { return totalWeight; }
    public String getTransactionNotes() { return transactionNotes; }
    
    // Getter khusus untuk kolom tabel
    public String getServiceName() { return serviceName; }
}