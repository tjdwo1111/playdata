package application;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Vector;

// Ŭ���̾�Ʈ�� ���� ��û �� ������� ��� �����ϴ� Ŭ����.
public class gameServer {
  // �� ��ü���� Vector�� ����
  ServerSocket ss = null;
  Vector<gameUser> alluser; // ����� ��� Ŭ���̾�Ʈ
  Vector<gameUser> waituser; // ���ǿ� �ִ� Ŭ���̾�Ʈ
  Vector<gameRoom> room;

  public static void main(String[] args) {
    gameServer server = new gameServer();

    server.alluser = new Vector<>();
    server.waituser = new Vector<>();
    server.room = new Vector<>();

    try {
      // ���� ���� �غ�
      server.ss = new ServerSocket(8787);
      System.out.println("[Server] ���� ���� �غ� �Ϸ�!");

      // ���� ���� ��� ���,����
      while (true) {
        Socket socket = server.ss.accept();
        gameUser gu = new gameUser(socket, server);
        gu.start();
      }
    } catch (SocketException se) {
      System.out.println("[Server] ���� ���� ����" + se.toString());
    } catch (IOException ie) {
      System.out.println("[Server] ����� ����" + ie.toString());
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
