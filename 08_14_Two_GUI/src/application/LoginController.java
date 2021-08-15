package application;


import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
	
	@FXML
	private Button btLogin;

	@FXML
	private Label lblUserName;
	
	@FXML
	private TextField txtUserName;
	
	@FXML
	public void Login(ActionEvent event) throws Exception {
		Stage waitingRoonmWindow = new Stage();
		Parent waitingRoomRoot;
		
		try {
			waitingRoomRoot = FXMLLoader.load(getClass().getResource("WaitingRoomDesign.fxml"));
			Scene waitingRoomScene = new Scene(waitingRoomRoot);
			waitingRoonmWindow.setScene(waitingRoomScene);
			waitingRoonmWindow.show();
			
			
			Stage loginWindow = (Stage) btLogin.getScene().getWindow();
			loginWindow.close();
			
	
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}	
	


