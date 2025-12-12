package view;

import controller.AuthHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;

public class LoginView {
    private AuthHandler auth = new AuthHandler();

    public void show(Stage stage) {
        Label lEmail = new Label("Email");
        TextField tfEmail = new TextField();
        Label lPass = new Label("Password");
        PasswordField pf = new PasswordField();
        Button bLogin = new Button("Login");
        Button bRegister = new Button("Register (Customer)");
        Label msg = new Label();

        bLogin.setOnAction(e -> {
            User u = auth.login(tfEmail.getText().trim(), pf.getText());
            if (u == null) {
                msg.setText("Invalid credentials");
                return;
            }
            switch (u.getRole()) {
                case "Admin": new AdminMenuView(u).show(stage); break;
                case "Customer": new CustomerMenuView(u).show(stage); break;
                case "Receptionist": new ReceptionistMenuView(u).show(stage); break;
                case "Laundry Staff": new LaundryMenuView(u).show(stage); break;
                default: new CustomerMenuView(u).show(stage);
            }
        });

        bRegister.setOnAction(e -> new RegisterView().show(stage));

        VBox root = new VBox(8, lEmail, tfEmail, lPass, pf, bLogin, bRegister, msg);
        root.setPadding(new Insets(16));
        stage.setScene(new Scene(root, 360, 300));
        stage.setTitle("GoVlash - Login");
        stage.show();
    }
}
