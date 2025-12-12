package view;

import model.User;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdminMenuView {
    private User admin;
    public AdminMenuView(User admin) { this.admin = admin; }

    public void show(Stage stage) {
        Button bServices = new Button("Manage Services");
        Button bUsers = new Button("Manage Employees (seed)");
        Button bLogout = new Button("Logout");

        bServices.setOnAction(e -> new ServiceManagementView().show(stage));
        bLogout.setOnAction(e -> new LoginView().show(stage));

        VBox root = new VBox(10, bServices, bUsers, bLogout);
        root.setPadding(new Insets(12));
        stage.setScene(new Scene(root, 400, 250));
        stage.setTitle("Admin - " + admin.getUsername());
        stage.show();
    }
}
