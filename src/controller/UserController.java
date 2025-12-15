package controller;
import dao.UserDAO;
import model.User;
import java.time.LocalDate;
import java.util.ArrayList;
import javafx.scene.control.Alert;

public class UserController {
    private UserDAO userDAO = new UserDAO();
    private static User currentUser; // Menyimpan sesi user yang login

    public User login(String email, String password) {
        User user = userDAO.login(email, password);
        if (user != null) currentUser = user;
        return user;
    }

    public static User getCurrentUser() { return currentUser; }

    public void register(String name, String email, String password, String confirm, String gender, LocalDate dob, String role) {
        // ... (Logika Validasi Anda tetap sama, copy paste di sini) ...
        
        // Ganti baris model.addUser menjadi:
        userDAO.insertUser(name, email, password, gender, dob.toString(), role);
    }
    
    public void addEmployee(String name, String email, String password, String confirm, String gender, LocalDate dob, String role) {
        // ... (Logika Validasi) ...
        userDAO.insertUser(name, email, password, gender, dob.toString(), role);
    }

    public ArrayList<User> getAllEmployees() {
        return userDAO.getUsersByRole("Employee"); // Atau spesifik role
    }
}