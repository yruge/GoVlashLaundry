package test;

import controller.UserController;
import model.User;

public class TestLogin {
    public static void main(String[] args) {
        UserController userCtrl = new UserController();
        
        System.out.println("=== 🚀 TEST LOGIN SYSTEM ===");
        System.out.println("Database: laundry_db");
        System.out.println("Table: users (userId, userName, userEmail, userRole)");
        System.out.println("=================================\n");
        
        // ===== TEST 1: ADMIN LOGIN =====
        System.out.println("1. 🔑 Testing ADMIN Login:");
        System.out.println("   Email: admin@govlash.com");
        System.out.println("   Password: admin123");
        
        User admin = userCtrl.login("admin@govlash.com", "admin123");
        if (admin != null) {
            System.out.println("   ✅ SUCCESS! Logged in as: " + admin.getUserName());
            System.out.println("      Role: " + admin.getUserRole());
            System.out.println("      Email: " + admin.getUserEmail());
        } else {
            System.out.println("   ❌ FAILED! Invalid credentials or database error.");
        }
        
        // ===== TEST 2: WRONG PASSWORD =====
        System.out.println("\n2. ❌ Testing Wrong Password:");
        System.out.println("   Email: admin@govlash.com");
        System.out.println("   Password: wrongpassword");
        
        User wrong = userCtrl.login("admin@govlash.com", "wrongpassword");
        if (wrong == null) {
            System.out.println("   ✅ CORRECT! Login rejected (as expected).");
        } else {
            System.out.println("   ❌ WRONG! Should have failed!");
        }
        
        // ===== TEST 3: CUSTOMER LOGIN =====
        System.out.println("\n3. 👤 Testing CUSTOMER Login:");
        System.out.println("   Email: customer1@email.com");
        System.out.println("   Password: 123456");
        
        User customer = userCtrl.login("customer1@email.com", "123456");
        if (customer != null) {
            System.out.println("   ✅ SUCCESS! Logged in as: " + customer.getUserName());
            System.out.println("      Role: " + customer.getUserRole());
        } else {
            System.out.println("   ❌ FAILED! Customer not found.");
        }
        
        // ===== TEST 4: RECEPTIONIST LOGIN =====
        System.out.println("\n4. 🏢 Testing RECEPTIONIST Login:");
        System.out.println("   Email: reception1@govlash.com");
        System.out.println("   Password: 123456");
        
        User reception = userCtrl.login("reception1@govlash.com", "123456");
        if (reception != null) {
            System.out.println("   ✅ SUCCESS! Logged in as: " + reception.getUserName());
            System.out.println("      Role: " + reception.getUserRole());
        } else {
            System.out.println("   ❌ FAILED! Receptionist not found.");
        }
        
        // ===== TEST 5: DATABASE QUERY DIRECTLY =====
        System.out.println("\n5. 📊 Database Status:");
        try {
            database.Connect db = database.Connect.getInstance();
            java.sql.ResultSet rs = db.execQuery("SELECT COUNT(*) as total FROM users");
            if (rs.next()) {
                System.out.println("   Total users in database: " + rs.getInt("total"));
            }
            
            rs = db.execQuery("SELECT userId, userName, userEmail, userRole FROM users");
            System.out.println("   User List:");
            while (rs.next()) {
                System.out.println("   - ID: " + rs.getInt("userId") + 
                                 ", Name: " + rs.getString("userName") + 
                                 ", Email: " + rs.getString("userEmail") + 
                                 ", Role: " + rs.getString("userRole"));
            }
        } catch (Exception e) {
            System.out.println("   ❌ Error checking database: " + e.getMessage());
        }
        
        System.out.println("\n=== 🎯 TEST COMPLETE ===");
    }
}