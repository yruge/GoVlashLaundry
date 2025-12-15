package view;

import controller.NotificationController;
import controller.TransactionController;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import model.Transaction;

public class AllTransactionView {
    private TransactionController transactionController = new TransactionController();
    private NotificationController notificationController = new NotificationController(); // Tambahan

    public VBox getView() {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        
        Label title = new Label("All Transactions Report");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        
        TableView<Transaction> table = new TableView<>();
        
        TableColumn<Transaction, Integer> colID = new TableColumn<>("ID");
        colID.setCellValueFactory(new PropertyValueFactory<>("transactionID"));

        TableColumn<Transaction, Integer> colCust = new TableColumn<>("Cust ID");
        colCust.setCellValueFactory(new PropertyValueFactory<>("customerID"));

        TableColumn<Transaction, String> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(new PropertyValueFactory<>("transactionStatus"));

        // --- KOLOM AKSI (NOTIFY) ---
        TableColumn<Transaction, Void> colAction = new TableColumn<>("Action");
        Callback<TableColumn<Transaction, Void>, TableCell<Transaction, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Transaction, Void> call(final TableColumn<Transaction, Void> param) {
                return new TableCell<>() {
                    private final Button btnNotify = new Button("Notify Customer");

                    {
                        btnNotify.setOnAction(event -> {
                            Transaction tr = getTableView().getItems().get(getIndex());
                            // Sesuai Diagram: Send Notification
                            String message = "Your order (ID: " + tr.getTransactionID() + ") is finished and ready for pickup. Thank you!";
                            notificationController.sendNotification(tr.getCustomerID(), message);
                            
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setContentText("Notification sent to Customer ID: " + tr.getCustomerID());
                            alert.show();
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            Transaction tr = getTableView().getItems().get(getIndex());
                            // Tombol hanya muncul jika status 'Finished'
                            if ("Finished".equalsIgnoreCase(tr.getTransactionStatus())) {
                                setGraphic(btnNotify);
                            } else {
                                setGraphic(null);
                            }
                        }
                    }
                };
            }
        };
        colAction.setCellFactory(cellFactory);

        table.getColumns().addAll(colID, colCust, colStatus, colAction);
        
        // Filter Buttons
        Button btnShowAll = new Button("Show All");
        Button btnShowFinished = new Button("Show Finished Only"); // Sesuai Diagram

        btnShowAll.setOnAction(e -> table.getItems().setAll(transactionController.getAllTransactions()));
        btnShowFinished.setOnAction(e -> table.getItems().setAll(transactionController.getTransactionsByStatus("Finished")));

        // Load Default
        table.getItems().setAll(transactionController.getAllTransactions());

        layout.getChildren().addAll(title, new HBox(10, btnShowAll, btnShowFinished), table);
        return layout;
    }
}