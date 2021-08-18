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
	gameUser user;
	
	@FXML
	private Button btLogin;

	@FXML
	private Label lblUserName;
	
	@FXML
	private TextField txtUserName;
	
	@FXML
	public void Login(ActionEvent event) throws Exception { //접속 버튼을 눌렀을 때의 onAction
		//TODO: 네트워크 통신 시작 userName과 서버 정보, 포트번호 넘겨주기
		Stage waitingRoomWindow = new Stage();
		Parent waitingRoomRoot;
		
		FXMLLoader loader = new FXMLLoader();
		//소켓 연결
		Socket s1 = null;
		BufferedWriter bw = null;
		
	    try {
	      s1 = new Socket("127.0.0.1",8787);
	     
	      //서버로 이 정보 보내주기
	      bw = new BufferedWriter(new OutputStreamWriter(s1.getOutputStream()));
	      bw.write(txtUserName.getText().toString());
	      bw.flush();
	      
	      //user정보 객체 생성
	      gameServer sev = new gameServer();
	      user = new gameUser(s1,sev,txtUserName.getText().toString());
	      
	    } catch (UnknownHostException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }finally {
	    	if ( bw != null) try{bw.close();} catch(IOException e){}
//            if ( s1 != null) try{s1.close();} catch(IOException e){} //소켓통신중단
	    }
		
	    //대기실 창 띄어주기
		try {
			//loader에 location 설정해주기
			loader.setLocation(getClass().getResource("WaitingRoomDesign.fxml"));
			waitingRoomRoot = (Parent)loader.load();
			
			//waitingRoom으로 화면 전환 시 user의 정보 넘겨주기
			WaitingRoomController wrc = loader.getController(); // loader을 통해 WatingRoomController생성
			wrc.initData(user);
			
			Scene waitingRoomScene = new Scene(waitingRoomRoot);
			waitingRoomWindow.setScene(waitingRoomScene);
			waitingRoomWindow.show();
			
			//전의 창 닫기
			Stage loginWindow = (Stage) btLogin.getScene().getWindow();
	
			loginWindow.close();
		
	
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}	
	


