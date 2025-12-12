package controller;

import dao.NotificationDAO;
import dao.TransactionDAO;
import model.Transaction;

import java.util.List;
import java.util.UUID;

public class TransactionHandler {
    private TransactionDAO dao = new TransactionDAO();
    private NotificationDAO notifDao = new NotificationDAO();

    public String createTransaction(String serviceId, String customerId, double weight, String notes) {
        if (weight < 2 || weight > 50) return "Weight must be 2..50 kg";
        Transaction t = new Transaction();
        t.setTransactionId(UUID.randomUUID().toString());
        t.setServiceId(serviceId);
        t.setCustomerId(customerId);
        t.setStatus("Pending");
        t.setTotalWeight(weight);
        t.setNotes(notes);
        boolean ok = dao.create(t);
        return ok ? "SUCCESS" : "FAILED";
    }

    public List<Transaction> listAll() {
        return dao.findAll();
    }

    public List<Transaction> listByStatus(String status) {
        return dao.findByStatus(status);
    }

    public String assignReceptionist(String transactionId, String receptionistId) {
        boolean ok = dao.assignReceptionist(transactionId, receptionistId);
        return ok ? "SUCCESS" : "FAILED";
    }

    public String finishByLaundry(String transactionId, String laundryId) {
        boolean ok = dao.assignLaundryStaffAndFinish(transactionId, laundryId);
        if (ok) {
            // notify customer
            Transaction t = dao.findById(transactionId);
            if (t != null) {
                String msg = "Your order " + transactionId + " is finished and ready for pickup.";
                notifDao.create(UUID.randomUUID().toString(), t.getCustomerId(), msg);
            }
            return "SUCCESS";
        }
        return "FAILED";
    }
}
