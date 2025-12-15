import javafx.application.Application;
import javafx.stage.Stage;
import view.AdminView;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Login dulu (sementara pakai langsung admin)
        // Nanti bisa diganti dengan LoginView
        new AdminView(primaryStage);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}