package view;

import controller.ServiceHandler;
import controller.TransactionHandler;
import model.Service;
import model.User;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class TransactionCreateView {
    private User user;
    private TransactionHandler th = new TransactionHandler();
    private ServiceHandler sh = new ServiceHandler();

    public TransactionCreateView(User u) { this.user = u; }

    public void show(Stage stage) {
        ListView<Service> lv = new ListView<>();
        try {
            List<Service> services = sh.getAll();
            lv.setItems(FXCollections.observableArrayList(services));
            lv.setCellFactory(p -> new ListCell<>() {
                @Override protected void updateItem(Service s, boolean empty) {
                    super.updateItem(s, empty);
                    setText(empty || s==null? null : s.getName() + " - Rp " + s.getPrice());
                }
            });
        } catch (Exception e) { e.printStackTrace(); }

        TextField tfWeight = new TextField();
        TextArea taNotes = new TextArea();
        Button bCreate = new Button("Create Transaction");
        Label msg = new Label();

        bCreate.setOnAction(e -> {
            Service sel = lv.getSelectionModel().getSelectedItem();
            if (sel == null) { msg.setText("Select service"); return; }
            double weight = Double.parseDouble(tfWeight.getText());
            String res = th.createTransaction(sel.getServiceId(), user.getUserId(), weight, taNotes.getText());
            msg.setText(res);
            if ("SUCCESS".equals(res)) new CustomerMenuView(user).show(stage);
        });

        VBox root = new VBox(8, new Label("Select Service:"), lv, new Label("Weight (kg)"), tfWeight, new Label("Notes"), taNotes, bCreate, msg);
        root.setPadding(new Insets(12));
        stage.setScene(new Scene(root, 520, 600));
        stage.setTitle("Create Transaction - " + user.getUsername());
        stage.show();
    }
}
