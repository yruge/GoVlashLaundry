package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Admin;
import model.Customer;
import model.LaundryStaff;
import model.Receptionist;
import model.User;
import util.Connect;

public class UserDAO {

    public User login(String email, String password) {
        String query = "SELECT * FROM user WHERE userEmail = ? AND userPassword = ?";
        try {
            PreparedStatement ps = Connect.getInstance().prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String role = rs.getString("userRole");
                int id = rs.getInt("userID");
                String name = rs.getString("userName");
                String gender = rs.getString("userGender");
                java.sql.Date dob = rs.getDate("userDOB");

                if ("Customer".equalsIgnoreCase(role)) {
                    return new Customer(id, name, email, password, gender, dob, role);
                } else if ("Admin".equalsIgnoreCase(role)) {
                    return new Admin(id, name, email, password, gender, dob, role);
                } else if ("Laundry Staff".equalsIgnoreCase(role)) {
                    return new LaundryStaff(id, name, email, password, gender, dob, role);
                } else if ("Receptionist".equalsIgnoreCase(role)) {
                    return new Receptionist(id, name, email, password, gender, dob, role);
                }
                
                return new User(id, name, email, password, gender, dob, role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void registerUser(User user) {
        String query = "INSERT INTO user (userName, userEmail, userPassword, userGender, userDOB, userRole) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = Connect.getInstance().prepareStatement(query);
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getUserEmail());
            ps.setString(3, user.getUserPassword());
            ps.setString(4, user.getUserGender());
            ps.setDate(5, user.getUserDOB());
            ps.setString(6, user.getUserRole());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isUsernameExists(String name) {
        String query = "SELECT userID FROM user WHERE userName = ?";
        try {
            PreparedStatement ps = Connect.getInstance().prepareStatement(query);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isEmailExists(String email) {
        String query = "SELECT userID FROM user WHERE userEmail = ?";
        try {
            PreparedStatement ps = Connect.getInstance().prepareStatement(query);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<User> getAllEmployees() {
        List<User> employees = new ArrayList<>();
        String query = "SELECT * FROM user WHERE userRole != 'Customer'";
        try {
            PreparedStatement ps = Connect.getInstance().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String role = rs.getString("userRole");
                if ("Admin".equalsIgnoreCase(role)) {
                    employees.add(new Admin(
                        rs.getInt("userID"), rs.getString("userName"), rs.getString("userEmail"),
                        rs.getString("userPassword"), rs.getString("userGender"), rs.getDate("userDOB"), role
                    ));
                } else if ("Laundry Staff".equalsIgnoreCase(role)) {
                    employees.add(new LaundryStaff(
                        rs.getInt("userID"), rs.getString("userName"), rs.getString("userEmail"),
                        rs.getString("userPassword"), rs.getString("userGender"), rs.getDate("userDOB"), role
                    ));
                } else if ("Receptionist".equalsIgnoreCase(role)) {
                    employees.add(new Receptionist(
                        rs.getInt("userID"), rs.getString("userName"), rs.getString("userEmail"),
                        rs.getString("userPassword"), rs.getString("userGender"), rs.getDate("userDOB"), role
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }
}