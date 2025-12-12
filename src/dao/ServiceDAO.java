package dao;

import model.Service;
import util.Connect;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceDAO {
    private Connect connect = Connect.getConnection();

    public boolean create(Service s) {
        String sql = "INSERT INTO services (service_id, name, description, price, duration_days) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connect.getCon().prepareStatement(sql)) {
            ps.setString(1, s.getServiceId());
            ps.setString(2, s.getName());
            ps.setString(3, s.getDescription());
            ps.setDouble(4, s.getPrice());
            ps.setInt(5, s.getDurationDays());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public List<Service> findAll() {
        List<Service> list = new ArrayList<>();
        String sql = "SELECT * FROM services ORDER BY name";
        try (PreparedStatement ps = connect.getCon().prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Service s = new Service();
                s.setServiceId(rs.getString("service_id"));
                s.setName(rs.getString("name"));
                s.setDescription(rs.getString("description"));
                s.setPrice(rs.getDouble("price"));
                s.setDurationDays(rs.getInt("duration_days"));
                list.add(s);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    public Service findById(String id) {
        String sql = "SELECT * FROM services WHERE service_id = ?";
        try (PreparedStatement ps = connect.getCon().prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Service s = new Service();
                s.setServiceId(rs.getString("service_id"));
                s.setName(rs.getString("name"));
                s.setDescription(rs.getString("description"));
                s.setPrice(rs.getDouble("price"));
                s.setDurationDays(rs.getInt("duration_days"));
                return s;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public boolean update(Service s) {
        String sql = "UPDATE services SET name=?, description=?, price=?, duration_days=? WHERE service_id=?";
        try (PreparedStatement ps = connect.getCon().prepareStatement(sql)) {
            ps.setString(1, s.getName());
            ps.setString(2, s.getDescription());
            ps.setDouble(3, s.getPrice());
            ps.setInt(4, s.getDurationDays());
            ps.setString(5, s.getServiceId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    public boolean delete(String id) {
        String sql = "DELETE FROM services WHERE service_id = ?";
        try (PreparedStatement ps = connect.getCon().prepareStatement(sql)) {
            ps.setString(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
}
