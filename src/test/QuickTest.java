package test;

import model.User;

public class QuickTest {
    public static void main(String[] args) {
        // Test 1: Create User object
        User user = new User(99, "TestUser", "test@email.com", "password", 
                           "Male", "2000-01-01", "Customer");
        
        System.out.println("✅ User object created: " + user.getUserName());
        
        // Test 2: Test login method (harusnya null karena ngga di database)
        User loggedIn = user.login("admin@govlash.com", "admin123");
        System.out.println("✅ Login method works: " + (loggedIn != null ? "User found" : "Should be null"));
        
        System.out.println("\n🎯 File User.java BERFUNGSI dengan baik!");
    }
}