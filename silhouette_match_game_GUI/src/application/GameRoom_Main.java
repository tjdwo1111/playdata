package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class GameRoom_Main extends Application {
	@Override
	public void start(Stage primaryStage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("GameRoom_Design.fxml"));
		primaryStage.setScene(new Scene(root));
		primaryStage.setTitle("�Ƿ翧 ���߱�");
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
