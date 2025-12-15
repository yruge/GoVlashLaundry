package view;

import controller.ServiceController;
import controller.TransactionController;
import controller.NotificationController;
import controller.UserController;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Service;
import model.Transaction;
import model.Notification;

public class CustomerView {
    Stage stage;
    BorderPane bp;
    ServiceController srvController = new ServiceController();
    TransactionController trController = new TransactionController();
    NotificationController notifController = new NotificationController();

    public CustomerView(Stage stage) {
        this.stage = stage;
        bp = new BorderPane();
        
        MenuBar mb = new MenuBar();
        Menu m = new Menu("Menu");
        MenuItem mBuy = new MenuItem("Buy Service");
        MenuItem mHist = new MenuItem("History");
        MenuItem mNotif = new MenuItem("Notifications");
        MenuItem mLogout = new MenuItem("Logout");
        
        m.getItems().addAll(mBuy, mHist, mNotif, new SeparatorMenuItem(), mLogout);
        mb.getMenus().add(m);
        bp.setTop(mb);

        mBuy.setOnAction(e -> showBuy());
        mHist.setOnAction(e -> showHistory());
        mNotif.setOnAction(e -> showNotifications());
        mLogout.setOnAction(e -> new LoginView(stage).show());

        showBuy();

        stage.setScene(new Scene(bp, 800, 600));
        stage.setTitle("Customer Dashboard");
    }

    private void showBuy() {
        VBox layout = new VBox(15); layout.setPadding(new Insets(20));
        
        ComboBox<Service> cmbService = new ComboBox<>();
        cmbService.setItems(FXCollections.observableArrayList(srvController.getAllServices()));
        cmbService.setPromptText("Select Service");

        TextField txtWeight = new TextField(); txtWeight.setPromptText("Weight (Kg)");
        TextField txtNotes = new TextField(); txtNotes.setPromptText("Notes");
        
        Button btnOrder = new Button("Place Order");
        
        btnOrder.setOnAction(e -> {
            Service s = cmbService.getValue();
            if(s != null) {
                trController.order(s.getServiceID(), UserController.getCurrentUser().getUserID(), txtWeight.getText(), txtNotes.getText());
            }
        });

        layout.getChildren().addAll(new Label("Order Laundry"), cmbService, txtWeight, txtNotes, btnOrder);
        bp.setCenter(layout);
    }

    private void showHistory() {
        TableView<Transaction> table = new TableView<>();
        TableColumn<Transaction, String> dateCol = new TableColumn<>("Date"); dateCol.setCellValueFactory(new PropertyValueFactory<>("transactionDate"));
        TableColumn<Transaction, String> srvCol = new TableColumn<>("Service"); srvCol.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        TableColumn<Transaction, String> statCol = new TableColumn<>("Status"); statCol.setCellValueFactory(new PropertyValueFactory<>("transactionStatus"));
        table.getColumns().addAll(dateCol, srvCol, statCol);
        
        table.setItems(FXCollections.observableArrayList(trController.getCustomerHistory(UserController.getCurrentUser().getUserID())));
        
        VBox layout = new VBox(10, new Label("My Transaction History"), table);
        layout.setPadding(new Insets(20));
        bp.setCenter(layout);
    }

    private void showNotifications() {
        TableView<Notification> table = new TableView<>();
        TableColumn<Notification, String> msgCol = new TableColumn<>("Message"); msgCol.setCellValueFactory(new PropertyValueFactory<>("message"));
        TableColumn<Notification, Boolean> readCol = new TableColumn<>("Read"); readCol.setCellValueFactory(new PropertyValueFactory<>("read")); // pastikan di model methodnya isRead() / getRead()
        table.getColumns().addAll(msgCol, readCol);
        
        int myID = UserController.getCurrentUser().getUserID();
        table.setItems(FXCollections.observableArrayList(notifController.getMyNotifications(myID)));
        
        Button btnRead = new Button("Mark as Read (View)");
        Button btnDel = new Button("Delete");

        btnRead.setOnAction(e -> {
            Notification n = table.getSelectionModel().getSelectedItem();
            if(n!=null) {
                notifController.readNotification(n.getNotificationID());
                Alert a = new Alert(Alert.AlertType.INFORMATION); a.setContentText(n.getMessage()); a.showAndWait();
                table.setItems(FXCollections.observableArrayList(notifController.getMyNotifications(myID)));
            }
        });

        btnDel.setOnAction(e -> {
            Notification n = table.getSelectionModel().getSelectedItem();
            if(n!=null) {
                notifController.deleteNotification(n.getNotificationID());
                table.setItems(FXCollections.observableArrayList(notifController.getMyNotifications(myID)));
            }
        });

        VBox layout = new VBox(10, new Label("My Notifications"), table, new HBox(10, btnRead, btnDel));
        layout.setPadding(new Insets(20));
        bp.setCenter(layout);
    }
}