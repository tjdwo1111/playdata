package silhouetteServer;

import java.net.*;
import java.io.*;
import java.util.*;

public class gameUser extends Thread {


  gameSever server;
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


  public gameUser(Socket soc, Server sev) {
    this.socket = soc;
    this.server = sev;

    auser = server.alluser;
  }


  @Override
  public void run() {
    System.out.println("서버에 입장합니다!" + this.socket.toString());

    os = this.socket.getOutputStream();
    dos = new DataOutputStream(os);
    is = this.socket.getInputStream();
    dis = new DataInputStream(is);
  }
}
