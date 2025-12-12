package view;

import controller.AuthHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class RegisterView {
    private AuthHandler auth = new AuthHandler();

    public void show(Stage stage) {
        TextField tfName = new TextField();
        TextField tfEmail = new TextField();
        PasswordField pf = new PasswordField();
        PasswordField pf2 = new PasswordField();
        ChoiceBox<String> gender = new ChoiceBox<>();
        gender.getItems().addAll("Male", "Female");
        DatePicker dpDob = new DatePicker();
        Button bReg = new Button("Register");
        Label msg = new Label();

        bReg.setOnAction(e -> {
            String name = tfName.getText().trim();
            String email = tfEmail.getText().trim();
            String pass = pf.getText();
            String conf = pf2.getText();
            String g = gender.getValue();
            if (dpDob.getValue() == null) { msg.setText("Select date of birth"); return;}
            Date dob = Date.from(dpDob.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());

            String res = auth.registerCustomer(name, email, pass, conf, g, dob);
            msg.setText(res);
            if ("SUCCESS".equals(res)) new LoginView().show(stage);
        });

        VBox root = new VBox(8,
                new Label("Name"), tfName,
                new Label("Email"), tfEmail,
                new Label("Password"), pf,
                new Label("Confirm"), pf2,
                new Label("Gender"), gender,
                new Label("Date of Birth"), dpDob,
                bReg, msg);
        root.setPadding(new Insets(12));
        stage.setScene(new Scene(root, 380, 520));
        stage.setTitle("Register - Customer");
        stage.show();
    }
}
