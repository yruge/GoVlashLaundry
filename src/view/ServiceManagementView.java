package view;

import controller.ServiceHandler;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.Service;

import java.util.List;

public class ServiceManagementView {
    private ServiceHandler handler = new ServiceHandler();

    public void show(Stage stage) {
        ListView<Service> listView = new ListView<>();
        refresh(listView);

        TextField tfName = new TextField();
        TextField tfPrice = new TextField();
        TextField tfDuration = new TextField();
        TextArea taDesc = new TextArea();
        Button bAdd = new Button("Add Service");
        Button bDelete = new Button("Delete Selected");
        Label msg = new Label();

        bAdd.setOnAction(e -> {
            String name = tfName.getText();
            String desc = taDesc.getText();
            double price = Double.parseDouble(tfPrice.getText());
            int duration = Integer.parseInt(tfDuration.getText());
            String res = handler.createService(name, desc, price, duration);
            msg.setText(res);
            refresh(listView);
        });

        bDelete.setOnAction(e -> {
            Service sel = listView.getSelectionModel().getSelectedItem();
            if (sel != null) {
                handler.delete(sel.getServiceId());
                refresh(listView);
            }
        });

        HBox form = new HBox(6, new VBox(new Label("Name"), tfName), new VBox(new Label("Price"), tfPrice), new VBox(new Label("Duration"), tfDuration));
        VBox root = new VBox(8, listView, form, new Label("Desc"), taDesc, bAdd, bDelete, msg);
        root.setPadding(new Insets(12));
        stage.setScene(new Scene(root, 700, 500));
        stage.setTitle("Service Management");
        stage.show();
    }

    private void refresh(ListView<Service> lv) {
        try {
            List<Service> list = handler.getAll();
            lv.setItems(FXCollections.observableArrayList(list));
            lv.setCellFactory(param -> new ListCell<>() {
                @Override
                protected void updateItem(Service s, boolean empty) {
                    super.updateItem(s, empty);
                    if (empty || s == null) setText(null);
                    else setText(s.getName() + " - Rp " + s.getPrice());
                }
            });
        } catch (Exception e) { e.printStackTrace(); }
    }
}
