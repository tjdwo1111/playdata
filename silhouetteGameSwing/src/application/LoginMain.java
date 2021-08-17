package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginMain extends Application {
  
  GameClient c;
  public LoginMain(GameClient _c) {
    c = _c;
  }
  
  FXMLLoader loader = new FXMLLoader();
  
  @Override
  public void start(Stage primaryStage) throws IOException {
    loader.setLocation(getClass().getResource("LoginDesign.fxml"));
    Parent root = (Parent) loader.load();
    primaryStage.setScene(new Scene(root));
    primaryStage.setTitle("실루엣 맞추기 게임");
    primaryStage.show();
    
  }
}
