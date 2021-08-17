package application;

import java.net.*;
import java.io.*;
import javafx.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


// 서버와의 연결과 각 인터페이스를 관리하는 클래스
public class GameClient {

  Socket mySocket = null;

  /* 메시지 송신을 위한 필드 */
  OutputStream os = null;
  DataOutputStream dos = null;
  /* 메시지 송신을 위한 필드 */

  /* 화면들을 관리할 필드 */
  LoginFrame lf = null;
  WaitRoomFrame wrf = null;

  


  public static void main(String[] args) {
    GameClient client = new GameClient();
    try {
      // 서버에 연결
      client.mySocket = new Socket("localhost", 8787);
      System.out.println("[Client] 서버 연결 성공");

      client.os = client.mySocket.getOutputStream();
      client.dos = new DataOutputStream(client.os);

      // 화면 컨트롤러 객체들을 생성
      client.lf = new LoginFrame(client);
      client.wrf = new WaitRoomFrame(client);

      // client.wc = new WaitingRoomController(client);
      
      
      MessageListener msgListener = new MessageListener(client, client.mySocket);
      msgListener.start(); // 스레드 시작


    } catch (SocketException se) {
      System.out.println("[Clinet 서버 연결 오류 > " + se.toString());
    } catch (IOException ie) {
      System.out.println("[Client] 입출력 오류 > " + ie.toString());

    }

  }

  /* 서버에 메시지 전송 */
  void sendMsg(String _m) {
    try {
      dos.writeUTF(_m);
    } catch (Exception e) {
      System.out.println("[Client] 메시지 전송 오류 > " + e.toString());
    }
  }
  /* 서버에 메시지 전송 */
}


// 서버와의 메ㅔ시지 송수신을 관리하는 클래스
// 스레드를 상속받아 각 기능과 독립적으로 기능할 수 있도록 한다.
class MessageListener extends Thread {
  Socket socket;
  GameClient client;

  /* 메시지 수신을 위한 필드 */
  InputStream is;
  DataInputStream dis;
  /* 메시지 수신을 위한 필드 */

  String msg; // 수신 메시지를 저장

  /* 각 메시지를 구분하기 위한 태그 */
  final String loginTag = "LOGIN"; // 로그인
  final String croomTag = "CROOM"; // 방 생성
  final String vroomTag = "VROOM"; // 방 목록
  final String uroomTag = "UROOM"; // 방 유저
  final String eroomTag = "EROOM"; // 방 입장
  final String cuserTag = "CUSER"; // 접속 유저
  final String rexitTag = "REXIT"; // 방 퇴장
  final String gameStart = "START"; // 게임시작
  /* 각 메시지를 구분하기 위한 태그 */

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
        msg = dis.readUTF(); // 메시지 수신을 상시 대기한다.

        String[] m = msg.split("//"); // msg를 "//"로 나누어 m[] 배열에 차례로 집어넣는다.

        // 수신받은 문자열들의 첫 번째 배열(m[0])은 모두 태그 문자, 각 기능을 분리한다.

        /* 로그인 */
        if (m[0].equals(loginTag)) {
          loginCheck(m[1]);
        }
        /* 로그인 */

//        /* 방 생성 */
//        else if (m[0].equals(eroomTag)) {
//          createRoom(m[1]);
//        }
//        /* 방 생성 */
//
//        /* 접속 유저 */
//        else if (m[0].equals(cuserTag)) {
//          viewCUser(m[1]);
//        }
//        /* 접속 유저 */
//
//        /* 방 목록 */
//        else if (m[0].equals(vroomTag)) {
//          if (m.length > 1) { // 배열 크기가 1보다 클 때
//            roomList(m[1]);
//          } else { // 배열 크기가 1보다 작다 == 방이없다
//            String[] room = {""}; // 방 목록이 비도록 바꾼다.
//            // client.wc 리스트뷰에 뿌리기..
//          }
//        }
//        /* 방 목록 */
//
//        /* 방 인원 */
//        else if (m[0].equals(uroomTag)) {
//          roomUser(m[1]);
//        }
//        /* 방 인원 */
//
//        /* 방 입장 */
//        else if (m[0].equals(eroomTag)) {
//          enterRoom(m[1]);
//        }
//        /* 방 입장 */
      }

    } catch (Exception e) {
      System.out.println("[Client] Error : 메시지 받기 오류 > " + e.toString());
    }
  }
  
  /* 로그인 성공 여부를 확인하는 메소드 */
  void loginCheck(String _m) {
    if (_m.equals("OKAY")) {
      System.out.println("[Client] 로그인 성공 : 대기방 열럼 : 로그인 인터페이스 종료");
      client.wrf.setVisible(true);
      client.lf.dispose();
    }
  }

  
    //방 생성 여부를 확인하는 메소드
   //void createRoom
}
