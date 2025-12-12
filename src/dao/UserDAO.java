package dao;

import model.User;
import util.Connect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private Connect connect = Connect.getConnection();

    public boolean insert(User u) {
        String sql = "INSERT INTO users (user_id, username, email, password, gender, dob, role) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connect.getCon().prepareStatement(sql)) {
            ps.setString(1, u.getUserId());
            ps.setString(2, u.getUsername());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getPassword());
            ps.setString(5, u.getGender());
            ps.setDate(6, new java.sql.Date(u.getDob().getTime()));
            ps.setString(7, u.getRole());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public User findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement ps = connect.getCon().prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User u = new User();
                u.setUserId(rs.getString("user_id"));
                u.setUsername(rs.getString("username"));
                u.setEmail(rs.getString("email"));
                u.setPassword(rs.getString("password"));
                u.setGender(rs.getString("gender"));
                u.setDob(rs.getDate("dob"));
                u.setRole(rs.getString("role"));
                return u;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public User findById(String id) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        try (PreparedStatement ps = connect.getCon().prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User u = new User();
                u.setUserId(rs.getString("user_id"));
                u.setUsername(rs.getString("username"));
                u.setEmail(rs.getString("email"));
                u.setPassword(rs.getString("password"));
                u.setGender(rs.getString("gender"));
                u.setDob(rs.getDate("dob"));
                u.setRole(rs.getString("role"));
                return u;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public List<User> findByRole(String role) {
        List<User> res = new ArrayList<>();
        String sql = "SELECT * FROM users WHERE role = ?";
        try (PreparedStatement ps = connect.getCon().prepareStatement(sql)) {
            ps.setString(1, role);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User u = new User();
                u.setUserId(rs.getString("user_id"));
                u.setUsername(rs.getString("username"));
                u.setEmail(rs.getString("email"));
                u.setPassword(rs.getString("password"));
                u.setGender(rs.getString("gender"));
                u.setDob(rs.getDate("dob"));
                u.setRole(rs.getString("role"));
                res.add(u);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return res;
    }
}
