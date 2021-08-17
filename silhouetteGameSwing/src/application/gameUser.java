package application;

import java.net.*;
import java.io.*;
import java.util.*;

// �꽌踰꾩뿉 �젒�냽�븳 �쑀�����쓽 硫붿꽭吏� �넚�닔�떊�쓣 愿�由ы븯�뒗 �겢�옒�뒪
// �뒪�젅�뱶瑜� �긽�냽諛쏆븘 �뿰寃� �슂泥��씠 �뱾�뼱�솕�쓣 �븣�룄 �룆由쎌쟻�쑝濡� �룞�옉�븷 �닔 �엳�룄濡� �븳�떎.
public class gameUser extends Thread {

  gameServer server;
  Socket socket;
  Vector<gameUser> auser; // �뿰寃곕맂 紐⑤뱺 �겢�씪�씠�뼵�듃
  Vector<gameUser> wuser; // ��湲곗떎�뿉 �엳�뒗 紐⑤뱺 �겢�씪�씠�뼵�듃
  Vector<gameRoom> room; // �깮�꽦�맂 紐⑤뱺 Room

  // 硫붿꽭吏� �넚�닔�떊�쓣 �쐞�븳 �븘�뱶
  OutputStream os;
  DataOutputStream dos;
  InputStream is;
  DataInputStream dis;

  String msg; // �닔�떊 硫붿꽭吏�瑜� ���옣�븷 �븘�뱶.
  String name; // �겢�씪�씠�뼵�듃�쓽 �땳�꽕�엫�쓣 ���옣�븷 �븘�뱶.


  gameRoom myRoom; // �엯�옣�븳 諛⑹쓽 媛앹껜瑜� ���옣�븷 �븘�뱶

  // 硫붿떆吏�瑜� 援щ텇�븯湲� �쐞�븳 �깭洹�
  final String loginTag = "LOGIN"; // 濡쒓렇�씤
  final String croomTag = "CROOM"; // 諛� �깮�꽦
  final String vroomTag = "VROOM"; // 諛� 紐⑸줉
  final String uroomTag = "UROOM"; // 諛� �쑀��
  final String eroomTag = "EROOM"; // 諛� �엯�옣
  final String cuserTag = "CUSER"; // �젒�냽 �쑀��
  final String pexitTag = "PEXIT"; // �봽濡쒓렇�옩 醫낅즺
  final String rexitTag = "REXIT"; // 諛� �눜�옣
  final String gameStart = "START"; // 寃뚯엫�떆�옉
  final String gameEnd = "END"; // 寃뚯엫醫낅즺

  final String chatTag = "CHAT";
  final String chatMsgTag ="CHATM";

  final String gameSkip = "SKIP"; // 寃뚯엫 �뒪�궢




  public gameUser(Socket soc, gameServer sev) {
    this.socket = soc;
    this.server = sev;
    auser = server.alluser;
    wuser = server.waituser;
    room = server.room;
  }


  @Override
  public void run() {
    System.out.println("[Server] �겢�씪�씠�뼵�듃 �젒�냽 > " + this.socket.toString());

    try {
      os = this.socket.getOutputStream();
      dos = new DataOutputStream(os);
      is = this.socket.getInputStream();
      dis = new DataInputStream(is);

      while (true) {
        msg = dis.readUTF(); // 硫붿떆吏� �닔�떊�쓣 �긽�떆 ��湲곗떆�궓�떎.

        String[] m = msg.split("//"); // msg瑜� "//"濡� �굹�늻�뼱 m[]諛곗뿴�뿉 李⑤�濡� 吏묒뼱�꽔�뒗�떎.
        // �닔�떊 諛쏆� 臾몄옄�뿴�뱾�쓽 泥� 踰덉㎏ 諛곗뿴(m[0])�� 紐⑤몢 �깭洹� 臾몄옄. 媛� 湲곕뒫�쓣 遺꾨━�븳�떎.

        /* 濡쒓렇�씤 */
        if (m[0].equals(loginTag)) {
          String mm = m[1];

          if (!mm.equals("null")) {
            name = mm; // 濡쒓렇�씤�븳 �궗�슜�옄�쓽 �땳�꽕�엫�쓣 �븘�뱶�뿉 ���옣�븳�떎.
            auser.add(this);
            wuser.add(this);

            dos.writeUTF(loginTag + "//OKAY");

            sendWait(connectedUser());

            if (room.size() > 0) { // �깮�꽦�맂 諛⑹쓽 媛��닔媛� 0�씠硫�
              sendWait(roomInfo()); // ��湲곗떎 �젒�냽 �씤�썝�뿉 諛� 紐⑸줉�쓣 �쟾�넚�븳�떎.
            }
          }
        }
        /* 濡쒓렇�씤 */

        /* 諛⑹깮�꽦 */
        else if (m[0].equals(croomTag)) {
          myRoom = new gameRoom(); // �깉濡쒖슫 Room 媛앹껜 �깮�꽦 �썑 myRoom�뿉 珥덇린�솕
          myRoom.title = m[1]; // 諛� �젣紐⑹쓣 m[1]濡� �꽕�젙.
          myRoom.count++; // 諛⑹쓽 �씤�썝�닔혨f �븯�굹 異붽�

          room.add(myRoom); // room 諛곗뿴�뿉 myRoom�쓣 異붽�.

          myRoom.gUser.add(this); // myRoom�쓽 �젒�냽 �씤�썝�뿉 �겢�씪�씠�뼵�듃 異붽�
          wuser.remove(this); // ��湲곗떎 �젒�냽 �씤�썝�뿉�꽌 �겢�씪�씠�뼵�듃瑜� 吏��슫�떎

          dos.writeUTF(croomTag + "//OKAY");
          System.out.println("[Server] " + name + " : 諛�  '" + m[1] + "' �깮�꽦");

          sendWait(roomInfo()); // ��湲곗떎 �젒�냽 �씤�썝�뿉 諛� 紐⑸줉�쓣 �쟾�넚�븳�떎.
          sendRoom(roomUser()); // 諛⑹뿉 �엯�옣�븳 �씤�썝�뿉 諛� �씤�썝 紐⑸줉�쓣 �쟾�넚�븳�떎.
        }
        /* 諛� �깮�꽦 */

        /* 諛⑹엯�옣 */
        else if (m[0].equals(eroomTag)) {
          for (int makedRoom = 0; makedRoom < room.size(); makedRoom++) { // �깮�꽦�맂 諛⑹쓽 媛��닔
            gameRoom r = room.get(makedRoom);
            if (r.title.equals(m[1])) { // 諛� �젣紐⑹씠 媛숆퀬.

              if (r.count < 6) { // 諛� �씤�썝�닔媛� 6紐낅낫�떎 �쟻�쓣 �븣 �엯�옣 �꽦怨�
                myRoom = room.get(makedRoom); // myRoom�뿉 �몢 議곌굔�씠 留욌뒗 n踰덉㎏ room�쓣 珥덇린�솕
                myRoom.count++; // 諛⑹쓽 �씤�썝�닔瑜� �븯�굹 異붽��븳�떎.
                wuser.remove(this); // ��湲곗떎 �젒�냽 �씤�썝�뿉�꽌 �겢�씪�씠�뼵�듃瑜� �궘�젣�븳�떎.
                myRoom.gUser.add(this); // myRoom�쓽 �젒�냽 �씤�썝�뿉 �겢�씪�씠�뼵�듃瑜� 異붽��븳�떎.

                sendWait(roomInfo()); // ��湲곗떎 �젒�냽 �씤�썝�뿉 諛� 紐⑸줉�쓣 �쟾�넚
                sendRoom(roomUser()); // 諛⑹뿉 �엯�옣�븳 �씤�썝�뿉 諛� �씤�썝 紐⑸줉�쓣 �쟾�넚.

                dos.writeUTF(eroomTag + "//OKAY");
                System.out.println("[Server] " + name + " : 諛� '" + m[1] + "' �엯�옣");
              } else {
                dos.writeUTF(eroomTag + "//FAIL");
                System.out.println("[Server] �씤�썝 珥덇낵 �엯�옣 遺덇��뒫!");
              }
            } else { // 媛숈� 諛� �젣紐⑹씠 �뾾�쑝�땲 �엯�옣 �떎�뙣.
              dos.writeUTF(eroomTag + "//FAIL");
              System.out.println("[Server] " + name + " : 諛� '" + m[1] + "' �엯�옣 �삤瑜�");
            }
          }
        }
        /* 諛⑹엯�옣 */

        /* �봽濡쒓렇�옩 醫낅즺 */
        else if (m[0].equals(pexitTag)) {
          auser.remove(this); // �쟾泥� �젒�냽 �씤�썝�뿉�꽌 �겢�씪�씠�뼵�듃 �궘�젣
          wuser.remove(this); // ��湲곗떎 �젒�냽 �씤�썝�뿉�꽌 �겢�씪�씠�뼵�듃 �궘�젣

          sendWait(connectedUser()); // ��湲곗떎 �젒�냽 �씤�썝�뿉 �쟾泥� �젒�냽�씤�썝�쓣 �쟾�넚
        }
        /* �봽濡쒓렇�옩 醫낅즺 */

        /* 諛� �눜�옣 */
        else if (m[0].equals(rexitTag)) {
          myRoom.gUser.remove(this); // myRoom�쓽 �젒�냽 �씤�썝�뿉�꽌 �겢�씪�씠�뼵�듃 �궘�젣
          myRoom.count--; // myRoom�쓽 �씤�썝�닔 �븯�굹 �궘�젣
          wuser.add(this); // ��湲곗떎 �젒�냽 �씤�썝�뿉 �겢�씪�씠�뼵�듃 異붽�

          System.out.println("[Server] " + name + " : 諛� '" + myRoom.title + "' �눜�옣");

          if (myRoom.count == 0) { // myRoom�쓽 �씤�썝�닔媛� 0�씠硫� myRoom�쓣 room 諛곗뿴�뿉�꽌 �궘�젣.
            room.remove(myRoom);
          }

          if (room.size() != 0) { // �깮�꽦�맂 Room�쓽 媛쒖닔媛� 0�씠 �븘�땲硫� 諛⑹뿉 �엯�옣�븳 �씤�썝�뿉 諛� �씤�썝 紐⑸줉�쓣 �쟾�넚
            sendRoom(roomUser());
          }
        
        /*梨꾪똿*/
        }else if(m[0].equals(chatTag)) {
        	System.out.println("[Server] 메세지 내용 " + m[1]);
        	System.out.println("name찍히니?" + name);
        	sendRoom(chatMsg(m[1]));
        }
        /*梨꾪똿*/
        
      }

    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  /* �쁽�옱 議댁옱�븯�뒗 諛⑹쓽 紐⑸줉�쓣 議고쉶 */
  String roomInfo() {
    String msg = vroomTag + "//";

    for (int makedRoom = 0; makedRoom < room.size(); makedRoom++) {
      msg = msg + room.get(makedRoom).title + " : " + room.get(makedRoom).count + "@";
    }
    return msg;
  }
  /* �쁽�옱 議댁옱�븯�뒗 諛⑹쓽 紐⑸줉�쓣 議고쉶 */

  /* �겢�씪�씠�뼵�듃媛� �엯�옣�븳 諛⑹쓽 �씤�썝�쓣 議고쉶 */
  String roomUser() {
    String msg = uroomTag + "//";

    for (int i = 0; i < myRoom.gUser.size(); i++) {
      msg = msg + myRoom.gUser.get(i).name + "@";
    }
    return msg;
  }
  /* �겢�씪�씠�뼵�듃媛� �엯�옣�븳 諛⑹쓽 �씤�썝�쓣 議고쉶 */

  /* 梨꾪똿 硫붿꽭吏� �궡�샊 */
  String chatMsg(String chatMsg) {
	  String msg = chatMsgTag + "//" + name + " : " + chatMsg;
	  return msg;
  }
  /* 梨꾪똿 硫붿꽭吏� �궡�샊 */
  
  /* �젒�냽�븳 紐⑤뱺 �쉶�썝 紐⑸줉�쓣 議고쉶 */
  String connectedUser() {
    String msg = cuserTag + "//";
    for (int i = 0; i < auser.size(); i++) {
      msg = msg + auser.get(i).name + "@";
    }
    return msg;
  }
  /* �젒�냽�븳 紐⑤뱺 �쉶�썝 紐⑸줉�쓣 議고쉶 */

  /* ��湲곗떎�뿉 �엳�뒗 紐⑤뱺 �쉶�썝�뿉寃� 硫붿떆吏� �쟾�넚 */
  void sendWait(String m) {
    for (int i = 0; i < wuser.size(); i++) {
      try {
        wuser.get(i).dos.writeUTF(m);
      } catch (Exception e) {
        wuser.remove(i--);
      }
    }
  }
  /* ��湲곗떎�뿉 �엳�뒗 紐⑤뱺 �쉶�썝�뿉寃� 硫붿떆吏� �쟾�넚 */

  /* 諛⑹뿉 �엯�옣�븳 紐⑤뱺 �쉶�썝�뿉寃� 硫붿떆吏� �쟾�넚 */
  void sendRoom(String m) {
    for (int i = 0; i < myRoom.gUser.size(); i++) {
      try {
        myRoom.gUser.get(i).dos.writeUTF(m);
      } catch (Exception e) {
        myRoom.gUser.remove(i--);
      }
    }
  }
  /* 諛⑹뿉 �엯�옣�븳 紐⑤뱺 �쉶�썝�뿉寃� 硫붿떆吏� �쟾�넚 */
}
