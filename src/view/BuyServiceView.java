package view;

import controller.ServiceController;
import controller.TransactionController;
import controller.UserController;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import model.Service;

public class BuyServiceView {
    private ServiceController serviceController = new ServiceController();
    private TransactionController transactionController = new TransactionController();
    private TableView<Service> table;

    public VBox getView() {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(15));
        Label title = new Label("Buy Service");
        title.setStyle("-fx-font-size: 20px;");

        table = new TableView<>();
        TableColumn<Service, String> colName = new TableColumn<>("Service");
        colName.setCellValueFactory(new PropertyValueFactory<>("serviceName"));
        TableColumn<Service, Double> colPrice = new TableColumn<>("Price");
        colPrice.setCellValueFactory(new PropertyValueFactory<>("servicePrice"));
        TableColumn<Service, String> colDesc = new TableColumn<>("Description");
        colDesc.setCellValueFactory(new PropertyValueFactory<>("serviceDescription"));

        table.getColumns().addAll(colName, colPrice, colDesc);
        table.getItems().setAll(serviceController.getAllServices());

        // Input Tambahan Transaksi
        TextField txtWeight = new TextField(); txtWeight.setPromptText("Total Weight (Kg)");
        TextArea txtNotes = new TextArea(); txtNotes.setPromptText("Notes (Optional)"); txtNotes.setMaxHeight(60);
        Button btnOrder = new Button("Place Order");
        Label lblMsg = new Label();

        btnOrder.setOnAction(e -> {
            Service selected = table.getSelectionModel().getSelectedItem();
            if (selected == null) {
                lblMsg.setText("Please select a service first.");
                return;
            }
            try {
                Double weight = Double.parseDouble(txtWeight.getText());
                String valid = transactionController.validateOrder(weight, txtNotes.getText());
                
                if (valid.equals("VALID")) {
                    int customerID = UserController.getCurrentUser().getUserID();
                    transactionController.orderLaundryService(selected.getServiceID(), customerID, weight, txtNotes.getText());
                    lblMsg.setText("Order placed successfully! Check 'My History'.");
                    lblMsg.setStyle("-fx-text-fill: green;");
                } else {
                    lblMsg.setText(valid);
                    lblMsg.setStyle("-fx-text-fill: red;");
                }
            } catch (NumberFormatException ex) {
                lblMsg.setText("Weight must be a number.");
            }
        });

        layout.getChildren().addAll(title, table, new Label("Order Details:"), txtWeight, txtNotes, btnOrder, lblMsg);
        return layout;
    }
}