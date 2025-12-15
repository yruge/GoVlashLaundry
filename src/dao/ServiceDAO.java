package dao;

import java.sql.*;
import java.util.ArrayList;
import database.Connect;
import model.Service;

public class ServiceDAO {
    private Connection con = Connect.getInstance().getConnection();

    public ArrayList<Service> getAllServices() {
        ArrayList<Service> services = new ArrayList<>();
        try {
            String query = "SELECT * FROM services";
            ResultSet rs = con.createStatement().executeQuery(query);
            while (rs.next()) {
                services.add(new Service(
                    rs.getInt("service_id"),
                    rs.getString("service_name"),
                    rs.getString("service_description"),
                    rs.getDouble("service_price"),
                    rs.getInt("service_duration")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return services;
    }

    public void insertService(String name, String desc, double price, int duration) {
        try {
            String query = "INSERT INTO services (service_name, service_description, service_price, service_duration) VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, desc);
            ps.setDouble(3, price);
            ps.setInt(4, duration);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Fitur tambahan jika diperlukan Update/Delete
    public void updateService(int id, String name, String desc, double price, int duration) {
        try {
            String query = "UPDATE services SET service_name=?, service_description=?, service_price=?, service_duration=? WHERE service_id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, name); ps.setString(2, desc); ps.setDouble(3, price); ps.setInt(4, duration); ps.setInt(5, id);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
    
    public void deleteService(int id) {
        try {
            PreparedStatement ps = con.prepareStatement("DELETE FROM services WHERE service_id=?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) { e.printStackTrace(); }
    }
}