package application;

import java.net.*;
import java.io.*;


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
  GameRoomFrame grf = null;



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
      client.grf = new GameRoomFrame(client);

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

        /* �� ���� */
        else if (m[0].equals(croomTag)) {
          createRoom(m[1]);
        }
        /* �� ���� */

        /* ���� ���� */
        else if (m[0].equals(cuserTag)) {
          viewCUser(m[1]);
        }
        /* ���� ���� */

        /* �� ��� */
        else if (m[0].equals(vroomTag)) {
          if (m.length > 1) { // �迭 ũ�Ⱑ 1���� Ŭ ��
            roomList(m[1]);
          } else { // �迭 ũ�Ⱑ 1���� �۴� == ���̾���
            String[] room = {""}; // �� ����� �񵵷� �ٲ۴�.
            client.wrf.rList.setListData(room);
          }
        }
        /* �� ��� */

        /* �� �ο� */
        else if (m[0].equals(uroomTag)) {
          roomUser(m[1]);
        }
        /* �� �ο� */

        /* �� ���� */
        else if (m[0].equals(eroomTag)) {
          enterRoom(m[1]);
        }
        /* �� ���� */
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


  /* �� ���� ���θ� Ȯ���ϴ� �޼ҵ� */
  void createRoom(String _m) {
    if (_m.equals("OKAY")) {
      System.out.println("[Client] ���ӹ� ������ �Ϸ� �ƽ��ϴ�.");
      client.grf.setVisible(true);
      client.wrf.setVisible(false);
      client.grf.setTitle(client.wrf.roomName); // ������ ���� �̸����� �����̸�����.
      client.grf.host = true; // ���� �������̸� Host�� �ȴ�.
    }
  }

  /* ���� �ο��� ����ϴ� �޼ҵ� */
  void viewCUser(String _m) {
    if (!_m.equals("")) {
      String[] user = _m.split("@");
      client.wrf.cuList.setListData(user);
    }
  }

  /* �� ����� ����ϴ� �޼ҵ� */
  void roomList(String _m) {
    if (!_m.equals("")) {
      String[] room = _m.split("@");
      client.wrf.rList.setListData(room);
    }
  }

  /* �� ���� ���θ� Ȯ���ϴ� �޼ҵ� */
  void enterRoom(String _m) {
    if (_m.equals("OKAY")) { // �� ���忡 ���� �ߴٸ�.
      System.out.println("[Client] ���ӹ� ���� �Ϸ�.");
      client.grf.setVisible(true);
      client.wrf.setVisible(false);
      client.grf.setTitle(client.wrf.selRoom); // ������ ���� �̸����� �� ���̸� ����.
      client.grf.host = false; // ��������Խ�Ʈ
    }
  }

  /* �� �ο� ����� ����ϴ� �޼ҵ� */
  void roomUser(String _m) {
    if (!_m.equals("")) {
      String[] user = _m.split("@");
      client.grf.userList.setListData(user);
    }
  }
}
