package view;

import controller.NotificationHandler;
import model.Notification;
import model.User;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class NotificationListView {
    private User user;
    private NotificationHandler nh = new NotificationHandler();

    public NotificationListView(User user) { this.user = user; }

    public void show(Stage stage) {
        ListView<Notification> lv = new ListView<>();
        refresh(lv);

        Button bOpen = new Button("Open");
        bOpen.setOnAction(e -> {
            Notification sel = lv.getSelectionModel().getSelectedItem();
            if (sel != null) {
                nh.markRead(sel.getNotificationId());
                new NotificationDetailView(sel).show(stage);
            }
        });

        VBox root = new VBox(8, new Label("Notifications for " + user.getUsername()), lv, bOpen);
        root.setPadding(new Insets(12));
        stage.setScene(new Scene(root, 540, 420));
        stage.setTitle("Notifications");
        stage.show();
    }

    private void refresh(ListView<Notification> lv) {
        List<Notification> list = nh.listFor(user.getUserId());
        lv.setItems(FXCollections.observableArrayList(list));
        lv.setCellFactory(p -> new ListCell<>() {
            @Override protected void updateItem(Notification n, boolean empty) {
                super.updateItem(n, empty);
                if (empty || n == null) setText(null);
                else setText((n.isRead() ? "[READ] " : "[NEW] ") + n.getMessage());
            }
        });
    }
}
