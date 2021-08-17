package application;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.*;
import javafx.util.Duration;
import sun.applet.Main;

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
	private Label timer = new Label();
	
	public String answerTemp;
	final int answers = 15;
	int end = 1;
	private List<String> Images;


	private final Integer STARTTIME = 30;
	private Timeline timeline;
	private IntegerProperty timeSeconds = new SimpleIntegerProperty(STARTTIME);


	public void initData(String data) {
		String temp = data;
		answerLabel.setText(switchString(temp));
	}

	
	public void timerStart() {
		timer.textProperty().bind(timeSeconds.asString());
		timer.setTextFill(Color.RED);
		timer.setStyle("-fx-font-size: 3em;");

		if (timeline != null) {
			timeline.stop();
		}
		timeSeconds.set(STARTTIME);
		timeline = new Timeline();
		timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(STARTTIME + 1), new KeyValue(timeSeconds, 0)));
		timeline.playFromStart();
	}
	
	public void gameStart() {
		btGameStart.setVisible(false);
		btSkip.setVisible(true);
		btOpenAnswer.setVisible(true);
		inputAnswer.setVisible(true);
		imgQuiz.setVisible(true);
		timer.setVisible(true);
		// firstLoadImg(new File("resource"));
		initData(answerTemp);
		timerStart();
		// imgQuiz.setImage(loadRandomImages());
		end = 1;

	}

	public void skip() {
		imgQuiz.setImage(loadRandomImages());
		initData(answerTemp);
		

		if ((answers - end) != 0) {
			System.out.println("���� ������" + (answers - end) + "���Դϴ�.");
			end++;
			timerStart();
			
		} else {
			System.out.println("���� ������ ���� ������ �����մϴ�!");
			btGameStart.setVisible(true);
			btSkip.setVisible(false);
			btOpenAnswer.setVisible(false);
			inputAnswer.setVisible(false);
			imgQuiz.setVisible(false);
			timer.setVisible(false);
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

	public void answer() {

		if (answerLabel.getText().equals(inputAnswer.getText())) {
			System.out.println("������������");
			System.out.println("�����Դϴ�");
			System.out.println("���� ������" + (answers - end) + "���Դϴ�.");
			if ((answers - end) != 0) {
				imgQuiz.setImage(loadRandomImages());
				initData(answerTemp);
				end++;
				timerStart();
				

			} else {
				System.out.println("������ �����մϴ�!");
				btGameStart.setVisible(true);
				btSkip.setVisible(false);
				btOpenAnswer.setVisible(false);
				inputAnswer.setVisible(false);
				imgQuiz.setVisible(false);
				timer.setVisible(false);
			}
		} else {
			System.out.println("����������");
			System.out.println(answerLabel.getText());
			System.out.println(inputAnswer.getText());
			System.out.println("�����Դϴ�.");
		}

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
		if (image.indexOf("animation") != -1) {
			answerTemp = image.substring(image.lastIndexOf("animation"), image.lastIndexOf("."));
		}
		if (image.indexOf("animal") != -1) {
			answerTemp = image.substring(image.lastIndexOf("animal"), image.lastIndexOf("."));
		}
		if (image.indexOf("poke") != -1) {
			answerTemp = image.substring(image.lastIndexOf("poke"), image.lastIndexOf("."));
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
		//����
		case "animalAlpaca":
			temp = "����ī";
			break;
		case "animalCamel":
			temp = "��Ÿ";
			break;
		case "animalCat":
			temp = "�����";
			break;
		case "animalDuck":
			temp = "����";
			break;
		case "animalHamster":
			temp = "�ܽ���";
			break;
		case "animalHorse":
			temp = "��";
			break;
		case "animalMeerkat":
			temp = "�̾�Ĺ";
			break;
		case "animalMonkey":
			temp = "������";
			break;
		case "animalOstrich":
			temp = "Ÿ��";
			break;
		case "animalPanda":
			temp = "�Ǵ�";
			break;
		case "animalPig":
			temp = "����";
			break;
		case "animalPuppy":
			temp = "������";
			break;
		case "animalSheep":
			temp = "��";
			break;
		case "animalSquirrel":
			temp = "�ٶ���";
			break;
		case "animalTiger":
			temp = "ȣ����";
			break;
		//�ִϸ��̼�
		case "animationAgumon":
			temp = "�Ʊ���";
			break;
		case "animationBisiri":
			temp = "�����";
			break;
		case "animationBonobono":
			temp = "���뺸��";
			break;
		case "animationBreadman":
			temp = "�Ļ���";
			break;
		case "animationDdung":
			temp = "����";
			break;
		case "animationDulli":
			temp = "�Ѹ�";
			break;
		case "animationInuyasha":
			temp = "�̴��߻�";
			break;
		case "animationKeroro":
			temp = "�ɷη�";
			break;
		case "animationKonan":
			temp = "�ڳ�";
			break;
		case "animationLoopy":
			temp = "����";
			break;
		case "animationNaruto":
			temp = "������";
			break;
		case "animationSonongong":
			temp = "�տ���";
			break;
		case "animationTotoro":
			temp = "�����";
			break;
		case "animationZoro":
			temp = "����";
			break;
		case "animationZzanggu":
			temp = "¯��";
			break;
		//���ϸ�
		case "pokeChikorita":
			temp = "ġ�ڸ�Ÿ";
			break;
		case "pokeDdogas":
			temp = "�ǰ���";
			break;
		case "pokeEvee":
			temp = "�̺���";
			break;
		case "pokeFiry":
			temp = "���̸�";
			break;
		case "pokeGorapaduck":
			temp = "����Ĵ�";
			break;
		case "pokeJammanbo":
			temp = "�Ḹ��";
			break;
		case "pokeKkobugi":
			temp = "���α�";
			break;
		case "pokeKkojimo":
			temp = "������";
			break;
		case "pokeMangnanyong":
			temp = "������";
			break;
		case "pokeModapi":
			temp = "�����";
			break;
		case "pokeNamujigi":
			temp = "��������";
			break;
		case "pokeNyaong":
			temp = "�Ŀ���";
			break;
		case "pokePikachu":
			temp = "��ī��";
			break;
		case "pokeTogepi":
			temp = "�����";
			break;
		case "pokeYadoran":
			temp = "�ߵ���";
			break;
		case "pokeYisanghaessi":
			temp = "�̻��ؾ�";
			break;
		}
		return temp;
	}

}