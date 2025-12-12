package view;

import model.Notification;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class NotificationDetailView {
    private Notification n;
    public NotificationDetailView(Notification n) { this.n = n; }

    public void show(Stage stage) {
        Label l = new Label(n.getMessage());
        Label d = new Label("At: " + n.getCreatedAt());
        VBox root = new VBox(8, l, d);
        root.setPadding(new Insets(12));
        stage.setScene(new Scene(root, 400, 200));
        stage.setTitle("Notification Detail");
        stage.show();
    }
}
