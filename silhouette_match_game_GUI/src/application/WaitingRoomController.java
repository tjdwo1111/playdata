package application;

import java.io.IOException;
import javafx.application.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.*;

public class WaitingRoomController{
	@FXML
	private Button exit;
	@FXML
	private Button makeRoom;
	@FXML
	private ListView roomList;
	
	public void makeRoom() {
		Stage primaryStage = new Stage();
		
		Parent root;
		
		try {
			root = FXMLLoader.load(getClass().getResource("MakeRoomDesign.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setAlwaysOnTop(true);
			primaryStage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void close() {
		Stage stage11 = (Stage) exit.getScene().getWindow();
		Platform.runLater(() -> {
			stage11.close();
		});
	}
	
}
