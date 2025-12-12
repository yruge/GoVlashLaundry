package view;

import model.User;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ReceptionistMenuView {
    private User user;
    public ReceptionistMenuView(User u) { this.user = u; }

    public void show(Stage stage) {
        Button bPending = new Button("View Pending Transactions");
        Button bLogout = new Button("Logout");

        bPending.setOnAction(e -> new TransactionListView("Pending").show(stage));
        bLogout.setOnAction(e -> new LoginView().show(stage));

        VBox root = new VBox(10, bPending, bLogout);
        root.setPadding(new Insets(12));
        stage.setScene(new Scene(root, 360, 200));
        stage.setTitle("Receptionist - " + user.getUsername());
        stage.show();
    }
}
