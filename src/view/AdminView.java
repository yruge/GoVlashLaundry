package view;

import controller.ServiceController;
import controller.TransactionController;
import controller.UserController;
import controller.NotificationController;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Service;
import model.Transaction;
import model.User;

public class AdminView {
    Stage stage;
    BorderPane bp;
    ServiceController serviceController = new ServiceController();
    UserController userController = new UserController();
    TransactionController trController = new TransactionController();
    NotificationController notifController = new NotificationController();

    public AdminView(Stage stage) {
        this.stage = stage;
        bp = new BorderPane();
        
        MenuBar mb = new MenuBar();
        Menu m = new Menu("Menu");
        MenuItem mService = new MenuItem("Manage Services");
        MenuItem mEmp = new MenuItem("Manage Employees");
        MenuItem mReport = new MenuItem("Transaction Report");
        MenuItem mLogout = new MenuItem("Logout");
        
        m.getItems().addAll(mService, mEmp, mReport, new SeparatorMenuItem(), mLogout);
        mb.getMenus().add(m);
        bp.setTop(mb);

        // Actions
        mService.setOnAction(e -> showManageServices());
        mEmp.setOnAction(e -> showManageEmployees());
        mReport.setOnAction(e -> showReport());
        mLogout.setOnAction(e -> new LoginView(stage).show());

        // Default Page
        showManageServices();

        stage.setScene(new Scene(bp, 900, 600));
        stage.setTitle("Admin Dashboard");
    }

    @SuppressWarnings("unchecked")
	private void showManageServices() {
        VBox layout = new VBox(10); layout.setPadding(new Insets(20));
        
        TextField name = new TextField(); name.setPromptText("Service Name");
        TextField desc = new TextField(); desc.setPromptText("Description");
        TextField price = new TextField(); price.setPromptText("Price");
        TextField dur = new TextField(); dur.setPromptText("Duration (Days)");
        Button btnAdd = new Button("Add Service");

        TableView<Service> table = new TableView<>();
        TableColumn<Service, String> colName = new TableColumn<>("Name"); colName.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        TableColumn<Service, Double> colPrice = new TableColumn<>("Price"); colPrice.setCellValueFactory(new PropertyValueFactory<>("servicePrice"));
        TableColumn<Service, Integer> colDur = new TableColumn<>("Duration"); colDur.setCellValueFactory(new PropertyValueFactory<>("serviceDuration"));
        table.getColumns().addAll(colName, colPrice, colDur);
        
        // Load Data
        table.setItems(FXCollections.observableArrayList(serviceController.getAllServices()));

        btnAdd.setOnAction(e -> {
            serviceController.addService(name.getText(), desc.getText(), price.getText(), dur.getText());
            table.setItems(FXCollections.observableArrayList(serviceController.getAllServices())); // Refresh
        });

        layout.getChildren().addAll(new Label("Manage Services"), name, desc, price, dur, btnAdd, table);
        bp.setCenter(layout);
    }

    @SuppressWarnings("unchecked")
	private void showManageEmployees() {
        VBox layout = new VBox(10); layout.setPadding(new Insets(20));

        TextField name = new TextField(); name.setPromptText("Name");
        TextField email = new TextField(); email.setPromptText("Email (@govlash.com)");
        PasswordField pass = new PasswordField(); pass.setPromptText("Password");
        
        ComboBox<String> roleCombo = new ComboBox<>();
        roleCombo.getItems().addAll("Admin", "Receptionist", "Laundry Staff");
        roleCombo.setPromptText("Select Role");
        
        ToggleGroup tg = new ToggleGroup();
        RadioButton rbMale = new RadioButton("Male"); rbMale.setToggleGroup(tg);
        RadioButton rbFemale = new RadioButton("Female"); rbFemale.setToggleGroup(tg);
        DatePicker dob = new DatePicker();

        Button btnAdd = new Button("Add Employee");
        
        TableView<User> table = new TableView<>();
        TableColumn<User, String> colName = new TableColumn<>("Name"); colName.setCellValueFactory(new PropertyValueFactory<>("userName"));
        TableColumn<User, String> colRole = new TableColumn<>("Role"); colRole.setCellValueFactory(new PropertyValueFactory<>("userRole"));
        table.getColumns().addAll(colName, colRole);
        table.setItems(FXCollections.observableArrayList(userController.getAllEmployees()));

        btnAdd.setOnAction(e -> {
            String gender = rbMale.isSelected() ? "Male" : "Female";
            userController.addEmployee(name.getText(), email.getText(), pass.getText(), pass.getText(), gender, dob.getValue(), roleCombo.getValue());
            table.setItems(FXCollections.observableArrayList(userController.getAllEmployees()));
        });

        layout.getChildren().addAll(new Label("Manage Employees"), name, email, pass, roleCombo, new javafx.scene.layout.HBox(10, rbMale, rbFemale), dob, btnAdd, table);
        bp.setCenter(layout);
    }

    @SuppressWarnings("unchecked")
	private void showReport() {
        VBox layout = new VBox(10); layout.setPadding(new Insets(20));

        CheckBox chkFinished = new CheckBox("Show Finished Only");
        
        TableView<Transaction> table = new TableView<>();
        TableColumn<Transaction, Integer> idCol = new TableColumn<>("ID"); idCol.setCellValueFactory(new PropertyValueFactory<>("transactionID"));
        TableColumn<Transaction, String> srvCol = new TableColumn<>("Service"); srvCol.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        TableColumn<Transaction, String> stCol = new TableColumn<>("Status"); stCol.setCellValueFactory(new PropertyValueFactory<>("transactionStatus"));
        table.getColumns().addAll(idCol, srvCol, stCol);
        
        table.setItems(FXCollections.observableArrayList(trController.getAllTransactions()));

        chkFinished.setOnAction(e -> {
            if(chkFinished.isSelected()) table.setItems(FXCollections.observableArrayList(trController.getFinishedTransactions()));
            else table.setItems(FXCollections.observableArrayList(trController.getAllTransactions()));
        });

        Button btnNotify = new Button("Notify Customer (If Finished)");
        btnNotify.setOnAction(e -> {
            Transaction t = table.getSelectionModel().getSelectedItem();
            if(t != null && "Finished".equals(t.getTransactionStatus())) {
                notifController.sendNotification(t.getCustomerID(), t.getTransactionID());
                Alert a = new Alert(Alert.AlertType.INFORMATION); a.setContentText("Notification Sent!"); a.show();
            } else {
                Alert a = new Alert(Alert.AlertType.WARNING); a.setContentText("Select a Finished transaction first."); a.show();
            }
        });

        layout.getChildren().addAll(new Label("Transaction Report"), chkFinished, table, btnNotify);
        bp.setCenter(layout);
    }
}