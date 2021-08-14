package application;

import java.io.IOException;
import javafx.application.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.*;

public class WaitGameRoomMainController { // 게임 시작 전 대기실
	@FXML
	private Button exit;
	@FXML
	private Button back;
	@FXML
	private Button makeRoomBt;

	public void makeRoom() {
		Stage primaryStage = new Stage();

		Parent root;
		
		Stage stage2 = (Stage) makeRoomBt.getScene().getWindow(); // 게임방에 입장하면 방 만들기 창이 사라지도록
		Platform.runLater(() -> {
				stage2.close();
		});

		try {
			root = FXMLLoader.load(getClass().getResource("GameRoomWaitingDesign.fxml"));

			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setAlwaysOnTop(true);
			primaryStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void start() { // 게임 시작 버튼을 누르면 GameRoomPlayingDesign 으로 넘어감
		Stage primaryStage = new Stage();

		Parent root;
		
		Stage stage11 = (Stage) exit.getScene().getWindow();
		Platform.runLater(() -> {
			stage11.close();
		});
		try {
			root = FXMLLoader.load(getClass().getResource("GameRoomPlayingDesign.fxml"));

			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setAlwaysOnTop(true);
			primaryStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public void back() {
		Stage primaryStage = new Stage();

		Parent root;
		Stage stage11 = (Stage) back.getScene().getWindow();
		Platform.runLater(() -> {
			stage11.close();
		});
		try {
			root = FXMLLoader.load(getClass().getResource("WaitingRoomDesign.fxml"));

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
