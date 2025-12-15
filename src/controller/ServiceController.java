package controller;

import dao.ServiceDAO;
import model.Service;
import java.util.List;

public class ServiceController {

    private ServiceDAO serviceDAO = new ServiceDAO();

    // --- VALIDATION ---
    public String validateAddService(String name, String description, Double price, Integer duration) {
        if (name.isEmpty()) return "Service Name cannot be empty.";
        if (name.length() > 50) return "Service Name must be <= 50 characters."; 
        
        if (description.isEmpty()) return "Description cannot be empty.";
        if (description.length() > 250) return "Description must be <= 250 characters."; 
        
        if (price <= 0) return "Price must be greater than 0."; 
        
        if (duration < 1 || duration > 30) return "Duration must be between 1 and 30 days."; 

        return "VALID";
    }

    public String validateEditService(String name, String description, Double price, Integer duration) {
        // Validasi edit biasanya sama dengan add
        return validateAddService(name, description, price, duration);
    }

    // --- OPERATIONS ---
    public void addService(String name, String description, Double price, Integer duration) {
        // ID 0 karena Auto Increment
        Service service = new Service(0, name, description, price, duration);
        serviceDAO.addService(service);
    }

    public void editService(int serviceID, String name, String description, Double price, Integer duration) {
        Service service = new Service(serviceID, name, description, price, duration);
        serviceDAO.updateService(service);
    }

    public void deleteService(int serviceID) {
        serviceDAO.deleteService(serviceID);
    }

    public List<Service> getAllServices() {
        return serviceDAO.getAllServices();
    }
}