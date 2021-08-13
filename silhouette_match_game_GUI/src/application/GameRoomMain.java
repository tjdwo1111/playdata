package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class GameRoomMain extends Application {
	@Override
	public void start(Stage primaryStage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("GameRoomPlayingDesign.fxml"));
		primaryStage.setScene(new Scene(root));
		primaryStage.setTitle("실루엣 맞추기");
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
