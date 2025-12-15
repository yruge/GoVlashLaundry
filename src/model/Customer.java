package model;

public class Customer extends User {
    
    // Constructor yang BENAR
    public Customer(int userID, String userName, String userEmail, String userPassword, 
                   String userGender, String userDOB) {
        // Panggil constructor parent (User) dengan role "Customer"
        super(userID, userName, userEmail, userPassword, userGender, userDOB, "Customer");
    }
    
    // Constructor alternatif tanpa ID (untuk insert baru)
    public Customer(String userName, String userEmail, String userPassword, 
                   String userGender, String userDOB) {
        // ID akan auto-increment di database
        super(0, userName, userEmail, userPassword, userGender, userDOB, "Customer");
    }
    
    // Method khusus Customer jika ada
    // Contoh: getTransactionHistory(), getNotifications(), dll
}