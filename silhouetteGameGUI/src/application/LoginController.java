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
	public void Login(ActionEvent event) throws Exception { //���� ��ư�� ������ ���� onAction
		//TODO: ��Ʈ��ũ ��� ���� userName�� ���� ����, ��Ʈ��ȣ �Ѱ��ֱ�
		Stage waitingRoomWindow = new Stage();
		Parent waitingRoomRoot;
		
		FXMLLoader loader = new FXMLLoader();
		//���� ����
		Socket s1 = null;
		BufferedWriter bw = null;
		
	    try {
	      s1 = new Socket("127.0.0.1",8787);
	     
	      //������ �� ���� �����ֱ�
	      bw = new BufferedWriter(new OutputStreamWriter(s1.getOutputStream()));
	      bw.write(txtUserName.getText().toString());
	      bw.flush();
	      
	      //user���� ��ü ����
	      gameServer sev = new gameServer();
	      user = new gameUser(s1,sev,txtUserName.getText().toString());
	      
	    } catch (UnknownHostException e) {
	      e.printStackTrace();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }finally {
	    	if ( bw != null) try{bw.close();} catch(IOException e){}
//            if ( s1 != null) try{s1.close();} catch(IOException e){} //��������ߴ�
	    }
		
	    //���� â ����ֱ�
		try {
			//loader�� location �������ֱ�
			loader.setLocation(getClass().getResource("WaitingRoomDesign.fxml"));
			waitingRoomRoot = (Parent)loader.load();
			
			//waitingRoom���� ȭ�� ��ȯ �� user�� ���� �Ѱ��ֱ�
			WaitingRoomController wrc = loader.getController(); // loader�� ���� WatingRoomController����
			wrc.initData(user);
			
			Scene waitingRoomScene = new Scene(waitingRoomRoot);
			waitingRoomWindow.setScene(waitingRoomScene);
			waitingRoomWindow.show();
			
			//���� â �ݱ�
			Stage loginWindow = (Stage) btLogin.getScene().getWindow();
	
			loginWindow.close();
		
	
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}	
	


