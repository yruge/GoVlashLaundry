package view;

import java.time.LocalDate;
import java.util.ArrayList;

import controller.UserController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.User;

public class AdminView {
	UserController userController = new UserController();
	Stage stage;
	Scene scene;
	BorderPane bp;
	GridPane addEmployeeGrid;
//	TableView<User> table;
	
	TextField userName, userEmail;
	PasswordField password, confirmPassword;	
	RadioButton maleRadio, femaleRadio;
	ToggleGroup genderGroup;
	DatePicker dobPicker;
	
	MenuBar menuBar;
	Menu menu;
	MenuItem AddEmployeeItem, ViewAllEmployeeItem;
	Label formLbl, nameLbl, emailLbl, passwordLbl, confirmLbl, genderLbl, dobLbl;
	Button AddBtn;

	public AdminView(Stage stage) {
		this.stage = stage;
		
		initiate();
		bp.setTop(menuBar);
		addEmployeeForm();
		Scene scene = new Scene(bp, 800, 500);
		stage.setScene(scene);
		stage.setTitle("Admin Page");
		stage.show();
	}
	
	public void viewAllEmployeeForm() {
		// 1. Create the Table
	    TableView<User> table = new TableView<>();

	    // 2. ID Column (Integer)
	    TableColumn<User, Integer> idColumn = new TableColumn<>("ID");
	    idColumn.setCellValueFactory(new PropertyValueFactory<User, Integer>("userID"));
	    idColumn.setMinWidth(100); 

	    // 3. Name Column (String)
	    TableColumn<User, String> nameColumn = new TableColumn<>("Name");
	    nameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("userName"));
	    nameColumn.setMinWidth(200);
	    
	    TableColumn<User, String> dobColumn = new TableColumn<>("Date of Birth");
	    dobColumn.setCellValueFactory(new PropertyValueFactory<User, String>("userDOB"));
	    dobColumn.setMinWidth(100);

	    // 4. Email Column (String)
	    TableColumn<User, String> emailColumn = new TableColumn<>("Email");
	    emailColumn.setCellValueFactory(new PropertyValueFactory<User, String>("userEmail"));
	    emailColumn.setMinWidth(200);
	    
	    TableColumn<User, String> genderColumn = new TableColumn<>("Gender");
	    genderColumn.setCellValueFactory(new PropertyValueFactory<User, String>("userGender"));
	    genderColumn.setMinWidth(100);

	    // 5. Role Column (String)
	    TableColumn<User, String> roleColumn = new TableColumn<>("Role");
	    roleColumn.setCellValueFactory(new PropertyValueFactory<User, String>("userRole"));
	    roleColumn.setMinWidth(100);

	    // 6. Add all columns to the table
	    table.getColumns().addAll(idColumn, nameColumn, dobColumn, emailColumn, genderColumn, roleColumn);

	    // 7. Fill Data (Get from Controller -> Convert to ObservableList -> Set)
	    ArrayList<User> empList = userController.getAllEmployees();
	    ObservableList<User> data = FXCollections.observableArrayList(empList);
	    table.setItems(data);

	    // 8. Display it
	    bp.setCenter(table);
	}
	
	public void addEmployeeForm() {
		GridPane addEmployeeGrid = new GridPane();
		addEmployeeGrid.setAlignment(Pos.CENTER);
		addEmployeeGrid.setVgap(10);
		addEmployeeGrid.setHgap(10);
		addEmployeeGrid.setPadding(new Insets(20));

        // Column 0 (Labels), Column 1 (Inputs)
		addEmployeeGrid.add(formLbl, 0, 0);
        
		addEmployeeGrid.add(nameLbl, 0, 1);
		addEmployeeGrid.add(userName, 1, 1);
        
		addEmployeeGrid.add(emailLbl, 0, 2);
		addEmployeeGrid.add(userEmail, 1, 2);
        
		addEmployeeGrid.add(passwordLbl, 0, 3);
		addEmployeeGrid.add(password, 1, 3);
        
		addEmployeeGrid.add(confirmLbl, 0, 4);
		addEmployeeGrid.add(confirmPassword, 1, 4);
        
		addEmployeeGrid.add(genderLbl, 0, 5);
        // Add radios side-by-side
        HBox genderBox = new HBox(10, maleRadio, femaleRadio);
        addEmployeeGrid.add(genderBox, 1, 5);
        
        addEmployeeGrid.add(dobLbl, 0, 6);
        addEmployeeGrid.add(dobPicker, 1, 6);
        addEmployeeGrid.add(AddBtn, 1, 8);
        
        bp.setCenter(addEmployeeGrid);
	}
	
	private void initiate() {
		bp = new BorderPane();
		
		userName = new TextField();
		userEmail = new TextField();
		password = new PasswordField();
		confirmPassword = new PasswordField();
		
		maleRadio = new RadioButton("Male");
		femaleRadio = new RadioButton("Female");
		genderGroup = new ToggleGroup();
		maleRadio.setToggleGroup(genderGroup);
		femaleRadio.setToggleGroup(genderGroup);
		
		AddBtn = new Button("Add");
		
		
		dobPicker = new DatePicker();
		
		menuBar = new MenuBar();
		menu = new Menu("Page");
		AddEmployeeItem = new MenuItem("Add Employee");
		ViewAllEmployeeItem = new MenuItem("View All Employees");
		menu.getItems().addAll(AddEmployeeItem, ViewAllEmployeeItem);
		menuBar.getMenus().add(menu);
		
		
		formLbl = new Label("Add Employee");
		nameLbl = new Label("Username"); 
		emailLbl = new Label("Email");
		passwordLbl = new Label("Password");
		confirmLbl = new Label("Confirm Password");
		genderLbl = new Label("Gender");
		dobLbl = new Label("Date of Birth");
		
		AddEmployeeItem.setOnAction(e -> {
			addEmployeeForm(); 
		});
		
		ViewAllEmployeeItem.setOnAction(e -> {
			viewAllEmployeeForm();
		});
		
		AddBtn.setOnAction(e -> {
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
				    userController.addEmployee(name, mail, pass, conf, gender, dob, "Employee");// Call the method to show Register
			});
	}

}
