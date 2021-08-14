package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
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
		
		String name;
		
		server.alluser = new Vector<>();
		server.room = new Vector<>();
		
		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		
		try {
			//���� ���� �غ�
			server.ss = new ServerSocket(8787);
			System.out.println("Ŭ���̾�Ʈ �����..");

			//���� ���� ��� ���,����
			while(true) {
				Socket socket = server.ss.accept();
				
//				is  = socket.getInputStream();
//				isr = new InputStreamReader(is);
//				br = new BufferedReader(isr);
//				
//				String nickname = br.readLine();
//				System.out.println("nick" + nickname);
				br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

				name = br.readLine();
				//���ӵ� user ��ü ���� �� user ������ ����
				gameUser user = new gameUser(socket, server,name);
				user.start();
			}
		}catch(SocketException se) {
			System.out.println("[Server] ���� ���� ����" + se.toString());
		}catch(IOException ie) {
			System.out.println("[Server] ����� ����" + ie.toString());
		}catch(Exception e) {
			System.out.println(e);
		}
	}
}