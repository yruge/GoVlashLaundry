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

public class LaundryStaffView {
    Stage stage;
    TransactionController trController = new TransactionController();

    public LaundryStaffView(Stage stage) {
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

        Label lbl = new Label("My Assigned Tasks (In Progress)");
        lbl.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        TableView<Transaction> table = new TableView<>();
        TableColumn<Transaction, Integer> idCol = new TableColumn<>("ID"); idCol.setCellValueFactory(new PropertyValueFactory<>("transactionID"));
        TableColumn<Transaction, String> srvCol = new TableColumn<>("Service"); srvCol.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        TableColumn<Transaction, String> noteCol = new TableColumn<>("Notes"); noteCol.setCellValueFactory(new PropertyValueFactory<>("transactionNotes"));
        table.getColumns().addAll(idCol, srvCol, noteCol);

        int myID = UserController.getCurrentUser().getUserID();
        table.setItems(FXCollections.observableArrayList(trController.getStaffTasks(myID)));

        Button btnFinish = new Button("Mark as Finished");
        btnFinish.setOnAction(e -> {
            Transaction t = table.getSelectionModel().getSelectedItem();
            if(t != null) {
                trController.finishOrder(t.getTransactionID());
                table.setItems(FXCollections.observableArrayList(trController.getStaffTasks(myID))); // Refresh
                Alert a = new Alert(Alert.AlertType.INFORMATION); a.setContentText("Order Finished!"); a.show();
            }
        });

        layout.getChildren().addAll(lbl, table, btnFinish);
        bp.setCenter(layout);

        stage.setScene(new Scene(bp, 800, 600));
        stage.setTitle("Laundry Staff Dashboard");
    }
}