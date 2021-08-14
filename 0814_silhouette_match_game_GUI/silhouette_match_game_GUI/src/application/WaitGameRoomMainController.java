package application;

import java.io.IOException;
import javafx.application.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.*;

public class WaitGameRoomMainController { // ���� ���� �� ����
	@FXML
	private Button exit;
	@FXML
	private Button back;
	@FXML
	private Button makeRoomBt;

	
	public void makeRoom() {
		Stage primaryStage = new Stage();

		Parent root;

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

	public void start() { // ���� ���� ��ư�� ������ GameRoomPlayingDesign ���� �Ѿ
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
		Stage stage11 = (Stage) back.getScene().getWindow();
		Platform.runLater(() -> {
			stage11.close();
		});
	}

	public void close() {
		Stage stage11 = (Stage) exit.getScene().getWindow();
		Platform.runLater(() -> {
			stage11.close();
		});
	}

}
