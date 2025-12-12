package controller;

import dao.UserDAO;
import model.User;
import util.Validation;

import java.util.Date;
import java.util.UUID;

public class AuthHandler {
    private UserDAO userDAO = new UserDAO();

    public String registerCustomer(String username, String email, String password, String confirm, String gender, Date dob) {
        if (username == null || username.isBlank()) return "Username required";
        if (password == null || password.length() < 6) return "Password min 6 chars";
        if (!password.equals(confirm)) return "Password mismatch";
        if (!Validation.validCustomerEmail(email)) return "Customer email must end @email.com";
        if (!Validation.isAdult(dob, 12)) return "Must be at least 12 years old";
        if (userDAO.findByEmail(email) != null) return "Email already used";
        User u = new User(UUID.randomUUID().toString(), username, email, password, gender, dob, "Customer");
        return userDAO.insert(u) ? "SUCCESS" : "FAILED";
    }

    public String registerEmployee(String username, String email, String password, String confirm, String gender, Date dob, String role) {
        if (!Validation.validEmployeeEmail(email)) return "Employee email must end @govlash.com";
        if (userDAO.findByEmail(email) != null) return "Email already used";
        User u = new User(UUID.randomUUID().toString(), username, email, password, gender, dob, role);
        return userDAO.insert(u) ? "SUCCESS" : "FAILED";
    }

    public User login(String email, String password) {
        User u = userDAO.findByEmail(email);
        if (u == null) return null;
        if (!u.getPassword().equals(password)) return null; // NOTE: hashing not implemented for demo
        return u;
    }
}
