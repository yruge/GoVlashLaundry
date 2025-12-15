package controller;

import model.Service;
import database.Connect;
import java.sql.*;

public class ServiceController {
    private Connect db;
    
    public ServiceController() {
        db = Connect.getInstance();
    }
    
    // CREATE: Add new service
    public boolean addService(Service service) {
        if (!service.validateAll()) {
            System.out.println("❌ Validation failed!");
            return false;
        }
        
        String query = "INSERT INTO services (serviceName, serviceDescription, servicePrice, serviceDuration) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = db.prepareStatement(query);
            ps.setString(1, service.getServiceName());
            ps.setString(2, service.getServiceDescription());
            ps.setDouble(3, service.getServicePrice());
            ps.setInt(4, service.getServiceDuration());
            
            ps.executeUpdate();
            System.out.println("✅ Service added: " + service.getServiceName());
            return true;
        } catch (SQLException e) {
            System.out.println("❌ Error adding service: " + e.getMessage());
            return false;
        }
    }
    
    // READ: Get all services
    public ResultSet getAllServices() {
        String query = "SELECT * FROM services ORDER BY serviceId";  // DIUBAH: serviceId
        return db.execQuery(query);
    }
    
    // READ: Get service by ID
    public Service getServiceById(int serviceId) {
        String query = "SELECT * FROM services WHERE serviceId = " + serviceId;  // DIUBAH: serviceId
        ResultSet rs = db.execQuery(query);
        
        try {
            if (rs.next()) {
                return new Service(
                    rs.getInt("serviceId"),          // DIUBAH
                    rs.getString("serviceName"),     // DIUBAH
                    rs.getString("serviceDescription"),  // DIUBAH
                    rs.getDouble("servicePrice"),    // DIUBAH
                    rs.getInt("serviceDuration")     // DIUBAH
                );
            }
        } catch (SQLException e) {
            System.out.println("❌ Error getting service: " + e.getMessage());
        }
        return null;
    }
    
    // UPDATE: Update service
    public boolean updateService(Service service) {
        if (!service.validateAll()) {
            System.out.println("❌ Validation failed!");
            return false;
        }
        
        String query = "UPDATE services SET serviceName = ?, serviceDescription = ?, servicePrice = ?, serviceDuration = ? WHERE serviceId = ?";  // DIUBAH
        try {
            PreparedStatement ps = db.prepareStatement(query);
            ps.setString(1, service.getServiceName());
            ps.setString(2, service.getServiceDescription());
            ps.setDouble(3, service.getServicePrice());
            ps.setInt(4, service.getServiceDuration());
            ps.setInt(5, service.getServiceId());
            
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Service updated: " + service.getServiceName());
                return true;
            }
        } catch (SQLException e) {
            System.out.println("❌ Error updating service: " + e.getMessage());
        }
        return false;
    }
    
    // DELETE: Delete service
    public boolean deleteService(int serviceId) {
        String query = "DELETE FROM services WHERE serviceId = ?";  // DIUBAH
        try {
            PreparedStatement ps = db.prepareStatement(query);
            ps.setInt(1, serviceId);
            
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("✅ Service deleted, ID: " + serviceId);
                return true;
            }
        } catch (SQLException e) {
            System.out.println("❌ Error deleting service: " + e.getMessage());
        }
        return false;
    }
}