package application;

import java.net.*;
import java.io.*;
import java.util.*;

public class gameUser extends Thread {


  gameServer server;
  Socket socket; // Client Socket
  Vector<gameUser> auser;

  Vector<gameRoom> room;

  OutputStream os;
  DataOutputStream dos;
  InputStream is;
  DataInputStream dis;

  String msg; // 수신할 메세지
  String name; // gameUser's name
 

  gameRoom nowRoom; // 입장한 방의 Object를 저장


  // 메세지 구분 태그
  // final String


  public gameUser(Socket soc, gameServer sev, String name) {
    this.socket = soc;
	this.server = sev;
	this.name = name;
    auser = server.alluser;
  }


@Override
  public void run() {
    System.out.println(name + "서버에 입장합니다!" + this.socket.toString());
    
    try {
      os = this.socket.getOutputStream();
      dos = new DataOutputStream(os);
      is = this.socket.getInputStream();
      dis = new DataInputStream(is);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  public static void main(String[] args) {
//    Socket s1 = null;
//    Socket s2 = null;
//    try {
//      s1 = new Socket("127.0.0.1",8787);
//      s2 = new Socket("127.0.0.1",8787);
//    } catch (UnknownHostException e) {
//      e.printStackTrace();
//    } catch (IOException e) {
//      e.printStackTrace();
//    }
//    gameServer sev = new gameServer();
//    gameUser g1 = new gameUser(s1,sev);
//    gameUser g2 = new gameUser(s2,sev);
  }
}