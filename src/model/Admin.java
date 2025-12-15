package model;

public class Admin extends Employee {

    public Admin(int userID, String userName, String userEmail, String userPassword, String userGender, String userDOB) {
        super(userID, userName, userEmail, userPassword, userGender, userDOB, "Admin");
    }
}