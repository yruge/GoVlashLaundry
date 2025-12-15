package view;

import controller.TransactionController;
import controller.UserController;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Transaction;
import model.User;

import java.util.List;

public class ReceptionistTaskView {
    private TransactionController transactionController = new TransactionController();
    private UserController userController = new UserController();
    private TableView<Transaction> table;

    public VBox getView() {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        Label title = new Label("Pending Transactions");
        title.setStyle("-fx-font-size: 20px;");

        table = new TableView<>();
        // Kolom Transaction ID
        TableColumn<Transaction, Integer> colID = new TableColumn<>("ID");
        colID.setCellValueFactory(new PropertyValueFactory<>("transactionID"));
        
        // Kolom Status
        TableColumn<Transaction, String> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(new PropertyValueFactory<>("transactionStatus"));

        table.getColumns().addAll(colID, colStatus);
        refreshTable();

        // Bagian Assign
        ComboBox<User> cmbStaff = new ComboBox<>();
        // Filter user yang rolenya "Laundry Staff" saja untuk dimasukkan ke ComboBox
        List<User> allEmployees = userController.getAllEmployees();
        for(User u : allEmployees) {
            if(u.getUserRole().equals("Laundry Staff")) {
                cmbStaff.getItems().add(u);
            }
        }
        
        // Agar ComboBox menampilkan Nama, bukan object ID
        cmbStaff.setCellFactory(lv -> new ListCell<User>() {
            @Override
            protected void updateItem(User item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getUserName());
            }
        });
        cmbStaff.setButtonCell(new ListCell<User>() {
            @Override
            protected void updateItem(User item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getUserName());
            }
        });

        Button btnAssign = new Button("Assign to Staff");
        Label lblMsg = new Label();

        btnAssign.setOnAction(e -> {
            Transaction selectedTr = table.getSelectionModel().getSelectedItem();
            User selectedStaff = cmbStaff.getValue();

            if (selectedTr != null && selectedStaff != null) {
                int receptionistID = UserController.getCurrentUser().getUserID();
                transactionController.assignOrderToLaundryStaff(selectedTr.getTransactionID(), receptionistID, selectedStaff.getUserID());
                
                // Update status juga agar hilang dari list Pending
                transactionController.updateTransactionStatus(selectedTr.getTransactionID(), "In Progress"); // Asumsi status berubah
                
                lblMsg.setText("Order assigned successfully!");
                refreshTable();
            } else {
                lblMsg.setText("Select a transaction and a staff.");
            }
        });

        layout.getChildren().addAll(title, table, new Label("Select Laundry Staff:"), new HBox(10, cmbStaff, btnAssign), lblMsg);
        return layout;
    }

    private void refreshTable() {
        table.getItems().setAll(transactionController.getTransactionsByStatus("Pending"));
    }
}