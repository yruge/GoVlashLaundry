package dao;

import java.sql.*;
import java.util.ArrayList;
import database.Connect;
import model.*;

public class UserDAO {
    private Connection con = Connect.getInstance().getConnection();

    // Helper untuk mapping data
    private User mapUser(ResultSet rs) throws SQLException {
        int id = rs.getInt("user_id");
        String name = rs.getString("user_name");
        String email = rs.getString("user_email");
        String pass = rs.getString("user_password");
        String gender = rs.getString("user_gender");
        String dob = rs.getString("user_dob");
        String role = rs.getString("user_role");

        // DEBUG: Cek data yang diambil
        System.out.println("DEBUG: Data ditemukan di DB -> Role: " + role + ", Nama: " + name);

        switch (role) {
            case "Admin": return new Admin(id, name, email, pass, gender, dob);
            case "Customer": return new Customer(id, name, email, pass, gender, dob);
            case "Receptionist": return new Receptionist(id, name, email, pass, gender, dob);
            case "Laundry Staff": return new LaundryStaff(id, name, email, pass, gender, dob);
            default: return null;
        }
    }

    public User login(String email, String password) {
        // DEBUG: Cek Koneksi
        if (con == null) {
            System.err.println("FATAL ERROR: Koneksi ke Database NULL! Cek file Connect.java atau nyalakan XAMPP.");
            return null;
        }

        try {
            System.out.println("DEBUG: Mencoba login dengan Email: " + email + " | Pass: " + password);
            
            String query = "SELECT * FROM users WHERE user_email = ? AND user_password = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                System.out.println("DEBUG: User ditemukan! Memproses mapping...");
                return mapUser(rs);
            } else {
                System.err.println("DEBUG: Query berhasil tapi user TIDAK ditemukan. Cek kesesuaian email/password.");
            }
        } catch (SQLException e) {
            System.err.println("DEBUG ERROR SQL: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // ... (Biarkan method insertUser dan getUsersByRole seperti sebelumnya) ...
    public void insertUser(String name, String email, String password, String gender, String dob, String role) {
        try {
            String query = "INSERT INTO users (user_name, user_email, user_password, user_gender, user_dob, user_role) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, name); ps.setString(2, email); ps.setString(3, password); 
            ps.setString(4, gender); ps.setString(5, dob); ps.setString(6, role);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public ArrayList<User> getUsersByRole(String role) {
        ArrayList<User> users = new ArrayList<>();
        try {
            String query = role.equals("Employee") ? "SELECT * FROM users WHERE user_role != 'Customer'" : "SELECT * FROM users WHERE user_role = ?";
            PreparedStatement ps = con.prepareStatement(query);
            if(!role.equals("Employee")) ps.setString(1, role);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User u = mapUser(rs);
                if (u != null) users.add(u);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return users;
    }
}