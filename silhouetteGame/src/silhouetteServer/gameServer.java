package silhouetteServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Vector;

public class gameServer {
	Vector<gameUser> alluser;
	Vector<gameRoom> room;
	
	public static void main(String[] args) {
		ServerSocket ss = null;
		Socket socket = null;
		
		gameServer server = new gameServer();
		
		server.alluser = new Vector<>();
		server.room = new Vector<>();
		
		try {
			//���� ���� �غ�
			ss = new ServerSocket(8787);
			System.out.println("Ŭ���̾�Ʈ �����..");

			//���� ���� ��� ���,����
			while(true) {
				socket = ss.accept();
				
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
