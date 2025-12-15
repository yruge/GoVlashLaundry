package test;

import controller.ServiceController;
import model.Service;
import java.sql.ResultSet;

public class TestServiceCRUD {
    public static void main(String[] args) {
        ServiceController controller = new ServiceController();
        
        System.out.println("=== TEST SERVICE MANAGEMENT ===");
        
        // 1. Get all services
        System.out.println("\n1. All Services:");
        ResultSet rs = controller.getAllServices();
        try {
            while (rs.next()) {
                System.out.println("- " + rs.getString("service_name") + " (Rp" + rs.getDouble("service_price") + ")");
            }
        } catch (Exception e) { e.printStackTrace(); }
        
        // 2. Add new service
        System.out.println("\n2. Adding new service...");
        Service newService = new Service("Ironing Only", "Ironing service without washing", 10000.00, 1);
        boolean added = controller.addService(newService);
        System.out.println("Add result: " + (added ? "SUCCESS" : "FAILED"));
        
        // 3. Update service
        System.out.println("\n3. Updating service...");
        Service toUpdate = controller.getServiceById(1); // Update Regular Wash
        if (toUpdate != null) {
            toUpdate.setServicePrice(16000.00); // Naik harga
            boolean updated = controller.updateService(toUpdate);
            System.out.println("Update result: " + (updated ? "SUCCESS" : "FAILED"));
        }
    }
}