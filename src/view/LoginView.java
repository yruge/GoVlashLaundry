package view;

import controller.UserController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;

public class LoginView {
    private Stage stage;
    private UserController userController = new UserController();

    public LoginView(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        Label title = new Label("GoVlash Laundry Login");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.setMaxWidth(300);

        PasswordField passField = new PasswordField();
        passField.setPromptText("Password");
        passField.setMaxWidth(300);

        Button btnLogin = new Button("Login");
        Hyperlink linkReg = new Hyperlink("Register as Customer");

        Label lblError = new Label();
        lblError.setStyle("-fx-text-fill: red;");

        btnLogin.setOnAction(e -> {
            String email = emailField.getText();
            String pass = passField.getText();
            
            User user = userController.login(email, pass);
            
            if (user != null) {
                // Redirect based on Role
                String role = user.getUserRole();
                if(role.equals("Admin")) new AdminView(stage);
                else if(role.equals("Customer")) new CustomerView(stage);
                else if(role.equals("Receptionist")) new ReceptionistView(stage);
                else if(role.equals("Laundry Staff")) new LaundryStaffView(stage);
            } else {
                lblError.setText("Invalid Email or Password");
            }
        });

        linkReg.setOnAction(e -> new RegisterView(stage).show());

        root.getChildren().addAll(title, emailField, passField, btnLogin, linkReg, lblError);
        
        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("GoVlash Laundry - Login");
        stage.show();
    }
}