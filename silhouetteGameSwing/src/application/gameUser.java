package application;

import java.net.*;
import java.io.*;
import java.util.*;
import javafx.fxml.FXMLLoader;

// ������ ������ �������� �޼��� �ۼ����� �����ϴ� Ŭ����
// �����带 ��ӹ޾� ���� ��û�� ������ ���� ���������� ������ �� �ֵ��� �Ѵ�.
public class gameUser extends Thread {

  gameServer server;
  Socket socket;
  Vector<gameUser> auser; // ����� ��� Ŭ���̾�Ʈ
  Vector<gameUser> wuser; // ���ǿ� �ִ� ��� Ŭ���̾�Ʈ
  Vector<gameRoom> room; // ������ ��� Room

  // �޼��� �ۼ����� ���� �ʵ�
  OutputStream os;
  DataOutputStream dos;
  InputStream is;
  DataInputStream dis;

  String msg; // ���� �޼����� ������ �ʵ�.
  String name; // Ŭ���̾�Ʈ�� �г����� ������ �ʵ�.


  gameRoom myRoom; // ������ ���� ��ü�� ������ �ʵ�

  // �޽����� �����ϱ� ���� �±�
  final String loginTag = "LOGIN"; // �α���
  final String croomTag = "CROOM"; // �� ����
  final String vroomTag = "VROOM"; // �� ���
  final String uroomTag = "UROOM"; // �� ����
  final String eroomTag = "EROOM"; // �� ����
  final String cuserTag = "CUSER"; // ���� ����
  final String pexitTag = "PEXIT"; // ���α׷� ����
  final String rexitTag = "REXIT"; // �� ����
  final String gameStart = "START"; // ���ӽ���
  final String gameEnd = "END"; // ��������



  public gameUser(Socket soc, gameServer sev) {
    this.socket = soc;
    this.server = sev;
    auser = server.alluser;
    wuser = server.waituser;
    room = server.room;
  }


  @Override
  public void run() {
    System.out.println("[Server] Ŭ���̾�Ʈ ���� > " + this.socket.toString());

    try {
      os = this.socket.getOutputStream();
      dos = new DataOutputStream(os);
      is = this.socket.getInputStream();
      dis = new DataInputStream(is);

      while (true) {
        msg = dis.readUTF(); // �޽��� ������ ��� ����Ų��.

        String[] m = msg.split("//"); // msg�� "//"�� ������ m[]�迭�� ���ʷ� ����ִ´�.
        // ���� ���� ���ڿ����� ù ��° �迭(m[0])�� ��� �±� ����. �� ����� �и��Ѵ�.
        
        /*�α���*/
        if (m[0].equals(loginTag)) {
          String mm = m[1];

          if (!mm.equals("null")) {
            name = mm; // �α����� ������� �г����� �ʵ忡 �����Ѵ�.
            auser.add(this);
            wuser.add(this);

            dos.writeUTF(loginTag + "//OKAY");

            sendWait(connectedUser());

            if (room.size() > 0) { // ������ ���� ������ 0�̸�
              sendWait(roomInfo()); // ���� ���� �ο��� �� ����� �����Ѵ�.
            }
          }
        }
        /*�α���*/
        
        /*�����*/
        else if(m[0].equals(croomTag)) {
          myRoom = new gameRoom(); // ���ο� Room ��ü ���� �� myRoom�� �ʱ�ȭ
          myRoom.title = m[1]; // �� ������ m[1]�� ����.
          myRoom.count++; // ���� �ο����f �ϳ� �߰�
          
          room.add(myRoom); // room �迭�� myRoom�� �߰�.
          
          myRoom.gUser.add(this); // myRoom�� ���� �ο��� Ŭ���̾�Ʈ �߰�
          wuser.remove(this); // ���� ���� �ο����� Ŭ���̾�Ʈ�� �����
          
          dos.writeUTF(croomTag + "//OKAY");
          System.out.println("[Server] " + name + " : ��  '" + m[1] + "' ����");
          
          sendWait(roomInfo()); // ���� ���� �ο��� �� ����� �����Ѵ�.
          sendRoom(roomUser()); // �濡 ������ �ο��� �� �ο� ����� �����Ѵ�.
        }
        /*�� ����*/
        
        /*������*/
        else if(m[0].equals(eroomTag)) {
          for (int makedRoom = 0; makedRoom< room.size(); makedRoom++) { // ������ ���� ����
            gameRoom r = room.get(makedRoom);
            if(r.title.equals(m[1])){ // �� ������ ����.
              
              if (r.count < 6) { // �� �ο����� 6���� ���� �� ���� ����
                myRoom = room.get(makedRoom); // myRoom�� �� ������ �´� n��° room�� �ʱ�ȭ
                myRoom.count++;  // ���� �ο����� �ϳ� �߰��Ѵ�.
                wuser.remove(this); // ���� ���� �ο����� Ŭ���̾�Ʈ�� �����Ѵ�.
                myRoom.gUser.add(this); // myRoom�� ���� �ο��� Ŭ���̾�Ʈ�� �߰��Ѵ�.
                
                sendWait(roomInfo()); // ���� ���� �ο��� �� ����� ����
                sendRoom(roomUser()); // �濡 ������ �ο��� �� �ο� ����� ����.
                
                dos.writeUTF(eroomTag + "//OKAY");
                System.out.println("[Server] " + name + " : �� '" + m[1] + "' ����");
              } else {
                dos.writeUTF(eroomTag + "//FAIL");
                System.out.println("[Server] �ο� �ʰ� ���� �Ұ���!");
              }
            } else { // ���� �� ������ ������ ���� ����.
              dos.writeUTF(eroomTag + "//FAIL");
              System.out.println("[Server] " + name + " : �� '" + m[1] + "' ���� ����");
            }
          }
        }
        /*������*/
        
        /*���α׷� ����*/
        else if(m[0].equals(pexitTag)) {
          auser.remove(this); // ��ü ���� �ο����� Ŭ���̾�Ʈ ����
          wuser.remove(this); // ���� ���� �ο����� Ŭ���̾�Ʈ ����
          
          sendWait(connectedUser()); // ���� ���� �ο��� ��ü �����ο��� ����
        }
        /*���α׷� ����*/
        
        /*�� ����*/
        else if(m[0].equals(rexitTag)) {
          myRoom.gUser.remove(this); // myRoom�� ���� �ο����� Ŭ���̾�Ʈ ����
          myRoom.count--; // myRoom�� �ο��� �ϳ� ����
          wuser.add(this); // ���� ���� �ο��� Ŭ���̾�Ʈ �߰�
          
          System.out.println("[Server] " + name + " : �� '" + myRoom.title + "' ����");
          
          if (myRoom.count == 0) { // myRoom�� �ο����� 0�̸� myRoom�� room �迭���� ����.
            room.remove(myRoom);
          }
          
          if (room.size() != 0) { // ������ Room�� ������ 0�� �ƴϸ� �濡 ������ �ο��� �� �ο� ����� ����
            sendRoom(roomUser());
          }
        }
      }

    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
  
  /*���� �����ϴ� ���� ����� ��ȸ*/
  String roomInfo() {
    String msg = vroomTag + "//";
    
    for (int makedRoom = 0; makedRoom < room.size(); makedRoom++) {
      msg = msg + room.get(makedRoom).title + " : " + room.get(makedRoom).count + "@";
    }
    return msg;
  }
  /*���� �����ϴ� ���� ����� ��ȸ*/
  
  /*Ŭ���̾�Ʈ�� ������ ���� �ο��� ��ȸ*/
  String roomUser() {
    String msg = uroomTag + "//";
    
    for(int i = 0; i < myRoom.gUser.size(); i++) {
      msg = msg + myRoom.gUser.get(i).name + "@";
    }
    return msg;
  }
  /*Ŭ���̾�Ʈ�� ������ ���� �ο��� ��ȸ*/
  
  /*������ ��� ȸ�� ����� ��ȸ*/
  String connectedUser() {
    String msg = cuserTag + "//";
    for(int i = 0; i <auser.size(); i++) {
      msg = msg + auser.get(i).name + "@";
    }
    return msg;
  }
  /*������ ��� ȸ�� ����� ��ȸ*/
  
  /* ���ǿ� �ִ� ��� ȸ������ �޽��� ����*/
  void sendWait(String m) {
    for (int i = 0; i < wuser.size(); i++) {
      try {
        wuser.get(i).dos.writeUTF(m);
      } catch (Exception e) {
        wuser.remove(i--);
      }
    }
  }
  /* ���ǿ� �ִ� ��� ȸ������ �޽��� ����*/
  
  /*�濡 ������ ��� ȸ������ �޽��� ����*/
  void sendRoom(String m) {
    for (int i = 0; i <myRoom.gUser.size(); i++) {
      try {
        myRoom.gUser.get(i).dos.writeUTF(m);
      } catch (Exception e) {
        myRoom.gUser.remove(i--);
      }
    }
  }
  /*�濡 ������ ��� ȸ������ �޽��� ����*/
}
