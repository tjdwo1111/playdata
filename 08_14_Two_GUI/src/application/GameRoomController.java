package application;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.*;

public class GameRoomController{
	@FXML
	private Button btExit;
	@FXML
	private Button btGameStart;
	@FXML
	private Button btSkip;
	@FXML
	private Button btOpenAnswer;
	@FXML
	private Button btSend;

	public void gameStart() {
		btGameStart.setVisible(false);
		btSkip.setVisible(true);
		btOpenAnswer.setVisible(true);
	}
	
	public void openAnswerWindow() { // ������ ������ Answer �� �Ѿ
		Stage answerWindow = new Stage();
		
		Parent answerRoot;
		
		try {
			answerRoot = FXMLLoader.load(getClass().getResource("Answer.fxml"));
			
			Scene answerScene = new Scene(answerRoot);
			answerWindow.setScene(answerScene);
			answerWindow.setAlwaysOnTop(true);
			answerWindow.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void returnToWaitingRoom() {
		Stage waitingRoonmWindow = new Stage();
		Parent waitingRoomRoot;
		
		try {
			waitingRoomRoot = FXMLLoader.load(getClass().getResource("WaitingRoomDesign.fxml"));
			Scene waitingRoomScene = new Scene(waitingRoomRoot);
			waitingRoonmWindow.setScene(waitingRoomScene);
			waitingRoonmWindow.show();
			
			Stage  gameRoomWindow = (Stage) btExit.getScene().getWindow();
			gameRoomWindow.close();
					
	
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	@FXML
	private Button btAnswer;

	@FXML
	private TextField txtAnswer;
	
	public void answer() {
		// if(answer.eqeuls) ���� ����� ���ٸ� ���
		System.out.println("�����Դϴ�");
		Stage answerWindow = (Stage) btAnswer.getScene().getWindow();
		answerWindow.close();

	}
	
}
