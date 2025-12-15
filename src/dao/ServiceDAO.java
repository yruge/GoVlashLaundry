package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Service;
import util.Connect;

public class ServiceDAO {

    public void addService(Service service) {
        String query = "INSERT INTO service (serviceName, serviceDescription, servicePrice, serviceDuration) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = Connect.getInstance().prepareStatement(query);
            ps.setString(1, service.getServiceName());
            ps.setString(2, service.getServiceDescription());
            ps.setDouble(3, service.getServicePrice());
            ps.setInt(4, service.getServiceDuration());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateService(Service service) {
        String query = "UPDATE service SET serviceName=?, serviceDescription=?, servicePrice=?, serviceDuration=? WHERE serviceID=?";
        try {
            PreparedStatement ps = Connect.getInstance().prepareStatement(query);
            ps.setString(1, service.getServiceName());
            ps.setString(2, service.getServiceDescription());
            ps.setDouble(3, service.getServicePrice());
            ps.setInt(4, service.getServiceDuration());
            ps.setInt(5, service.getServiceID());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteService(int serviceID) {
        String query = "DELETE FROM service WHERE serviceID = ?";
        try {
            PreparedStatement ps = Connect.getInstance().prepareStatement(query);
            ps.setInt(1, serviceID);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Service> getAllServices() {
        List<Service> services = new ArrayList<>();
        String query = "SELECT * FROM service";
        try {
            PreparedStatement ps = Connect.getInstance().prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                services.add(new Service(
                    rs.getInt("serviceID"),
                    rs.getString("serviceName"),
                    rs.getString("serviceDescription"),
                    rs.getDouble("servicePrice"),
                    rs.getInt("serviceDuration")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return services;
    }
}