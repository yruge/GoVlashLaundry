package model;

import java.sql.Date;
import java.sql.ResultSet;
import database.Connect;

public class User {
    private int userId;
    private String userName;
    private String userEmail;
    private String password;
    private String userGender;
    private Date userDOB;
    private String userRole;
    
    // Constructor kosong
    public User() {}
    
    // Constructor dengan parameter String untuk DOB
    public User(int userId, String userName, String userEmail, String password, 
                String userGender, String userDOB, String userRole) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.password = password;
        this.userGender = userGender;
        
        // Convert String to Date
        try {
            this.userDOB = Date.valueOf(userDOB);
        } catch (Exception e) {
            this.userDOB = null;
            System.out.println("Warning: Invalid date format for user: " + userName);
        }
        
        this.userRole = userRole;
    }
    
    // Constructor dengan Date langsung
    public User(int userId, String userName, String userEmail, String password, 
                String userGender, Date userDOB, String userRole) {
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.password = password;
        this.userGender = userGender;
        this.userDOB = userDOB;
        this.userRole = userRole;
    }
    
    // ========== LOGIN METHOD ==========
    public User login(String email, String password) {
        try {
            Connect db = Connect.getInstance();
            String query = String.format(
                "SELECT * FROM users WHERE userEmail = '%s' AND password = '%s'",
                email, password
            );
            
            ResultSet rs = db.execQuery(query);
            
            if (rs.next()) {
                return new User(
                    rs.getInt("userId"),
                    rs.getString("userName"),
                    rs.getString("userEmail"),
                    rs.getString("password"),
                    rs.getString("userGender"),
                    rs.getDate("userDOB"),
                    rs.getString("userRole")
                );
            }
        } catch (Exception e) {
            System.out.println("Login error: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    // ========== ADD USER ==========
    public boolean addUser(String name, String email, String password, 
                           String gender, String dob, String role) {
        try {
            Connect db = Connect.getInstance();
            String query = String.format(
                "INSERT INTO users (userName, userEmail, password, userGender, userDOB, userRole) " +
                "VALUES ('%s', '%s', '%s', '%s', '%s', '%s')",
                name, email, password, gender, dob, role
            );
            
            db.execUpdate(query);
            System.out.println("User registered: " + email);
            return true;
        } catch (Exception e) {
            System.out.println("Add user error: " + e.getMessage());
            return false;
        }
    }
    
    // ========== ADD EMPLOYEE ==========
    public boolean addEmployee(String name, String email, String password, 
                               String gender, String dob, String role) {
        // Sama dengan addUser, tapi dengan validasi berbeda di controller
        return addUser(name, email, password, gender, dob, role);
    }
    
    // ========== GET USERS BY ROLE ==========
    public java.util.ArrayList<User> getUsersByRole(String role) {
        java.util.ArrayList<User> userList = new java.util.ArrayList<>();
        
        try {
            Connect db = Connect.getInstance();
            String query = String.format(
                "SELECT * FROM users WHERE userRole = '%s'",
                role
            );
            
            ResultSet rs = db.execQuery(query);
            
            while (rs.next()) {
                User user = new User(
                    rs.getInt("userId"),
                    rs.getString("userName"),
                    rs.getString("userEmail"),
                    rs.getString("password"),
                    rs.getString("userGender"),
                    rs.getDate("userDOB"),
                    rs.getString("userRole")
                );
                userList.add(user);
            }
        } catch (Exception e) {
            System.out.println("Get users by role error: " + e.getMessage());
        }
        
        return userList;
    }
    
    // ========== GET ALL USERS ==========
    public java.util.ArrayList<User> getAllUsers() {
        java.util.ArrayList<User> userList = new java.util.ArrayList<>();
        
        try {
            Connect db = Connect.getInstance();
            String query = "SELECT * FROM users ORDER BY userId";
            
            ResultSet rs = db.execQuery(query);
            
            while (rs.next()) {
                User user = new User(
                    rs.getInt("userId"),
                    rs.getString("userName"),
                    rs.getString("userEmail"),
                    rs.getString("password"),
                    rs.getString("userGender"),
                    rs.getDate("userDOB"),
                    rs.getString("userRole")
                );
                userList.add(user);
            }
        } catch (Exception e) {
            System.out.println("Get all users error: " + e.getMessage());
        }
        
        return userList;
    }
    
    // ========== GETTERS & SETTERS ==========
    public int getUserId() {
        return userId;
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getUserEmail() {
        return userEmail;
    }
    
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getUserGender() {
        return userGender;
    }
    
    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }
    
    public Date getUserDOB() {
        return userDOB;
    }
    
    public void setUserDOB(Date userDOB) {
        this.userDOB = userDOB;
    }
    
    public String getUserRole() {
        return userRole;
    }
    
    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
    
    // ========== TO STRING ==========
    @Override
    public String toString() {
        return userName + " (" + userRole + ") - " + userEmail;
    }
    
    // ========== VALIDATION METHODS ==========
    public boolean validateEmail() {
        if (userRole.equals("Customer")) {
            return userEmail.endsWith("@email.com");
        } else {
            return userEmail.endsWith("@govlash.com");
        }
    }
    
    public boolean validateAge() {
        if (userDOB == null) return false;
        
        long ageInMillis = System.currentTimeMillis() - userDOB.getTime();
        long ageInYears = ageInMillis / (1000L * 60 * 60 * 24 * 365);
        
        if (userRole.equals("Customer")) {
            return ageInYears >= 12;
        } else {
            return ageInYears >= 17;
        }
    }
}