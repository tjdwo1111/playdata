package application;

import java.net.*;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import java.io.*;


// �꽌踰꾩��쓽 �뿰寃곌낵 媛� �씤�꽣�럹�씠�뒪瑜� 愿�由ы븯�뒗 �겢�옒�뒪
public class GameClient {

  Socket mySocket = null;

  /* 硫붿떆吏� �넚�떊�쓣 �쐞�븳 �븘�뱶 */
  OutputStream os = null;
  DataOutputStream dos = null;
  /* 硫붿떆吏� �넚�떊�쓣 �쐞�븳 �븘�뱶 */

  /* �솕硫대뱾�쓣 愿�由ы븷 �븘�뱶 */
  LoginFrame lf = null;
  WaitRoomFrame wrf = null;


  GameRoomFrame grf = null;




  public static void main(String[] args) {
    GameClient client = new GameClient();
    try {
      // �꽌踰꾩뿉 �뿰寃�
      client.mySocket = new Socket("localhost", 8787);
      System.out.println("[Client] �꽌踰� �뿰寃� �꽦怨�");

      client.os = client.mySocket.getOutputStream();
      client.dos = new DataOutputStream(client.os);

      // �솕硫� 而⑦듃濡ㅻ윭 媛앹껜�뱾�쓣 �깮�꽦
      client.lf = new LoginFrame(client);
      client.wrf = new WaitRoomFrame(client);
      client.grf = new GameRoomFrame(client);


      // client.wc = new WaitingRoomController(client);

      MessageListener msgListener = new MessageListener(client, client.mySocket);
      msgListener.start(); // �뒪�젅�뱶 �떆�옉


    } catch (SocketException se) {
      System.out.println("[Clinet �꽌踰� �뿰寃� �삤瑜� > " + se.toString());
    } catch (IOException ie) {
      System.out.println("[Client] �엯異쒕젰 �삤瑜� > " + ie.toString());

    }

  }

  /* �꽌踰꾩뿉 硫붿떆吏� �쟾�넚 */
  void sendMsg(String _m) {
    try {
      dos.writeUTF(_m);
      System.out.println("[client] sendMSG " + _m);
    } catch (Exception e) {
      System.out.println("[Client] 硫붿떆吏� �쟾�넚 �삤瑜� > " + e.toString());
    }
  }
  /* �꽌踰꾩뿉 硫붿떆吏� �쟾�넚 */
}


// �꽌踰꾩��쓽 硫붵뀛�떆吏� �넚�닔�떊�쓣 愿�由ы븯�뒗 �겢�옒�뒪
// �뒪�젅�뱶瑜� �긽�냽諛쏆븘 媛� 湲곕뒫怨� �룆由쎌쟻�쑝濡� 湲곕뒫�븷 �닔 �엳�룄濡� �븳�떎.
class MessageListener extends Thread {
  Socket socket;
  GameClient client;

  /* 硫붿떆吏� �닔�떊�쓣 �쐞�븳 �븘�뱶 */
  InputStream is;
  DataInputStream dis;
  /* 硫붿떆吏� �닔�떊�쓣 �쐞�븳 �븘�뱶 */

  String msg; // �닔�떊 硫붿떆吏�瑜� ���옣

  /* 媛� 硫붿떆吏�瑜� 援щ텇�븯湲� �쐞�븳 �깭洹� */
  final String loginTag = "LOGIN"; // 濡쒓렇�씤
  final String croomTag = "CROOM"; // 諛� �깮�꽦
  final String vroomTag = "VROOM"; // 諛� 紐⑸줉
  final String uroomTag = "UROOM"; // 諛� �쑀��
  final String eroomTag = "EROOM"; // 諛� �엯�옣
  final String cuserTag = "CUSER"; // �젒�냽 �쑀��
  final String rexitTag = "REXIT"; // 諛� �눜�옣
  final String gameStart = "START"; // 寃뚯엫�떆�옉
  final String chatTag = "CHAT"; // 梨꾪똿 �쟾�넚
  final String chatMsgTag ="CHATM"; //梨꾪똿硫붿꽭吏��궡�슜
  /* 媛� 硫붿떆吏�瑜� 援щ텇�븯湲� �쐞�븳 �깭洹� */

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
        msg = dis.readUTF(); // 硫붿떆吏� �닔�떊�쓣 �긽�떆 ��湲고븳�떎.

        String[] m = msg.split("//"); // msg瑜� "//"濡� �굹�늻�뼱 m[] 諛곗뿴�뿉 李⑤�濡� 吏묒뼱�꽔�뒗�떎.

        // �닔�떊諛쏆� 臾몄옄�뿴�뱾�쓽 泥� 踰덉㎏ 諛곗뿴(m[0])�� 紐⑤몢 �깭洹� 臾몄옄, 媛� 湲곕뒫�쓣 遺꾨━�븳�떎.

        /* 濡쒓렇�씤 */
        if (m[0].equals(loginTag)) {
          loginCheck(m[1]);
        }
        /* 濡쒓렇�씤 */

        /* 諛� �깮�꽦 */
        else if (m[0].equals(croomTag)) {
          createRoom(m[1]);
        }
        /* 諛� �깮�꽦 */

        /* �젒�냽 �쑀�� */
        else if (m[0].equals(cuserTag)) {
          viewCUser(m[1]);
        }
        /* �젒�냽 �쑀�� */

        /* 諛� 紐⑸줉 */
        else if (m[0].equals(vroomTag)) {
          if (m.length > 1) { // 諛곗뿴 �겕湲곌� 1蹂대떎 �겢 �븣
            roomList(m[1]);
          } else { // 諛곗뿴 �겕湲곌� 1蹂대떎 �옉�떎 == 諛⑹씠�뾾�떎
            String[] room = {""}; // 諛� 紐⑸줉�씠 鍮꾨룄濡� 諛붽씔�떎.
            client.wrf.rList.setListData(room);
          }
        }
        /* 諛� 紐⑸줉 */

        /* 諛� �씤�썝 */
        else if (m[0].equals(uroomTag)) {
          roomUser(m[1]);
        }
        /* 諛� �씤�썝 */

        /* 諛� �엯�옣 */
        else if (m[0].equals(eroomTag)) {
          enterRoom(m[1]);
        }
        /* 諛� �엯�옣 */
        
        /* server로 부터 채팅 내용 전달 받음 */
        else if(m[0].equals(chatMsgTag)) {
        	//TODO: 梨꾪똿李쎌뿉 蹂댁뿬二쇨린 
        	System.out.println("[CLIENT] 서버에서 받은 다른 user의 메시지 " + m[1]);
        	client.grf.output.append(m[1]+"\n");
        }
    
      }//while end

    } catch (Exception e) {
      System.out.println("[Client] Error : 硫붿떆吏� 諛쏄린 �삤瑜� > " + e.toString());
    }
  }//run end

  /* 濡쒓렇�씤 �꽦怨� �뿬遺�瑜� �솗�씤�븯�뒗 硫붿냼�뱶 */
  void loginCheck(String _m) {
    if (_m.equals("OKAY")) {
      System.out.println("[Client] 濡쒓렇�씤 �꽦怨� : ��湲곕갑 �뿴�읆 : 濡쒓렇�씤 �씤�꽣�럹�씠�뒪 醫낅즺");
      client.wrf.setVisible(true);
      client.lf.dispose();
    }
  }


  /* 諛� �깮�꽦 �뿬遺�瑜� �솗�씤�븯�뒗 硫붿냼�뱶 */
  void createRoom(String _m) {
    if (_m.equals("OKAY")) {
      System.out.println("[Client] 寃뚯엫諛� �깮�꽦�씠 �셿猷� �릱�뒿�땲�떎.");
      client.grf.setVisible(true);
      client.wrf.setVisible(false);

      // client.grf.setTitle(client.wrf.roomName); // �깮�꽦�븳 諛⑹쓽 �씠由꾩쑝濡� �뱾�뼱媛꾨갑�씠由꾩꽕�젙.
      // client.grf.host = 寃뚯엫�쓽�샇�뒪�듃

      client.grf.setTitle(client.wrf.roomName); // �깮�꽦�븳 諛⑹쓽 �씠由꾩쑝濡� �뱾�뼱媛꾨갑�씠由꾩꽕�젙.
      client.grf.host = true; // 諛⑹쓣 留뚮뱺�궗�엺�씠硫� Host媛� �맂�떎.

    }
  }

  /* �젒�냽 �씤�썝�쓣 異쒕젰�븯�뒗 硫붿냼�뱶 */
  void viewCUser(String _m) {
    if (!_m.equals("")) {
      String[] user = _m.split("@");
      client.wrf.cuList.setListData(user);
    }
  }

  /* 諛� 紐⑸줉�쓣 異쒕젰�븯�뒗 硫붿냼�뱶 */
  void roomList(String _m) {
    if (!_m.equals("")) {
      String[] room = _m.split("@");
      client.wrf.rList.setListData(room);
    }
  }

  /* 諛� �엯�옣 �뿬遺�瑜� �솗�씤�븯�뒗 硫붿냼�뱶 */
  void enterRoom(String _m) {
    if (_m.equals("OKAY")) { // 諛� �엯�옣�뿉 �꽦怨� �뻽�떎硫�.
      System.out.println("[Client] 寃뚯엫諛� �엯�옣 �셿猷�.");
      client.grf.setVisible(true);
      client.wrf.setVisible(false);
      client.grf.setTitle(client.wrf.selRoom); // �꽑�깮�븳 諛⑹쓽 �씠由꾩쑝濡� �뱾�뼱媛� 諛⑹씠由� �꽕�젙.
      client.grf.host = false; // �뱾�뼱媛꾩궗�엺��寃뚯뒪�듃
    }
  }

  /* 諛� �씤�썝 紐⑸줉�쓣 異쒕젰�븯�뒗 硫붿냼�뱶 */
  void roomUser(String _m) {
    if (!_m.equals("")) {
      String[] user = _m.split("@");
      client.grf.userList.setListData(user);
    }
  }
  
}
