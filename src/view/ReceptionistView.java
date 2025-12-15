package view;

import controller.TransactionController;
import controller.UserController;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Transaction;
import model.User;

public class ReceptionistView {
    Stage stage;
    TransactionController trController = new TransactionController();
    UserController userController = new UserController();

    public ReceptionistView(Stage stage) {
        this.stage = stage;
        BorderPane bp = new BorderPane();
        
        MenuBar mb = new MenuBar();
        Menu m = new Menu("Account");
        MenuItem logout = new MenuItem("Logout");
        logout.setOnAction(e -> new LoginView(stage).show());
        m.getItems().add(logout);
        mb.getMenus().add(m);
        bp.setTop(mb);

        VBox layout = new VBox(15); layout.setPadding(new Insets(20));

        Label lbl = new Label("Pending Transactions (Assign to Staff)");
        lbl.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TableView<Transaction> table = new TableView<>();
        TableColumn<Transaction, Integer> idCol = new TableColumn<>("ID"); idCol.setCellValueFactory(new PropertyValueFactory<>("transactionID"));
        TableColumn<Transaction, String> srvCol = new TableColumn<>("Service"); srvCol.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        TableColumn<Transaction, String> dateCol = new TableColumn<>("Date"); dateCol.setCellValueFactory(new PropertyValueFactory<>("transactionDate"));
        table.getColumns().addAll(idCol, srvCol, dateCol);
        
        table.setItems(FXCollections.observableArrayList(trController.getPendingTransactions()));

        ComboBox<User> staffCombo = new ComboBox<>();
        staffCombo.setPromptText("Select Laundry Staff");
        
        // Filter hanya Laundry Staff
        for(User u : userController.getAllEmployees()) {
            if("Laundry Staff".equals(u.getUserRole())) staffCombo.getItems().add(u);
        }

        Button btnAssign = new Button("Assign Order");
        btnAssign.setOnAction(e -> {
            Transaction t = table.getSelectionModel().getSelectedItem();
            User staff = staffCombo.getValue();
            if(t != null && staff != null) {
                trController.assignOrder(t.getTransactionID(), UserController.getCurrentUser().getUserID(), staff.getUserID());
                // Refresh
                table.setItems(FXCollections.observableArrayList(trController.getPendingTransactions()));
            } else {
                Alert a = new Alert(Alert.AlertType.WARNING); a.setContentText("Select Transaction & Staff first."); a.show();
            }
        });

        layout.getChildren().addAll(lbl, table, staffCombo, btnAssign);
        bp.setCenter(layout);

        stage.setScene(new Scene(bp, 800, 600));
        stage.setTitle("Receptionist Dashboard");
    }
}