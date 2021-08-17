package application;

import java.net.*;
import java.io.*;
import javafx.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


// �������� ����� �� �������̽��� �����ϴ� Ŭ����
public class GameClient {

  Socket mySocket = null;

  /* �޽��� �۽��� ���� �ʵ� */
  OutputStream os = null;
  DataOutputStream dos = null;
  /* �޽��� �۽��� ���� �ʵ� */

  /* ȭ����� ������ �ʵ� */
  LoginFrame lf = null;
  WaitRoomFrame wrf = null;

  


  public static void main(String[] args) {
    GameClient client = new GameClient();
    try {
      // ������ ����
      client.mySocket = new Socket("localhost", 8787);
      System.out.println("[Client] ���� ���� ����");

      client.os = client.mySocket.getOutputStream();
      client.dos = new DataOutputStream(client.os);

      // ȭ�� ��Ʈ�ѷ� ��ü���� ����
      client.lf = new LoginFrame(client);
      client.wrf = new WaitRoomFrame(client);

      // client.wc = new WaitingRoomController(client);
      
      
      MessageListener msgListener = new MessageListener(client, client.mySocket);
      msgListener.start(); // ������ ����


    } catch (SocketException se) {
      System.out.println("[Clinet ���� ���� ���� > " + se.toString());
    } catch (IOException ie) {
      System.out.println("[Client] ����� ���� > " + ie.toString());

    }

  }

  /* ������ �޽��� ���� */
  void sendMsg(String _m) {
    try {
      dos.writeUTF(_m);
    } catch (Exception e) {
      System.out.println("[Client] �޽��� ���� ���� > " + e.toString());
    }
  }
  /* ������ �޽��� ���� */
}


// �������� �ޤĽ��� �ۼ����� �����ϴ� Ŭ����
// �����带 ��ӹ޾� �� ��ɰ� ���������� ����� �� �ֵ��� �Ѵ�.
class MessageListener extends Thread {
  Socket socket;
  GameClient client;

  /* �޽��� ������ ���� �ʵ� */
  InputStream is;
  DataInputStream dis;
  /* �޽��� ������ ���� �ʵ� */

  String msg; // ���� �޽����� ����

  /* �� �޽����� �����ϱ� ���� �±� */
  final String loginTag = "LOGIN"; // �α���
  final String croomTag = "CROOM"; // �� ����
  final String vroomTag = "VROOM"; // �� ���
  final String uroomTag = "UROOM"; // �� ����
  final String eroomTag = "EROOM"; // �� ����
  final String cuserTag = "CUSER"; // ���� ����
  final String rexitTag = "REXIT"; // �� ����
  final String gameStart = "START"; // ���ӽ���
  /* �� �޽����� �����ϱ� ���� �±� */

  MessageListener(GameClient _c, Socket _s) {
    this.client = _c;
    this.socket = _s;
  }

  @Override
  public void run() {
    try {
      is = this.socket.getInputStream();
      dis = new DataInputStream(is);

      while (true) {
        msg = dis.readUTF(); // �޽��� ������ ��� ����Ѵ�.

        String[] m = msg.split("//"); // msg�� "//"�� ������ m[] �迭�� ���ʷ� ����ִ´�.

        // ���Ź��� ���ڿ����� ù ��° �迭(m[0])�� ��� �±� ����, �� ����� �и��Ѵ�.

        /* �α��� */
        if (m[0].equals(loginTag)) {
          loginCheck(m[1]);
        }
        /* �α��� */

//        /* �� ���� */
//        else if (m[0].equals(eroomTag)) {
//          createRoom(m[1]);
//        }
//        /* �� ���� */
//
//        /* ���� ���� */
//        else if (m[0].equals(cuserTag)) {
//          viewCUser(m[1]);
//        }
//        /* ���� ���� */
//
//        /* �� ��� */
//        else if (m[0].equals(vroomTag)) {
//          if (m.length > 1) { // �迭 ũ�Ⱑ 1���� Ŭ ��
//            roomList(m[1]);
//          } else { // �迭 ũ�Ⱑ 1���� �۴� == ���̾���
//            String[] room = {""}; // �� ����� �񵵷� �ٲ۴�.
//            // client.wc ����Ʈ�信 �Ѹ���..
//          }
//        }
//        /* �� ��� */
//
//        /* �� �ο� */
//        else if (m[0].equals(uroomTag)) {
//          roomUser(m[1]);
//        }
//        /* �� �ο� */
//
//        /* �� ���� */
//        else if (m[0].equals(eroomTag)) {
//          enterRoom(m[1]);
//        }
//        /* �� ���� */
      }

    } catch (Exception e) {
      System.out.println("[Client] Error : �޽��� �ޱ� ���� > " + e.toString());
    }
  }
  
  /* �α��� ���� ���θ� Ȯ���ϴ� �޼ҵ� */
  void loginCheck(String _m) {
    if (_m.equals("OKAY")) {
      System.out.println("[Client] �α��� ���� : ���� ���� : �α��� �������̽� ����");
      client.wrf.setVisible(true);
      client.lf.dispose();
    }
  }

  
    //�� ���� ���θ� Ȯ���ϴ� �޼ҵ�
   //void createRoom
}
