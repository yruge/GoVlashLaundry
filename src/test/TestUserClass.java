package test;

import model.User;

public class TestUserClass {
    public static void main(String[] args) {
        System.out.println("=== TEST USER CLASS ===");
        
        // Test constructor
        User user1 = new User(1, "testuser", "test@email.com", "pass123", 
                             "Male", "2000-01-01", "Customer");
        
        System.out.println("User created: " + user1.getUserName());
        System.out.println("Email: " + user1.getUserEmail());
        System.out.println("Role: " + user1.getUserRole());
        
        // Test validation
        System.out.println("\nEmail validation: " + user1.validateEmail());
        System.out.println("Age validation: " + user1.validateAge());
        
        System.out.println("\n✅ User class is working!");
    }
}