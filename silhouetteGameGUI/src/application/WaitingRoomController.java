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
	private Button btOpenMakeRoom;
	@FXML
	private ListView lvRoomList;
	
	gameUser user;
	public void initData(gameUser user) {
		this.user = user;
		System.out.println("wating room.. name " + user.name);
	}
	
	//방만들기 클릭 리스너
	public void openMakeRoomWindow() {
		Stage makeRoomWindow = new Stage();
		
		Parent makeRoomRoot;
		
		try {
			
//			//loader에 location 설정해주기
//			loader.setLocation(getClass().getResource("MakeRoomDesign.fxml"));
//			root = (Parent)loader.load();
			
			makeRoomRoot = FXMLLoader.load(getClass().getResource("MakeRoomDesign.fxml"));
			Scene makeRoomScene = new Scene(makeRoomRoot);
			makeRoomWindow.setScene(makeRoomScene);
			makeRoomWindow.setAlwaysOnTop(true);
			makeRoomWindow.show();
			
		} catch (IOException e) {
			System.out.println("에러?");
			e.printStackTrace();
		}
		
		Stage waitingRoomWindow = (Stage) btExit.getScene().getWindow();
		waitingRoomWindow.close();
		
	}
	
	public void close() {
		Stage waitingRoomWindow = (Stage) btExit.getScene().getWindow();
		waitingRoomWindow.close();
		
	}
	//makeRoom 창 
	
	@FXML
	private Button btBack;
	
	@FXML
	private Button btMakeRoom;

	public void makeRoom() {
		Stage gameRoomWindow = new Stage();

		Parent gameRoomRoot;
		
		Stage makeRoomWindow = (Stage) btMakeRoom.getScene().getWindow(); // 게임방에 입장하면 방 만들기 창이 사라지도록
		makeRoomWindow.close();

		
		try {
			gameRoomRoot = FXMLLoader.load(getClass().getResource("GameRoomDesign.fxml"));

			Scene gameRoomScene = new Scene(gameRoomRoot);
			gameRoomWindow.setScene(gameRoomScene);
			gameRoomWindow.setAlwaysOnTop(true);
			gameRoomWindow.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
		

	}
	
	public void back() {
		Stage waitingRoonmWindow = new Stage();
		Parent waitingRoomRoot;
		
		try {
			waitingRoomRoot = FXMLLoader.load(getClass().getResource("WaitingRoomDesign.fxml"));
			Scene waitingRoomScene = new Scene(waitingRoomRoot);
			waitingRoonmWindow.setScene(waitingRoomScene);
			waitingRoonmWindow.show();
			
			
			Stage makeRoomWindow = (Stage) btBack.getScene().getWindow();
			makeRoomWindow.close();
			
	
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
