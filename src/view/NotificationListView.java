package view;

import controller.NotificationController;
import controller.UserController;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import model.Notification;

import java.util.List;

public class NotificationListView {
    private NotificationController notificationController = new NotificationController();
    private TableView<Notification> table;
    private VBox mainLayout;

    public VBox getView() {
        mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(15));
        
        Label title = new Label("My Notifications");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // Ambil data
        int currentUserID = UserController.getCurrentUser().getUserID();
        List<Notification> notifs = notificationController.getNotificationsByRecipientID(currentUserID);

        // --- VALIDASI DATA KOSONG (Sesuai Diagram) ---
        if (notifs.isEmpty()) {
            Label emptyMsg = new Label("No notifications available.");
            mainLayout.getChildren().addAll(title, emptyMsg);
            return mainLayout;
        }

        table = new TableView<>();
        
        TableColumn<Notification, String> colDate = new TableColumn<>("Date");
        colDate.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
        
        TableColumn<Notification, String> colMsg = new TableColumn<>("Message");
        colMsg.setCellValueFactory(new PropertyValueFactory<>("notificationMessage"));
        
        TableColumn<Notification, Boolean> colRead = new TableColumn<>("Status");
        colRead.setCellValueFactory(new PropertyValueFactory<>("read"));
        colRead.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Boolean isRead, boolean empty) {
                super.updateItem(isRead, empty);
                if (empty || isRead == null) setText(null);
                else setText(isRead ? "Read" : "Unread");
            }
        });

        // --- KOLOM AKSI (VIEW DETAIL) ---
        TableColumn<Notification, Void> colAction = new TableColumn<>("Action");
        Callback<TableColumn<Notification, Void>, TableCell<Notification, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Notification, Void> call(final TableColumn<Notification, Void> param) {
                return new TableCell<>() {
                    private final Button btnView = new Button("View Detail");
                    {
                        btnView.setOnAction(event -> {
                            Notification n = getTableView().getItems().get(getIndex());
                            // Buka Halaman Detail (Ganti scene center)
                            // Kita pakai trik simple: replace content VBox ini
                            showDetail(n); 
                        });
                    }
                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) setGraphic(null);
                        else setGraphic(btnView);
                    }
                };
            }
        };
        colAction.setCellFactory(cellFactory);

        table.getColumns().addAll(colDate, colMsg, colRead, colAction);
        table.getItems().setAll(notifs);

        mainLayout.getChildren().addAll(title, table);
        return mainLayout;
    }

    // --- FITUR DETAIL (Sesuai Diagram: ViewNotificationDetail & DeleteNotification) ---
    private void showDetail(Notification n) {
        // 1. Mark as Read (Logic Sesuai Diagram)
        if (!n.isRead()) {
            notificationController.markAsRead(n.getNotificationID());
        }

        // 2. Tampilan Detail Page
        VBox detailLayout = new VBox(15);
        detailLayout.setPadding(new Insets(20));

        Label lblTitle = new Label("Notification Detail");
        lblTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label lblDate = new Label("Date: " + n.getCreatedAt());
        Label lblMsg = new Label("Message: " + n.getNotificationMessage());
        lblMsg.setWrapText(true);

        Button btnDelete = new Button("Delete Notification");
        Button btnBack = new Button("Back to List");

        btnDelete.setStyle("-fx-background-color: #ffcccc; -fx-text-fill: red;");
        
        // Aksi Delete
        btnDelete.setOnAction(e -> {
            notificationController.deleteNotification(n.getNotificationID());
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Notification deleted!");
            alert.showAndWait();
            
            // Refresh balik ke list
            mainLayout.getChildren().clear();
            mainLayout.getChildren().add(new NotificationListView().getView()); 
        });

        btnBack.setOnAction(e -> {
            mainLayout.getChildren().clear();
            mainLayout.getChildren().add(new NotificationListView().getView());
        });

        detailLayout.getChildren().addAll(lblTitle, lblDate, lblMsg, btnDelete, btnBack);
        
        // Ganti tampilan VBox utama dengan detail
        mainLayout.getChildren().clear();
        mainLayout.getChildren().add(detailLayout);
    }
}