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
  GameClient c;
  @FXML
  private Button btLogin;

  @FXML
  private Label lblUserName;

  @FXML
  private TextField txtUserName;

  final String loginTag = "LOGIN";

  
  public LoginController() {
    super();
  }

  public GameClient initData(GameClient _c) {
    return this.c = _c;
  }
  
  @FXML
  public void Login(ActionEvent event) throws Exception { // 접속 버튼을 눌렀을 때의 onAction
    
    String name = txtUserName.getText();
    if (name.equals("")) {
      System.out.println("[Client 로그인 실패 : 이름 미입력");
    } else {
      c.sendMsg(loginTag + "//" + name);
    }
  }

  void connectWaitngRoom() {
    try {
      Stage waitingRoomWindow = new Stage();
      Parent waitingRoomRoot;

      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("WaitingRoomDesign.fxml"));
      waitingRoomRoot = (Parent) loader.load();
      Scene waitingRoomScene = new Scene(waitingRoomRoot);
      waitingRoomWindow.setScene(waitingRoomScene);
      waitingRoomWindow.show();

      Stage loginWindow = (Stage) btLogin.getScene().getWindow();
      loginWindow.close();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}


