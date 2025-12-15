package controller;
import dao.ServiceDAO;
import model.Service;
import java.util.ArrayList;
import javafx.scene.control.Alert;

public class ServiceController {
    private ServiceDAO serviceDAO = new ServiceDAO();

    public ArrayList<Service> getAllServices() {
        return serviceDAO.getAllServices();
    }

    public void addService(String name, String desc, String priceStr, String durationStr) {
        try {
            double price = Double.parseDouble(priceStr);
            int duration = Integer.parseInt(durationStr);
            if(name.isEmpty() || desc.isEmpty()) throw new Exception("Empty fields");
            
            serviceDAO.insertService(name, desc, price, duration);
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Service Added!");
            alert.show();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Invalid Input: " + e.getMessage());
            alert.show();
        }
    }
}