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
			//서버 연결 준비
		  server.ss = new ServerSocket(8787);
			System.out.println("클라이언트 대기중..");

			//서버 연결 상시 대기,승인
			while(true) {
				Socket socket = server.ss.accept();
				
				//접속된 user 객체 생성 후 user 스레드 시작
				gameUser user = new gameUser(socket,server);
				user.start();
			}
		}catch(SocketException se) {
			System.out.println("[Server] 서버 소켓 오류" + se.toString());
		}catch(IOException ie) {
			System.out.println("[Server] 입출력 오류" + ie.toString());
		}
	}
}