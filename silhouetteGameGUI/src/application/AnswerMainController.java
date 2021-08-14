package application;

import java.io.IOException;
import javafx.application.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.*;

public class AnswerMainController {
	@FXML
	private Button exit;
	
	@FXML
	private Button answer;
	@FXML
	private Button makeRoomBt;

	public void answer() {


		// if(answer.eqeuls) 만약 정답과 같다면 출력
		System.out.println("정답입니다");
		Stage stage11 = (Stage) answer.getScene().getWindow();
		stage11.close();

	}
}
