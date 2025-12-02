package controller;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

import javafx.scene.control.Alert;
import model.User;

public class UserController {
	private User userModel;

	public UserController() {
		this.userModel = new User();
	}
	
	public User login(String email, String password) {
		if(email.isEmpty() || password.isEmpty()) {
			return null;
		}
		return userModel.login(email, password);
	}
	
	public ArrayList<User> getAllEmployees() {
		return userModel.getUsersByRole("Employee");
	}
	
	public void register(String name, String email, String password, String confirmPassword, String gender, LocalDate dob, String role)
	{
        
        // --- 1. VALIDATION ---
        
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showError("All text fields must be filled!");
            return; // Stop the method
        }
        
        if (gender == null) {
            showError("Please select a gender!");
            return;
        }
        
        if (dob == null) {
            showError("Please select a Date of Birth!");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showError("Passwords do not match!");
            return;
        }

        if (!email.endsWith("@email.com")) {
            showError("Email must end with @email.com!");
            return;
        }
        
        if (password.length() < 6) {
            showError("Password must be at least 6 characters!");
            return;
        }
        
        Period age = Period.between(dob, LocalDate.now());
        
        if(age.getYears() < 12)
        {
        	showError("You must be at least 12 years old to register!");
            return;
        }

        // --- 2. DATA CONVERSION & EXECUTION ---
        
        try {
            Date sqlDate = Date.valueOf(dob);
            String dateString = sqlDate.toString();
            
            // Call the Model
            userModel.addUser(name, email, password, gender, dateString, role);
            
            // Show Success Message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Registration Successful!");
            alert.show();
            
        } catch (Exception e) {
            showError("Database Error: " + e.getMessage());
        }
    }
	
	public void addEmployee(String name, String email, String password, String confirmPassword, String gender, LocalDate dob, String role)
	{
        
        // --- 1. VALIDATION ---
        
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showError("All text fields must be filled!");
            return; // Stop the method
        }
        
        if (gender == null) {
            showError("Please select a gender!");
            return;
        }
        
        if (dob == null) {
            showError("Please select a Date of Birth!");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showError("Passwords do not match!");
            return;
        }

        if (!email.endsWith("@govlash.com")) {
            showError("Email must end with @govlash.com!");
            return;
        }
        
        if (password.length() < 6) {
            showError("Password must be at least 6 characters!");
            return;
        }
        
        Period age = Period.between(dob, LocalDate.now());
        
        if(age.getYears() < 17)
        {
        	showError("Employee must be at least 17 years old to be added!");
            return;
        }

        // --- 2. DATA CONVERSION & EXECUTION ---
        
        try {
            Date sqlDate = Date.valueOf(dob);
            String dateString = sqlDate.toString();
            
            // Call the Model
            userModel.addEmployee(name, email, password, gender, dateString, role);
            
            // Show Success Message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Add Employee Successful!");
            alert.show();
            
        } catch (Exception e) {
            showError("Database Error: " + e.getMessage());
        }
    }
    
    // Helper method to keep code clean
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
    	}
}
