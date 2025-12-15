package model;

public class Receptionist extends Employee {

    public Receptionist(int userID, String userName, String userEmail, String userPassword, String userGender, String userDOB) {
        super(userID, userName, userEmail, userPassword, userGender, userDOB, "Receptionist");
    }
}