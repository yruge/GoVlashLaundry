package view;

import controller.UserController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;

public class RegisterView {
    private Stage stage;
    private UserController userController = new UserController();

    public RegisterView(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));

        Label title = new Label("Register Customer");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        TextField nameField = new TextField();
        TextField emailField = new TextField(); emailField.setPromptText("must end with @email.com");
        PasswordField passField = new PasswordField();
        PasswordField confField = new PasswordField();
        
        ToggleGroup tg = new ToggleGroup();
        RadioButton rbMale = new RadioButton("Male"); rbMale.setToggleGroup(tg);
        RadioButton rbFemale = new RadioButton("Female"); rbFemale.setToggleGroup(tg);
        HBox genderBox = new HBox(10, rbMale, rbFemale);
        
        DatePicker dobPicker = new DatePicker();

        grid.addRow(0, new Label("Name:"), nameField);
        grid.addRow(1, new Label("Email:"), emailField);
        grid.addRow(2, new Label("Password:"), passField);
        grid.addRow(3, new Label("Confirm:"), confField);
        grid.addRow(4, new Label("Gender:"), genderBox);
        grid.addRow(5, new Label("DOB:"), dobPicker);

        Button btnReg = new Button("Register");
        Button btnBack = new Button("Back to Login");

        btnReg.setOnAction(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String pass = passField.getText();
            String conf = confField.getText();
            String gender = rbMale.isSelected() ? "Male" : (rbFemale.isSelected() ? "Female" : null);
            LocalDate dob = dobPicker.getValue();

            // Panggil Controller (Validasi & Insert sudah di sana)
            // Catatan: Anda bisa menambahkan try-catch/if validation check di sini jika controller melempar error string
            userController.register(name, email, pass, conf, gender, dob, "Customer");
        });

        btnBack.setOnAction(e -> new LoginView(stage).show());

        root.getChildren().addAll(title, grid, new HBox(10, btnReg, btnBack));
        
        stage.setScene(new Scene(root, 800, 600));
        stage.setTitle("Register");
    }
}