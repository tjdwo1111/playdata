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

  String msg; // ������ �޼���
  String name; // gameUser's name

  gameRoom nowRoom; // ������ ���� Object�� ����


  // �޼��� ���� �±�
  // final String


  public gameUser(Socket soc, Server sev) {
    this.socket = soc;
    this.server = sev;

    auser = server.alluser;
  }


  @Override
  public void run() {
    System.out.println("������ �����մϴ�!" + this.socket.toString());

    os = this.socket.getOutputStream();
    dos = new DataOutputStream(os);
    is = this.socket.getInputStream();
    dis = new DataInputStream(is);
  }
}
