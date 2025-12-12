package view;

import model.User;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LaundryMenuView {
    private User user;
    public LaundryMenuView(User u) { this.user = u; }

    public void show(Stage stage) {
        Button bAssigned = new Button("View Assigned / Finish");
        Button bLogout = new Button("Logout");

        bAssigned.setOnAction(e -> new TransactionListView("Pending").show(stage)); // for simplicity
        bLogout.setOnAction(e -> new LoginView().show(stage));

        VBox root = new VBox(10, bAssigned, bLogout);
        root.setPadding(new Insets(12));
        stage.setScene(new Scene(root, 360, 200));
        stage.setTitle("Laundry Staff - " + user.getUsername());
        stage.show();
    }
}
