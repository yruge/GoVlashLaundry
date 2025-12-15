package main;  

import javafx.application.Application;
import javafx.stage.Stage;
import view.AdminView;

public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        try {
            new AdminView(primaryStage);
            System.out.println("✅ AdminView launched!");
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}