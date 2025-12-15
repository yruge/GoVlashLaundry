package view;

import java.time.LocalDate;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.User;
import controller.UserController;

public class Auth {
	UserController userController = new UserController();
	Stage stage;
	Scene scene;
	BorderPane bp;
	GridPane formContainer;
	
	TextField userName, userEmail;
	PasswordField password, confirmPassword;	
	RadioButton maleRadio, femaleRadio;
	ToggleGroup genderGroup;
	DatePicker dobPicker;
	
	MenuBar menuBar;
	Menu menu;
	MenuItem registerItem, loginItem;
	Label formLbl, nameLbl, emailLbl, passwordLbl, confirmLbl, genderLbl, dobLbl;
	Button registerBtn, loginBtn;
	
	public Auth(Stage stage) {
		this.stage = stage;

		initiate();
		initializeRegisterForm();
		initializeStage();
		scene = new Scene(bp, 800, 500);
		stage.setScene(scene);
		stage.setTitle("Authentication Page");
		stage.show();
		
	}
	
	public void initializeLoginForm() {
		GridPane loginGrid = new GridPane(); // Create a different grid for login
	    loginGrid.setAlignment(Pos.CENTER);
	    loginGrid.setVgap(10);
	    loginGrid.setHgap(10);
	    loginGrid.setPadding(new Insets(20));

	    Label loginTitle = new Label("Login Page");
	    
	    // Reuse existing fields or create new ones
	    // (Here reusing userEmail and password for simplicity)
	    loginGrid.add(loginTitle, 0, 0);
	    
	    loginGrid.add(emailLbl, 0, 1);
	    loginGrid.add(userEmail, 1, 1); // Note: This moves the textfield here
	    
	    loginGrid.add(passwordLbl, 0, 2);
	    loginGrid.add(password, 1, 2);
	    
	    loginGrid.add(loginBtn, 1, 3);

	    // CRITICAL: Swap the view
	    bp.setCenter(loginGrid);
	}
	
	public void initializeRegisterForm() {
		formContainer = new GridPane();
		formContainer.setAlignment(Pos.CENTER);
        formContainer.setVgap(10);
        formContainer.setHgap(10);
        formContainer.setPadding(new Insets(20));

        // Column 0 (Labels), Column 1 (Inputs)
        formContainer.add(formLbl, 0, 0);
        
        formContainer.add(nameLbl, 0, 1);
        formContainer.add(userName, 1, 1);
        
        formContainer.add(emailLbl, 0, 2);
        formContainer.add(userEmail, 1, 2);
        
        formContainer.add(passwordLbl, 0, 3);
        formContainer.add(password, 1, 3);
        
        formContainer.add(confirmLbl, 0, 4);
        formContainer.add(confirmPassword, 1, 4);
        
        formContainer.add(genderLbl, 0, 5);
        // Add radios side-by-side
        HBox genderBox = new HBox(10, maleRadio, femaleRadio);
        formContainer.add(genderBox, 1, 5);
        
        formContainer.add(dobLbl, 0, 6);
        formContainer.add(dobPicker, 1, 6);
        formContainer.add(registerBtn, 1, 8);
        
        bp.setCenter(formContainer);
	}
	
	public void initializeStage() {
		bp.setTop(menuBar);
		bp.setCenter(formContainer);	
	}

	private void initiate() {
		bp = new BorderPane();
		formContainer = new GridPane();
		
		userName = new TextField();
		userEmail = new TextField();
		password = new PasswordField();
		confirmPassword = new PasswordField();
		
		maleRadio = new RadioButton("Male");
		femaleRadio = new RadioButton("Female");
		genderGroup = new ToggleGroup();
		maleRadio.setToggleGroup(genderGroup);
		femaleRadio.setToggleGroup(genderGroup);
		
		registerBtn = new Button("Register");
		loginBtn = new Button("Login");	
		
		
		dobPicker = new DatePicker();
		
		menuBar = new MenuBar();
		menu = new Menu("Page");
		registerItem = new MenuItem("Register");
		loginItem = new MenuItem("Login");
		menu.getItems().addAll(registerItem, loginItem);
		menuBar.getMenus().add(menu);
		
		
		formLbl = new Label("Register New User");
		nameLbl = new Label("Username"); 
		emailLbl = new Label("Email");
		passwordLbl = new Label("Password");
		confirmLbl = new Label("Confirm Password");
		genderLbl = new Label("Gender");
		dobLbl = new Label("Date of Birth");
		
		registerItem.setOnAction(e -> {
			initializeRegisterForm(); 
		});
		
		registerBtn.setOnAction(e -> {
					String name = userName.getText();
				    String mail = userEmail.getText();
				    String pass = password.getText();
				    String conf = confirmPassword.getText();
				    LocalDate dob = dobPicker.getValue();
				    
				    // Get Gender
				    String gender = null;
				    if (maleRadio.isSelected()) gender = "Male";
				    else if (femaleRadio.isSelected()) gender = "Female";
				    
				    // 2. Call Controller (Void)
				    // We don't check for a result string anymore. The Controller handles the alerts.
				    userController.register(name, mail, pass, conf, gender, dob, "Customer");// Call the method to show Register
			});

		loginItem.setOnAction(e -> {
		    initializeLoginForm();    // Call the method to show Login
		});
		
		loginBtn.setOnAction(e -> {
			String email = userEmail.getText();
			String pass = password.getText();
			User loggedInUser = userController.login(email, pass);
			
			// Di dalam loginBtn.setOnAction
			if(loggedInUser != null) {
			    String role = loggedInUser.getUserRole();
			    
			    if(role.equals("Admin")) {
			        new AdminView(stage);
			    } else if (role.equals("Customer")) {
			        new CustomerView(stage);
			    } else if (role.equals("Receptionist")) {
			        new ReceptionistView(stage);
			    } else if (role.equals("Laundry Staff")) {
			        new LaundryStaffView(stage);
			    }
			}
		});
	}
}
