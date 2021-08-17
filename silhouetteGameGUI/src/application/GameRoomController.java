package application;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.control.ScrollBar;
import javafx.stage.*;

public class GameRoomController implements Initializable {
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
	@FXML
	private ImageView imgQuiz;
	@FXML
	private Button btAnswer;
	@FXML
	private Label answerLabel;
	@FXML
	private TextField inputAnswer;
	@FXML
	private TextField inputMsg;
	@FXML
	private TextArea outputMsg;
	@FXML 
	private ScrollBar verticalScrollBar;

	public String answerTemp;
	final int answers = 15;
	int end = 1;
	private List<String> Images;

	public void initData(String data) {
		String temp = data;
		answerLabel.setText(switchString(temp));
	}

	public void gameStart() {
		btGameStart.setVisible(false);
		btSkip.setVisible(true);
		btOpenAnswer.setVisible(true);
		inputAnswer.setVisible(true);
		imgQuiz.setVisible(true);
		// firstLoadImg(new File("resource"));
		initData(answerTemp);
		// imgQuiz.setImage(loadRandomImages());
		end = 1;
	}

	public void skip() {
		imgQuiz.setImage(loadRandomImages());
		initData(answerTemp);

		if ((answers - end) != 0) {
			System.out.println("���� ������" + (answers - end) + "���Դϴ�.");
			end++;
		} else {
			System.out.println("���� ������ ���� ������ �����մϴ�!");
			btGameStart.setVisible(true);
			btSkip.setVisible(false);
			btOpenAnswer.setVisible(false);
			inputAnswer.setVisible(false);
			imgQuiz.setVisible(false);
		}
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

			Stage gameRoomWindow = (Stage) btExit.getScene().getWindow();
			gameRoomWindow.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void send() {
		inputMsg.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent k) {
				if (k.getCode().equals(KeyCode.ENTER)) {
					send();
					outputMsg.appendText(":" + inputMsg.getText() + "\n");
					inputMsg.setText("");
					inputMsg.requestFocus();
				}
			}
		});
		btSend.setOnAction(event -> {
			send();
			outputMsg.appendText(":" + inputMsg.getText() + "\n");
			inputMsg.setText("");
			inputMsg.requestFocus();
		});

	}

	public void answer() {
		inputAnswer.setOnKeyPressed(new EventHandler<KeyEvent>() {
			
			@Override
			public void handle(KeyEvent k) {
				if (k.getCode().equals(KeyCode.ENTER)) {
					answer();
					inputAnswer.setText("");
					inputAnswer.requestFocus();
				}
			}
		});
		if (answerLabel.getText().equals(inputAnswer.getText())) {
	
			inputAnswer.setText("");
			inputAnswer.requestFocus();
			System.out.println("������������");
			System.out.println("�����Դϴ�");
			System.out.println("���� ������" + (answers - end) + "���Դϴ�.");
			if ((answers - end) != 0) {
				imgQuiz.setImage(loadRandomImages());
				initData(answerTemp);
				end++;
			} else {
				System.out.println("������ �����մϴ�!");
				btGameStart.setVisible(true);
				btSkip.setVisible(false);
				btOpenAnswer.setVisible(false);
				inputAnswer.setVisible(false);
				imgQuiz.setVisible(false);
			}
		} else {
			inputAnswer.setText("");
			inputAnswer.requestFocus();
			System.out.println("����������");
			System.out.println(answerLabel.getText());
			System.out.println(inputAnswer.getText());
			System.out.println("�����Դϴ�.");

		}

	}

	public void scrollBar() {
		verticalScrollBar.setMin(0);
		verticalScrollBar.setMax(100);
		verticalScrollBar.setBlockIncrement(1);
		verticalScrollBar.setUnitIncrement(1);
		verticalScrollBar.setVisibleAmount(10);
		verticalScrollBar.valueProperty().addListener((observable, oldValue, newValue) -> {
		});
	}


	//
	// �̹��� ó�� �׽�Ʈ..
	public Image loadRandomImages() {
		int countImages = Images.size();
		int imageNumber = (int) (Math.random() * countImages);
		String image = Images.get(imageNumber);

		if (image.indexOf("over") != -1) {
			answerTemp = image.substring(image.lastIndexOf("over"), image.lastIndexOf("."));
		}
		if (image.indexOf("lol") != -1) {
			answerTemp = image.substring(image.lastIndexOf("lol"), image.lastIndexOf("."));
		}

		return new Image(image);
	}

	// File ���丮�� ���� ���� �̹����� ã�´�.
	public void firstLoadImg(final File directroty) {
		if (Images == null) {
			Images = new ArrayList<String>();
		} else {
			Images.clear();
		}

		File[] files = directroty.listFiles();
		for (File f : files) {
			if (f.isDirectory()) {
				firstLoadImg(f);
			} else {
				Images.add(f.getPath());
			}
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		firstLoadImg(new File("resource"));
		imgQuiz.setImage(loadRandomImages());
	}

	public String switchString(String data) {
		String temp = data;
		switch (temp) {
		// ��
		case "lolVeigar":
			temp = "���̰�";
			break;
		case "lolAshe":
			temp = "�ֽ�";
			break;
		case "lolGalio":
			temp = "������";
			break;
		case "lolJanna":
			temp = "�ܳ�";
			break;
		case "lolKaisa":
			temp = "ī�̻�";
			break;
		case "lolRakan":
			temp = "��ĭ";
			break;
		case "lolLuLu":
			temp = "���";
			break;
		case "lolMaokai":
			temp = "����ī��";
			break;
		case "lolTeemo":
			temp = "Ƽ��";
			break;
		case "lolThresh":
			temp = "������";
			break;
		case "lolViego":
			temp = "�񿡰�";
			break;
		case "lolNocturn":
			temp = "����";
			break;
		case "lolRiven":
			temp = "����";
			break;
		case "lolYuumi":
			temp = "����";
			break;
		case "lolYorick":
			temp = "�丯";
			break;
		// ������ġ
		case "overWinston":
			temp = "������";
			break;
		case "overTracer":
			temp = "Ʈ���̼�";
			break;
		case "overSombra":
			temp = "�غ��";
			break;
		case "overReaper":
			temp = "����";
			break;
		case "overRack":
			temp = "��ŷ��";
			break;
		case "overDva":
			temp = "���";
			break;
		case "overEcho":
			temp = "����";
			break;
		case "overAshe":
			temp = "�ֽ�";
			break;
		case "overDoom":
			temp = "���ǽ�Ʈ";
			break;
		case "overAna":
			temp = "�Ƴ�";
			break;
		case "over76":
			temp = "����76";
			break;
		case "overGenji":
			temp = "����";
			break;
		case "overMercy":
			temp = "�޸���";
			break;
		case "overOrisa":
			temp = "������";
			break;
		case "overHog":
			temp = "�ε�ȣ��";
			break;
		}
		return temp;
	}

}