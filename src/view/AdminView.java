package view;

import java.time.LocalDate;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;

// Import Model & Controller
import model.User;
import model.Service;
import controller.UserController;
import controller.ServiceController;

public class AdminView {
    UserController userController = new UserController();
    ServiceController serviceController = new ServiceController();
    Stage stage;
    Scene scene;
    BorderPane bp;
    GridPane addEmployeeGrid;
    TableView<User> table;

    TextField userName, userEmail;
    PasswordField password, confirmPassword;
    RadioButton maleRadio, femaleRadio;
    ToggleGroup genderGroup;
    DatePicker dobPicker;

    MenuBar menuBar;
    Menu menu;
    MenuItem AddEmployeeItem, ViewAllEmployeeItem;
    Label formLbl, nameLbl, emailLbl, passwordLbl, confirmLbl, genderLbl, doLbl;
    Button AddBtn;

    public AdminView(Stage stage) {
        this.stage = stage;
        initiate();
    }

    private void initiate() {
        bp = new BorderPane();
        scene = new Scene(bp, 1000, 700);
        
        setupMenu();
        setupServiceManagement();
        
        stage.setScene(scene);
        stage.setTitle("GoVlash Laundry - Admin Dashboard");
        stage.show();
    }
    
    private void setupMenu() {
        menuBar = new MenuBar();
        
        menu = new Menu("Navigation");
        AddEmployeeItem = new MenuItem("Add Employee");
        ViewAllEmployeeItem = new MenuItem("View Employees");
        
        menu.getItems().addAll(AddEmployeeItem, ViewAllEmployeeItem);
        menuBar.getMenus().add(menu);
        
        bp.setTop(menuBar);
    }
    
    // ==================== SERVICE MANAGEMENT UI ====================
    private void setupServiceManagement() {
        TabPane tabPane = new TabPane();
        
        // Tab 1: Service Management
        Tab serviceTab = new Tab("Service Management");
        serviceTab.setClosable(false);
        
        VBox serviceLayout = new VBox(15);
        serviceLayout.setPadding(new Insets(20));
        serviceLayout.setStyle("-fx-background-color: #f5f5f5;");
        
        // Title
        Label titleLabel = new Label("📦 SERVICE MANAGEMENT");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
        
        // Form Panel
        VBox formPanel = new VBox(10);
        formPanel.setPadding(new Insets(15));
        formPanel.setStyle("-fx-background-color: white; -fx-border-color: #ddd; -fx-border-radius: 5;");
        
        Label formTitle = new Label("Add / Update Service");
        formTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        
        GridPane serviceForm = new GridPane();
        serviceForm.setHgap(15);
        serviceForm.setVgap(10);
        serviceForm.setPadding(new Insets(10, 0, 10, 0));
        
        // Form Fields
        Label nameLbl = new Label("Service Name:");
        nameLbl.setStyle("-fx-font-weight: bold;");
        TextField nameField = new TextField();
        nameField.setPromptText("Enter service name (max 50 characters)");
        nameField.setPrefWidth(300);
        
        Label descLbl = new Label("Description:");
        descLbl.setStyle("-fx-font-weight: bold;");
        TextArea descArea = new TextArea();
        descArea.setPromptText("Enter description (max 250 characters)");
        descArea.setPrefRowCount(3);
        descArea.setPrefWidth(300);
        
        Label priceLbl = new Label("Price (Rp):");
        priceLbl.setStyle("-fx-font-weight: bold;");
        TextField priceField = new TextField();
        priceField.setPromptText("e.g., 15000");
        priceField.setPrefWidth(150);
        
        Label durationLbl = new Label("Duration (days):");
        durationLbl.setStyle("-fx-font-weight: bold;");
        TextField durationField = new TextField();
        durationField.setPromptText("1-30 days");
        durationField.setPrefWidth(100);
        
        // Add fields to form grid
        serviceForm.add(nameLbl, 0, 0);
        serviceForm.add(nameField, 1, 0);
        serviceForm.add(descLbl, 0, 1);
        serviceForm.add(descArea, 1, 1);
        serviceForm.add(priceLbl, 0, 2);
        serviceForm.add(priceField, 1, 2);
        serviceForm.add(durationLbl, 2, 2);
        serviceForm.add(durationField, 3, 2);
        
        // Buttons
        HBox buttonBox = new HBox(15);
        Button addBtn = new Button("➕ Add Service");
        addBtn.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-weight: bold;");
        
        Button updateBtn = new Button("✏️ Update Selected");
        updateBtn.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; -fx-font-weight: bold;");
        
        Button deleteBtn = new Button("🗑️ Delete Selected");
        deleteBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");
        
        Button clearBtn = new Button("Clear Form");
        clearBtn.setStyle("-fx-background-color: #95a5a6; -fx-text-fill: white;");
        
        buttonBox.getChildren().addAll(addBtn, updateBtn, deleteBtn, clearBtn);
        
        // Service Table
        Label tableTitle = new Label("Service List");
        tableTitle.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 10 0 5 0;");
        
        TableView<Service> serviceTable = new TableView<>();
        serviceTable.setPrefHeight(300);
        
        // Table Columns
        TableColumn<Service, Integer> idCol = new TableColumn<>("ID");
        idCol.setMinWidth(50);
        idCol.setCellValueFactory(new PropertyValueFactory<>("serviceId"));
        
        TableColumn<Service, String> nameCol = new TableColumn<>("Service Name");
        nameCol.setMinWidth(150);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        
        TableColumn<Service, String> descCol = new TableColumn<>("Description");
        descCol.setMinWidth(200);
        descCol.setCellValueFactory(new PropertyValueFactory<>("serviceDescription"));
        
        TableColumn<Service, Double> priceCol = new TableColumn<>("Price");
        priceCol.setMinWidth(100);
        priceCol.setCellValueFactory(new PropertyValueFactory<>("servicePrice"));
        priceCol.setCellFactory(col -> new TableCell<Service, Double>() {
            @Override
            protected void updateItem(Double price, boolean empty) {
                super.updateItem(price, empty);
                if (empty || price == null) {
                    setText(null);
                } else {
                    setText(String.format("Rp%,.0f", price));
                    setStyle("-fx-alignment: CENTER-RIGHT;");
                }
            }
        });
        
        TableColumn<Service, Integer> durationCol = new TableColumn<>("Duration");
        durationCol.setMinWidth(80);
        durationCol.setCellValueFactory(new PropertyValueFactory<>("serviceDuration"));
        durationCol.setCellFactory(col -> new TableCell<Service, Integer>() {
            @Override
            protected void updateItem(Integer days, boolean empty) {
                super.updateItem(days, empty);
                if (empty || days == null) {
                    setText(null);
                } else {
                    setText(days + " day" + (days > 1 ? "s" : ""));
                    setStyle("-fx-alignment: CENTER;");
                }
            }
        });
        
        serviceTable.getColumns().addAll(idCol, nameCol, descCol, priceCol, durationCol);
        
        // Load initial data
        refreshServiceTable(serviceTable);
        
        // ========== EVENT HANDLERS ==========
        
        // Add Button
        addBtn.setOnAction(e -> {
            if (!validateForm(nameField, descArea, priceField, durationField)) {
                return;
            }
            
            try {
                String name = nameField.getText().trim();
                String desc = descArea.getText().trim();
                double price = Double.parseDouble(priceField.getText());
                int duration = Integer.parseInt(durationField.getText());
                
                Service newService = new Service(name, desc, price, duration);
                
                if (serviceController.addService(newService)) {
                    showAlert("Success", "✅ Service added successfully!", Alert.AlertType.INFORMATION);
                    refreshServiceTable(serviceTable);
                    clearForm(nameField, descArea, priceField, durationField);
                } else {
                    showAlert("Error", "❌ Failed to add service. Please check the data.", Alert.AlertType.ERROR);
                }
            } catch (NumberFormatException ex) {
                showAlert("Error", "❌ Please enter valid numbers for price and duration.", Alert.AlertType.ERROR);
            }
        });
        
        // Table Selection Handler
        serviceTable.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                nameField.setText(newVal.getServiceName());
                descArea.setText(newVal.getServiceDescription());
                priceField.setText(String.valueOf(newVal.getServicePrice()));
                durationField.setText(String.valueOf(newVal.getServiceDuration()));
            }
        });
        
        // Update Button
        updateBtn.setOnAction(e -> {
            Service selected = serviceTable.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showAlert("Error", "❌ Please select a service to update.", Alert.AlertType.ERROR);
                return;
            }
            
            if (!validateForm(nameField, descArea, priceField, durationField)) {
                return;
            }
            
            try {
                selected.setServiceName(nameField.getText().trim());
                selected.setServiceDescription(descArea.getText().trim());
                selected.setServicePrice(Double.parseDouble(priceField.getText()));
                selected.setServiceDuration(Integer.parseInt(durationField.getText()));
                
                if (serviceController.updateService(selected)) {
                    showAlert("Success", "✅ Service updated successfully!", Alert.AlertType.INFORMATION);
                    refreshServiceTable(serviceTable);
                } else {
                    showAlert("Error", "❌ Failed to update service.", Alert.AlertType.ERROR);
                }
            } catch (NumberFormatException ex) {
                showAlert("Error", "❌ Please enter valid numbers for price and duration.", Alert.AlertType.ERROR);
            }
        });
        
        // Delete Button
        deleteBtn.setOnAction(e -> {
            Service selected = serviceTable.getSelectionModel().getSelectedItem();
            if (selected == null) {
                showAlert("Error", "❌ Please select a service to delete.", Alert.AlertType.ERROR);
                return;
            }
            
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirm Delete");
            confirm.setHeaderText("Delete Service");
            confirm.setContentText("Are you sure you want to delete:\n" + 
                                 selected.getServiceName() + "?\n\nThis action cannot be undone.");
            
            confirm.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    if (serviceController.deleteService(selected.getServiceId())) {
                        showAlert("Success", "✅ Service deleted successfully!", Alert.AlertType.INFORMATION);
                        refreshServiceTable(serviceTable);
                        clearForm(nameField, descArea, priceField, durationField);
                    }
                }
            });
        });
        
        // Clear Button
        clearBtn.setOnAction(e -> {
            clearForm(nameField, descArea, priceField, durationField);
            serviceTable.getSelectionModel().clearSelection();
        });
        
        // Refresh Button (optional)
        Button refreshBtn = new Button("🔄 Refresh");
        refreshBtn.setOnAction(e -> refreshServiceTable(serviceTable));
        
        HBox topBox = new HBox(10);
        topBox.getChildren().addAll(titleLabel, refreshBtn);
        
        // Assemble Layout
        formPanel.getChildren().addAll(formTitle, serviceForm, buttonBox);
        
        VBox tableBox = new VBox(10);
        tableBox.getChildren().addAll(tableTitle, serviceTable);
        
        serviceLayout.getChildren().addAll(
            topBox,
            new Separator(),
            formPanel,
            new Separator(),
            tableBox
        );
        
        serviceTab.setContent(serviceLayout);
        
        // Tab 2: Employee Management (Placeholder)
        Tab employeeTab = new Tab("Employee Management");
        employeeTab.setClosable(false);
        employeeTab.setContent(new Label("Employee Management - Coming Soon"));
        
        tabPane.getTabs().addAll(serviceTab, employeeTab);
        bp.setCenter(tabPane);
    }
    
    // ==================== HELPER METHODS ====================
    
    private void refreshServiceTable(TableView<Service> table) {
        table.getItems().clear();
        try {
            var rs = serviceController.getAllServices();
            ObservableList<Service> serviceList = FXCollections.observableArrayList();
            
            while (rs.next()) {
                Service service = new Service(
                    rs.getInt("serviceId"),
                    rs.getString("serviceName"),
                    rs.getString("serviceDescription"),
                    rs.getDouble("servicePrice"),
                    rs.getInt("serviceDuration")
                );
                serviceList.add(service);
            }
            
            table.setItems(serviceList);
        } catch (Exception e) {
            showAlert("Error", "❌ Failed to load services: " + e.getMessage(), Alert.AlertType.ERROR);
            e.printStackTrace();
        }
    }
    
    private boolean validateForm(TextField name, TextArea desc, TextField price, TextField duration) {
        // Validate Name
        if (name.getText().trim().isEmpty()) {
            showAlert("Validation Error", "❌ Service name cannot be empty.", Alert.AlertType.ERROR);
            name.requestFocus();
            return false;
        }
        if (name.getText().trim().length() > 50) {
            showAlert("Validation Error", "❌ Service name cannot exceed 50 characters.", Alert.AlertType.ERROR);
            name.requestFocus();
            return false;
        }
        
        // Validate Description
        if (desc.getText().trim().isEmpty()) {
            showAlert("Validation Error", "❌ Description cannot be empty.", Alert.AlertType.ERROR);
            desc.requestFocus();
            return false;
        }
        if (desc.getText().trim().length() > 250) {
            showAlert("Validation Error", "❌ Description cannot exceed 250 characters.", Alert.AlertType.ERROR);
            desc.requestFocus();
            return false;
        }
        
        // Validate Price
        try {
            double priceValue = Double.parseDouble(price.getText());
            if (priceValue <= 0) {
                showAlert("Validation Error", "❌ Price must be greater than 0.", Alert.AlertType.ERROR);
                price.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert("Validation Error", "❌ Please enter a valid number for price.", Alert.AlertType.ERROR);
            price.requestFocus();
            return false;
        }
        
        // Validate Duration
        try {
            int durationValue = Integer.parseInt(duration.getText());
            if (durationValue < 1 || durationValue > 30) {
                showAlert("Validation Error", "❌ Duration must be between 1 and 30 days.", Alert.AlertType.ERROR);
                duration.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert("Validation Error", "❌ Please enter a valid number for duration (1-30).", Alert.AlertType.ERROR);
            duration.requestFocus();
            return false;
        }
        
        return true;
    }
    
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private void clearForm(TextField name, TextArea desc, TextField price, TextField duration) {
        name.clear();
        desc.clear();
        price.clear();
        duration.clear();
    }
}