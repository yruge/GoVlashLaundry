package model;

public abstract class User {
    protected int userID;
    protected String userName;
    protected String userEmail;
    protected String userPassword;
    protected String userGender;
    protected String userDOB; // Disimpan sebagai String (yyyy-mm-dd) atau java.sql.Date
    protected String userRole;

    public User(int userID, String userName, String userEmail, String userPassword, String userGender, String userDOB, String userRole) {
        this.userID = userID;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userGender = userGender;
        this.userDOB = userDOB;
        this.userRole = userRole;
    }

    // Constructor kosong untuk fleksibilitas
    public User() {}

    // --- Getters and Setters ---
    public int getUserID() { return userID; }
    public void setUserID(int userID) { this.userID = userID; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public String getUserPassword() { return userPassword; }
    public void setUserPassword(String userPassword) { this.userPassword = userPassword; }

    public String getUserGender() { return userGender; }
    public void setUserGender(String userGender) { this.userGender = userGender; }

    public String getUserDOB() { return userDOB; }
    public void setUserDOB(String userDOB) { this.userDOB = userDOB; }

    public String getUserRole() { return userRole; }
    public void setUserRole(String userRole) { this.userRole = userRole; }
    
    // Override toString agar ComboBox menampilkan Nama, bukan alamat memori
    @Override
    public String toString() {
        return this.userName;
    }
}