package test;

import database.Connect;
import java.sql.ResultSet;

public class TestConnection {
    public static void main(String[] args) {
        System.out.println("🔍 Testing Database Connection...");
        
        Connect db = Connect.getInstance();
        
        // Test 1: Cek jumlah services
        String query = "SELECT COUNT(*) as total FROM services";
        ResultSet rs = db.execQuery(query);
        
        try {
            if (rs.next()) {
                int totalServices = rs.getInt("total");
                System.out.println("✅ Database CONNECTED!");
                System.out.println("📦 Total services: " + totalServices);
                
                // Test 2: Tampilkan semua services
                System.out.println("\n📋 Service List:");
                query = "SELECT service_id, service_name, service_price, service_duration FROM services";
                rs = db.execQuery(query);
                
                while (rs.next()) {
                    System.out.println("ID: " + rs.getInt("service_id") + 
                                     " | Name: " + rs.getString("service_name") +
                                     " | Price: Rp" + rs.getDouble("service_price") +
                                     " | Duration: " + rs.getInt("service_duration") + " days");
                }
            }
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}