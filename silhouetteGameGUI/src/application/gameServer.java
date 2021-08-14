package application;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Vector;

public class gameServer {
  ServerSocket ss = null;
  Vector<gameUser> alluser;
	Vector<gameRoom> room;
	
	public static void main(String[] args) {
		
		gameServer server = new gameServer();
		
		server.alluser = new Vector<>();
		server.room = new Vector<>();
		
		try {
			//���� ���� �غ�
		  server.ss = new ServerSocket(8787);
			System.out.println("Ŭ���̾�Ʈ �����..");

			//���� ���� ��� ���,����
			while(true) {
				Socket socket = server.ss.accept();
				
				//���ӵ� user ��ü ���� �� user ������ ����
				gameUser user = new gameUser(socket,server);
				user.start();
			}
		}catch(SocketException se) {
			System.out.println("[Server] ���� ���� ����" + se.toString());
		}catch(IOException ie) {
			System.out.println("[Server] ����� ����" + ie.toString());
		}
	}
}