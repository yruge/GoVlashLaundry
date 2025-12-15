package controller;
import dao.TransactionDAO;
import model.Transaction;
import java.util.ArrayList;
import javafx.scene.control.Alert;

public class TransactionController {
    private TransactionDAO transactionDAO = new TransactionDAO();

    public void order(int serviceID, int customerID, String weightStr, String notes) {
        try {
            double weight = Double.parseDouble(weightStr);
            transactionDAO.insertTransaction(serviceID, customerID, weight, notes);
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Order Placed Successfully!");
            alert.show();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Invalid Weight!");
            alert.show();
        }
    }

    public ArrayList<Transaction> getCustomerHistory(int customerID) {
        return transactionDAO.getTransactionsByCustomer(customerID);
    }
    
 // Tambahkan method ini:

    public void assignOrder(int transactionID, int receptionistID, int staffID) {
        transactionDAO.assignStaff(transactionID, receptionistID, staffID);
    }

    public void finishOrder(int transactionID) {
        transactionDAO.updateStatus(transactionID, "Finished");
    }

    public ArrayList<Transaction> getPendingTransactions() {
        return transactionDAO.getTransactionsByStatus("Pending");
    }

    public ArrayList<Transaction> getStaffTasks(int staffID) {
        return transactionDAO.getAssignedTransactions(staffID);
    }
    
    public ArrayList<Transaction> getAllTransactions() {
        return transactionDAO.getAllTransactions();
    }
    
    public ArrayList<Transaction> getFinishedTransactions() {
        return transactionDAO.getTransactionsByStatus("Finished");
    }
}