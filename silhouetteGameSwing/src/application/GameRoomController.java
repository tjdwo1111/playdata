package application;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.*;
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
      System.out.println("남은 문제는" + (answers - end) + "개입니다.");
      end++;
    } else {
      System.out.println("남은 문제가 없어 게임을 종료합니다!");
      btGameStart.setVisible(true);
      btSkip.setVisible(false);
      btOpenAnswer.setVisible(false);
      inputAnswer.setVisible(false);
      imgQuiz.setVisible(false);
    }
  }

  public void openAnswerWindow() { // 정답을 누르면 Answer 로 넘어감
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
      System.out.println("정답정답정답");
      System.out.println("정답입니다");
      System.out.println("남은 문제는" + (answers - end) + "개입니다.");
      if ((answers - end) != 0) {
        imgQuiz.setImage(loadRandomImages());
        initData(answerTemp);
        end++;
      } else {
        System.out.println("게임을 종료합니다!");
        btGameStart.setVisible(true);
        btSkip.setVisible(false);
        btOpenAnswer.setVisible(false);
        inputAnswer.setVisible(false);
        imgQuiz.setVisible(false);
      }
    } else {
      System.out.println("오답오답오답");
      System.out.println(answerLabel.getText());
      System.out.println(inputAnswer.getText());
      System.out.println("오답입니다.");
    }

  }

  //
  // 이미지 처리 테스트..
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


  // File 디렉토리로 들어가서 랜덤 이미지를 찾는다.
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
      // 롤
      case "lolVeigar":
        temp = "베이가";
        break;
      case "lolAshe":
        temp = "애쉬";
        break;
      case "lolGalio":
        temp = "갈리오";
        break;
      case "lolJanna":
        temp = "잔나";
        break;
      case "lolKaisa":
        temp = "카이사";
        break;
      case "lolRakan":
        temp = "라칸";
        break;
      case "lolLuLu":
        temp = "룰루";
        break;
      case "lolMaokai":
        temp = "마오카이";
        break;
      case "lolTeemo":
        temp = "티모";
        break;
      case "lolThresh":
        temp = "쓰레쉬";
        break;
      case "lolViego":
        temp = "비에고";
        break;
      case "lolNocturn":
        temp = "녹턴";
        break;
      case "lolRiven":
        temp = "리븐";
        break;
      case "lolYuumi":
        temp = "유미";
        break;
      case "lolYorick":
        temp = "요릭";
        break;
      // 오버워치
      case "overWinston":
        temp = "윈스턴";
        break;
      case "overTracer":
        temp = "트레이서";
        break;
      case "overSombra":
        temp = "솜브라";
        break;
      case "overReaper":
        temp = "리퍼";
        break;
      case "overRack":
        temp = "레킹볼";
        break;
      case "overDva":
        temp = "디바";
        break;
      case "overEcho":
        temp = "에코";
        break;
      case "overAshe":
        temp = "애쉬";
        break;
      case "overDoom":
        temp = "둠피스트";
        break;
      case "overAna":
        temp = "아나";
        break;
      case "over76":
        temp = "솔져76";
        break;
      case "overGenji":
        temp = "겐지";
        break;
      case "overMercy":
        temp = "메르시";
        break;
      case "overOrisa":
        temp = "오리사";
        break;
      case "overHog":
        temp = "로드호그";
        break;
    }
    return temp;
  }


}
