package view;

import controller.NotificationHandler;
import model.User;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CustomerMenuView {
    private User user;
    private NotificationHandler nh = new NotificationHandler();

    public CustomerMenuView(User u) { this.user = u; }

    public void show(Stage stage) {
        Button bCreate = new Button("Create Transaction");
        Button bHistory = new Button("My Transactions");
        Button bNotif = new Button("Notifications");
        Button bLogout = new Button("Logout");

        bCreate.setOnAction(e -> new TransactionCreateView(user).show(stage));
        bNotif.setOnAction(e -> new NotificationListView(user).show(stage));
        bLogout.setOnAction(e -> new LoginView().show(stage));

        VBox root = new VBox(10, bCreate, bHistory, bNotif, bLogout);
        root.setPadding(new Insets(12));
        stage.setScene(new Scene(root, 380, 220));
        stage.setTitle("Customer - " + user.getUsername());
        stage.show();
    }
}
