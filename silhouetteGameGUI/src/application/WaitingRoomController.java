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
	FXMLLoader loader = new FXMLLoader();
	
	@FXML
	private Button btExit;
	@FXML
	private Button btMakeRoom;
	@FXML
	private ListView lvRoomList;
	
	gameUser user;
	public void initData(gameUser user) {
		this.user = user;
		System.out.println("wating room.. name " + user.name);
	}
	
	//방만들기 클릭 리스너
	public void makeRoom() {
		Stage primaryStage = new Stage();
		
		Parent root;
		
		try {
			
//			//loader에 location 설정해주기
//			loader.setLocation(getClass().getResource("MakeRoomDesign.fxml"));
//			root = (Parent)loader.load();
			
			root = FXMLLoader.load(getClass().getResource("MakeRoomDesign.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setAlwaysOnTop(true);
			primaryStage.show();
			
		} catch (IOException e) {
			System.out.println("에러?");
			e.printStackTrace();
		}
		
	}
	
	public void close() {
		Stage stage11 = (Stage) btExit.getScene().getWindow();
		Platform.runLater(() -> {
			stage11.close();
		});
	}
	
}
