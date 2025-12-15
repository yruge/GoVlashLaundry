package model;

public class Customer extends User {

    public Customer(int userID, String userName, String userEmail, String userPassword, String userGender, String userDOB) {
        // Role otomatis di-set sebagai "Customer"
        super(userID, userName, userEmail, userPassword, userGender, userDOB, "Customer");
    }
}