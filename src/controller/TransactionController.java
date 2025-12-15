package controller;

import dao.TransactionDAO;
import model.Transaction;
import java.sql.Date;
import java.util.List;

public class TransactionController {

    private TransactionDAO transactionDAO = new TransactionDAO();

    // --- VALIDATION ---
    public String validateOrder(Double totalWeight, String notes) {
        if (totalWeight == null) return "Weight cannot be empty.";
        if (totalWeight < 2 || totalWeight > 50) return "Weight must be between 2 and 50kg."; 
        
        if (notes.length() > 250) return "Notes must be <= 250 characters."; 

        return "VALID";
    }

    // --- OPERATIONS ---
    public void orderLaundryService(int serviceID, int customerID, double totalWeight, String notes) {
        long millis = System.currentTimeMillis();
        Date today = new Date(millis);
        
        // Receptionist & Staff ID null (atau 0/Integer null) di awal 
        // Status awal "Pending" 
        Transaction tr = new Transaction(0, serviceID, customerID, 0, 0, today, "Pending", totalWeight, notes);
        
        transactionDAO.createTransaction(tr);
    }

    public List<Transaction> getAllTransactions() {
        return transactionDAO.getAllTransactions(); // Harus descending order di DAO 
    }

    public List<Transaction> getTransactionsByStatus(String status) {
        return transactionDAO.getTransactionsByStatus(status);
    }
    
    // Untuk Receptionist Assign Laundry Staff
    public void assignOrderToLaundryStaff(int transactionID, int receptionistID, int laundryStaffID) {
        transactionDAO.assignStaff(transactionID, receptionistID, laundryStaffID);
    }
    
    // Untuk Laundry Staff update status ke Finished
    public void updateTransactionStatus(int transactionID, String status) {
        transactionDAO.updateStatus(transactionID, status);
    }
    
    public List<Transaction> getAssignedOrdersByLaundryStaffID(int laundryStaffID) {
        return transactionDAO.getByLaundryStaff(laundryStaffID);
    }
    
    public List<Transaction> getTransactionsByCustomerID(int customerID) {
        return transactionDAO.getByCustomer(customerID);
    }
}