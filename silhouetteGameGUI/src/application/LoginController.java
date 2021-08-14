package application;


import java.io.IOException;
import java.net.*;
import java.io.*;
import java.util.*;

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
	public void Login(ActionEvent event) throws Exception { //접속 버튼을 눌렀을 때의 onAction
		//TODO: 네트워크 통신 시작 userName과 서버 정보, 포트번호 넘겨주기
		Stage primaryStage = new Stage();
		
		Parent root;

		//소켓 연결
		Socket s1 = null;
	    try {
	      s1 = new Socket("127.0.0.1",8787);
	    } catch (UnknownHostException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
		
		try {
			root = FXMLLoader.load(getClass().getResource("WaitingRoomDesign.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.show();
			
			
			Stage stage11 = (Stage) btLogin.getScene().getWindow();
			Platform.runLater(() -> {
			stage11.close();
				});
	
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}	
	


