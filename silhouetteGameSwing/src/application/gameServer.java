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
// 클라이언트의 연결 요청 및 입출력을 상시 관리하는 클래스.
public class gameServer {
  // 각 객체들을 Vector로 관리
  ServerSocket ss = null;
  Vector<gameUser> alluser; // 연결된 모든 클라이언트
  Vector<gameUser> waituser; // 대기실에 있는 클라이언트
  Vector<gameRoom> room;
	
	public static void main(String[] args) {
		gameServer server = new gameServer();
		
		String name;
		
		server.alluser = new Vector<>();
		server.waituser = new Vector<>();
		server.room = new Vector<>();
		
		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		
		try {
			//서버 연결 준비
			server.ss = new ServerSocket(8787);
			System.out.println("[Server] 서버 소켓 준비 완료!");

			//서버 연결 상시 대기,승인
			while(true) {
				Socket socket = server.ss.accept();
				gameUser gu = new gameUser(socket, server);
				gu.start();
			}
		}catch(SocketException se) {
			System.out.println("[Server] 서버 소켓 오류" + se.toString());
		}catch(IOException ie) {
			System.out.println("[Server] 입출력 오류" + ie.toString());
		}catch(Exception e) {
			System.out.println(e);
		}
	}
}