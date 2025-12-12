package view;

import controller.TransactionHandler;
import model.Transaction;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class TransactionListView {
    private TransactionHandler th = new TransactionHandler();
    private String statusFilter;

    public TransactionListView(String statusFilter) {
        this.statusFilter = statusFilter;
    }

    public void show(Stage stage) {
        ListView<Transaction> lv = new ListView<>();
        refresh(lv);

        Button bAssign = new Button("Assign Receptionist (simulate)");
        Button bFinish = new Button("Finish (simulate by Laundry)");
        Label msg = new Label();

        bAssign.setOnAction(e -> {
            Transaction sel = lv.getSelectionModel().getSelectedItem();
            if (sel != null) {
                String res = th.assignReceptionist(sel.getTransactionId(), "11111111-1111-1111-1111-111111111112"); // example receptionist id
                msg.setText(res);
                refresh(lv);
            }
        });

        bFinish.setOnAction(e -> {
            Transaction sel = lv.getSelectionModel().getSelectedItem();
            if (sel != null) {
                String res = th.finishByLaundry(sel.getTransactionId(), "22222222-2222-2222-2222-222222222222"); // example laundry id
                msg.setText(res);
                refresh(lv);
            }
        });

        VBox root = new VBox(8, new Label("Transactions (" + statusFilter + ")"), lv, bAssign, bFinish, msg);
        root.setPadding(new Insets(12));
        stage.setScene(new Scene(root, 700, 500));
        stage.setTitle("Transactions");
        stage.show();
    }

    private void refresh(ListView<Transaction> lv) {
        List<Transaction> list = (statusFilter == null) ? th.listAll() : th.listByStatus(statusFilter);
        lv.setItems(FXCollections.observableArrayList(list));
        lv.setCellFactory(param -> new ListCell<>() {
            @Override protected void updateItem(Transaction t, boolean empty) {
                super.updateItem(t, empty);
                if (empty || t == null) setText(null);
                else setText(t.getTransactionId() + " - " + t.getStatus() + " - " + t.getTotalWeight() + "kg");
            }
        });
    }
}
