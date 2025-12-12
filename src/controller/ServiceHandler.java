package controller;

import dao.ServiceDAO;
import model.Service;

import java.util.List;
import java.util.UUID;

public class ServiceHandler {
    private ServiceDAO dao = new ServiceDAO();

    public String createService(String name, String desc, double price, int duration) {
        if (name == null || name.isBlank()) return "Name required";
        if (price <= 0) return "Price must > 0";
        if (duration < 1) return "Duration invalid";
        Service s = new Service(UUID.randomUUID().toString(), name, desc, price, duration);
        return dao.create(s) ? "SUCCESS" : "FAILED";
    }

    public List<Service> getAll() throws Exception {
        return dao.findAll();
    }

    public boolean delete(String id) {
        return dao.delete(id);
    }
}
