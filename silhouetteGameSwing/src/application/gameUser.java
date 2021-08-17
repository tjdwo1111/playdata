package application;

import java.net.*;
import java.io.*;
import java.util.*;
import javafx.fxml.FXMLLoader;

// 서버에 접속한 유저와의 메세지 송수신을 관리하는 클래스
// 스레드를 상속받아 연결 요청이 들어왔을 때도 독립적으로 동작할 수 있도록 한다.
public class gameUser extends Thread {

  gameServer server;
  Socket socket;
  Vector<gameUser> auser; // 연결된 모든 클라이언트
  Vector<gameUser> wuser; // 대기실에 있는 모든 클라이언트
  Vector<gameRoom> room; // 생성된 모든 Room

  // 메세지 송수신을 위한 필드
  OutputStream os;
  DataOutputStream dos;
  InputStream is;
  DataInputStream dis;

  String msg; // 수신 메세지를 저장할 필드.
  String name; // 클라이언트의 닉네임을 저장할 필드.


  gameRoom myRoom; // 입장한 방의 객체를 저장할 필드

  // 메시지를 구분하기 위한 태그
  final String loginTag = "LOGIN"; // 로그인
  final String croomTag = "CROOM"; // 방 생성
  final String vroomTag = "VROOM"; // 방 목록
  final String uroomTag = "UROOM"; // 방 유저
  final String eroomTag = "EROOM"; // 방 입장
  final String cuserTag = "CUSER"; // 접속 유저
  final String pexitTag = "PEXIT"; // 프로그램 종료
  final String rexitTag = "REXIT"; // 방 퇴장
  final String gameStart = "START"; // 게임시작
  final String gameEnd = "END"; // 게임종료



  public gameUser(Socket soc, gameServer sev) {
    this.socket = soc;
    this.server = sev;
    auser = server.alluser;
    wuser = server.waituser;
    room = server.room;
  }


  @Override
  public void run() {
    System.out.println("[Server] 클라이언트 접속 > " + this.socket.toString());

    try {
      os = this.socket.getOutputStream();
      dos = new DataOutputStream(os);
      is = this.socket.getInputStream();
      dis = new DataInputStream(is);

      while (true) {
        msg = dis.readUTF(); // 메시지 수신을 상시 대기시킨다.

        String[] m = msg.split("//"); // msg를 "//"로 나누어 m[]배열에 차례로 집어넣는다.
        // 수신 받은 문자열들의 첫 번째 배열(m[0])은 모두 태그 문자. 각 기능을 분리한다.
        
        /*로그인*/
        if (m[0].equals(loginTag)) {
          String mm = m[1];

          if (!mm.equals("null")) {
            name = mm; // 로그인한 사용자의 닉네임을 필드에 저장한다.
            auser.add(this);
            wuser.add(this);

            dos.writeUTF(loginTag + "//OKAY");

            sendWait(connectedUser());

            if (room.size() > 0) { // 생성된 방의 갯수가 0이면
              sendWait(roomInfo()); // 대기실 접속 인원에 방 목록을 전송한다.
            }
          }
        }
        /*로그인*/
        
        /*방생성*/
        else if(m[0].equals(croomTag)) {
          myRoom = new gameRoom(); // 새로운 Room 객체 생성 후 myRoom에 초기화
          myRoom.title = m[1]; // 방 제목을 m[1]로 설정.
          myRoom.count++; // 방의 인원수릃 하나 추가
          
          room.add(myRoom); // room 배열에 myRoom을 추가.
          
          myRoom.gUser.add(this); // myRoom의 접속 인원에 클라이언트 추가
          wuser.remove(this); // 대기실 접속 인원에서 클라이언트를 지운다
          
          dos.writeUTF(croomTag + "//OKAY");
          System.out.println("[Server] " + name + " : 방  '" + m[1] + "' 생성");
          
          sendWait(roomInfo()); // 대기실 접속 인원에 방 목록을 전송한다.
          sendRoom(roomUser()); // 방에 입장한 인원에 방 인원 목록을 전송한다.
        }
        /*방 생성*/
        
        /*방입장*/
        else if(m[0].equals(eroomTag)) {
          for (int makedRoom = 0; makedRoom< room.size(); makedRoom++) { // 생성된 방의 갯수
            gameRoom r = room.get(makedRoom);
            if(r.title.equals(m[1])){ // 방 제목이 같고.
              
              if (r.count < 6) { // 방 인원수가 6명보다 적을 때 입장 성공
                myRoom = room.get(makedRoom); // myRoom에 두 조건이 맞는 n번째 room을 초기화
                myRoom.count++;  // 방의 인원수를 하나 추가한다.
                wuser.remove(this); // 대기실 접속 인원에서 클라이언트를 삭제한다.
                myRoom.gUser.add(this); // myRoom의 접속 인원에 클라이언트를 추가한다.
                
                sendWait(roomInfo()); // 대기실 접속 인원에 방 목록을 전송
                sendRoom(roomUser()); // 방에 입장한 인원에 방 인원 목록을 전송.
                
                dos.writeUTF(eroomTag + "//OKAY");
                System.out.println("[Server] " + name + " : 방 '" + m[1] + "' 입장");
              } else {
                dos.writeUTF(eroomTag + "//FAIL");
                System.out.println("[Server] 인원 초과 입장 불가능!");
              }
            } else { // 같은 방 제목이 없으니 입장 실패.
              dos.writeUTF(eroomTag + "//FAIL");
              System.out.println("[Server] " + name + " : 방 '" + m[1] + "' 입장 오류");
            }
          }
        }
        /*방입장*/
        
        /*프로그램 종료*/
        else if(m[0].equals(pexitTag)) {
          auser.remove(this); // 전체 접속 인원에서 클라이언트 삭제
          wuser.remove(this); // 대기실 접속 인원에서 클라이언트 삭제
          
          sendWait(connectedUser()); // 대기실 접속 인원에 전체 접속인원을 전송
        }
        /*프로그램 종료*/
        
        /*방 퇴장*/
        else if(m[0].equals(rexitTag)) {
          myRoom.gUser.remove(this); // myRoom의 접속 인원에서 클라이언트 삭제
          myRoom.count--; // myRoom의 인원수 하나 삭제
          wuser.add(this); // 대기실 접속 인원에 클라이언트 추가
          
          System.out.println("[Server] " + name + " : 방 '" + myRoom.title + "' 퇴장");
          
          if (myRoom.count == 0) { // myRoom의 인원수가 0이면 myRoom을 room 배열에서 삭제.
            room.remove(myRoom);
          }
          
          if (room.size() != 0) { // 생성된 Room의 개수가 0이 아니면 방에 입장한 인원에 방 인원 목록을 전송
            sendRoom(roomUser());
          }
        }
      }

    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  /*현재 존재하는 방의 목록을 조회*/
  String roomInfo() {
    String msg = vroomTag + "//";
    
    for (int makedRoom = 0; makedRoom < room.size(); makedRoom++) {
      msg = msg + room.get(makedRoom).title + " : " + room.get(makedRoom).count + "@";
    }
    return msg;
  }
  /*현재 존재하는 방의 목록을 조회*/
  
  /*클라이언트가 입장한 방의 인원을 조회*/
  String roomUser() {
    String msg = uroomTag + "//";
    
    for(int i = 0; i < myRoom.gUser.size(); i++) {
      msg = msg + myRoom.gUser.get(i).name + "@";
    }
    return msg;
  }
  /*클라이언트가 입장한 방의 인원을 조회*/
  
  /*접속한 모든 회원 목록을 조회*/
  String connectedUser() {
    String msg = cuserTag + "//";
    for(int i = 0; i <auser.size(); i++) {
      msg = msg + auser.get(i).name + "@";
    }
    return msg;
  }
  /*접속한 모든 회원 목록을 조회*/
  
  /* 대기실에 있는 모든 회원에게 메시지 전송*/
  void sendWait(String m) {
    for (int i = 0; i < wuser.size(); i++) {
      try {
        wuser.get(i).dos.writeUTF(m);
      } catch (Exception e) {
        wuser.remove(i--);
      }
    }
  }
  /* 대기실에 있는 모든 회원에게 메시지 전송*/
  
  /*방에 입장한 모든 회원에게 메시지 전송*/
  void sendRoom(String m) {
    for (int i = 0; i <myRoom.gUser.size(); i++) {
      try {
        myRoom.gUser.get(i).dos.writeUTF(m);
      } catch (Exception e) {
        myRoom.gUser.remove(i--);
      }
    }
  }
  /*방에 입장한 모든 회원에게 메시지 전송*/
}
