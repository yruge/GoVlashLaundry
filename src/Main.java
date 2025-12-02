import database.Connect;
import javafx.application.Application;
import javafx.stage.Stage;
import view.Auth;

public class Main extends Application {
//	private Connect connect = Connect.getInstance();

	public static void main(String[] args) {
		launch(args);
	}


	@Override
	public void start(Stage primaryStage) throws Exception {
		new Auth(primaryStage);
		
	}

}
