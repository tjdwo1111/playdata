package application;

import java.net.*;
import javax.swing.ImageIcon;
import java.io.*;


// 서버와의 연결과 각 인터페이스를 관리하는 클래스
public class GameClient {

  Socket mySocket = null;

  /* 메시지 송신을 위한 필드 */
  OutputStream os = null;
  DataOutputStream dos = null;
  /* 메시지 송신을 위한 필드 */

  /* 화면들을 관리할 필드 */
  LoginFrame lf = null;
  WaitRoomFrame wrf = null;
  GameRoomFrame grf = null;



  public static void main(String[] args) {
    GameClient client = new GameClient();
    try {
      // 서버에 연결
      client.mySocket = new Socket("localhost", 8787);
      System.out.println("[Client] 서버 연결 성공");

      client.os = client.mySocket.getOutputStream();
      client.dos = new DataOutputStream(client.os);

      // 화면 컨트롤러 객체들을 생성
      client.lf = new LoginFrame(client);
      client.wrf = new WaitRoomFrame(client);
      client.grf = new GameRoomFrame(client);

      // client.wc = new WaitingRoomController(client);

      MessageListener msgListener = new MessageListener(client, client.mySocket);
      msgListener.start(); // 스레드 시작


    } catch (SocketException se) {
      System.out.println("[Clinet 서버 연결 오류 > " + se.toString());
    } catch (IOException ie) {
      System.out.println("[Client] 입출력 오류 > " + ie.toString());

    }

  }

  /* 서버에 메시지 전송 */
  void sendMsg(String _m) {
    try {
      dos.writeUTF(_m);
    } catch (Exception e) {
      System.out.println("[Client] 메시지 전송 오류 > " + e.toString());
    }
  }
  /* 서버에 메시지 전송 */
}


// 서버와의 메ㅔ시지 송수신을 관리하는 클래스
// 스레드를 상속받아 각 기능과 독립적으로 기능할 수 있도록 한다.
class MessageListener extends Thread {
  Socket socket;
  GameClient client;

  /* 메시지 수신을 위한 필드 */
  InputStream is;
  DataInputStream dis;
  /* 메시지 수신을 위한 필드 */

  String msg; // 수신 메시지를 저장

  /* 각 메시지를 구분하기 위한 태그 */
  final String loginTag = "LOGIN"; // 로그인
  final String croomTag = "CROOM"; // 방 생성
  final String vroomTag = "VROOM"; // 방 목록
  final String uroomTag = "UROOM"; // 방 유저
  final String eroomTag = "EROOM"; // 방 입장
  final String cuserTag = "CUSER"; // 접속 유저
  final String rexitTag = "REXIT"; // 방 퇴장
  final String gameStart = "START"; // 게임시작
  final String gameEnd = "END"; // 게임 끝
  final String gameSkip = "SKIP"; // 스킵
  final String gameAnswer = "ANSWER"; // 정답
  final String chatTag = "CHAT"; // 채팅전송
  final String chatMsgTag = "CHATM"; // 받은 메시지

  /* 각 메시지를 구분하기 위한 태그 */

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
        msg = dis.readUTF(); // 메시지 수신을 상시 대기한다.

        String[] m = msg.split("//"); // msg를 "//"로 나누어 m[] 배열에 차례로 집어넣는다.

        // 수신받은 문자열들의 첫 번째 배열(m[0])은 모두 태그 문자, 각 기능을 분리한다.

        /* 로그인 */
        if (m[0].equals(loginTag)) {
          loginCheck(m[1]);
        }
        /* 로그인 */

        /* 방 생성 */
        else if (m[0].equals(croomTag)) {
          createRoom(m[1]);
        }
        /* 방 생성 */

        /* 접속 유저 */
        else if (m[0].equals(cuserTag)) {
          viewCUser(m[1]);
        }
        /* 접속 유저 */

        /* 방 목록 */
        else if (m[0].equals(vroomTag)) {
          if (m.length > 1) { // 배열 크기가 1보다 클 때
            roomList(m[1]);
          } else { // 배열 크기가 1보다 작다 == 방이없다
            String[] room = {""}; // 방 목록이 비도록 바꾼다.
            client.wrf.rList.setListData(room);
          }
        }
        /* 방 목록 */

        /* 방 인원 */
        else if (m[0].equals(uroomTag)) {
          roomUser(m[1]);
        }
        /* 방 인원 */

        /* 방 입장 */
        else if (m[0].equals(eroomTag)) {
          enterRoom(m[1]);
        }
        /* 방 입장 */

        /* 게임방 퇴장 */
        else if (m[0].equals(rexitTag)) {
          exitRoom();
        }
        /* 게임방 퇴장 */

        /* 전달받은 채팅 내용 */
        else if (m[0].equals(chatMsgTag)) {
          System.out.println("[Client] 서버에서 받은 다른 USER의 메시지 " + m[1]);
          client.grf.output.append(m[1] + "\n");
        }
        /* 전달받은 채팅 내용 */

        /* 게임 시작 */
        else if (m[0].equals(gameStart)) {
          System.out.println("[Client] 게임 시작! 이미지를 랜덤하게 보여줍니다");
          setrandomImg(m[1]);
          client.grf.startBtn.setVisible(false);
          client.grf.answerBtn.setVisible(true);
          client.grf.skipBtn.setVisible(true);
        }
        /* 게임 시작 */

        /* 게임 스킵 */
        else if (m[0].equals(gameSkip)) {
          System.out.println("[Client] 게임 스킵! 이미지를 다시 섞습니다.");
          setrandomImg(m[1]);
          client.grf.count++;
        }
        /* 게임 스킵 */

        /* 게임 정답 */
        else if (m[0].equals(gameAnswer)) {
          System.out.println("[Client] 게입 정답! 이미지를 다시 섞습니다");
          setrandomImg(m[1]);
          client.grf.count++;
        }
        /* 게임 정답 */

        /* 게임 끝 */
        else if (m[0].equals(gameEnd)) {
          System.out.println("[Client] 게임 끝!");
          client.grf.startBtn.setVisible(true);
          client.grf.answerBtn.setVisible(false);
          client.grf.skipBtn.setVisible(false);
          client.grf.ranImg.setIcon(null);
          client.grf.count = 1;
        }
        /* 게임 끝 */
      }

    } catch (Exception e) {
      System.out.println("[Client] Error : 메시지 받기 오류 > " + e.toString());
    }
  }

  /* 로그인 성공 여부를 확인하는 메소드 */
  void loginCheck(String _m) {
    if (_m.equals("OKAY")) {
      System.out.println("[Client] 로그인 성공 : 대기방 열럼 : 로그인 인터페이스 종료");
      client.lf.dispose();
      client.wrf.setVisible(true);
    }
  }


  /* 방 생성 여부를 확인하는 메소드 */
  void createRoom(String _m) {
    if (_m.equals("OKAY")) {
      System.out.println("[Client] 게임방 생성이 완료 됐습니다.");
      client.grf.setVisible(true);
      client.wrf.setVisible(false);
      client.grf.setTitle(client.wrf.roomName); // 생성한 방의 이름으로 들어간방이름설정.
      client.grf.host = true; // 방을 만든사람이면 Host가 된다.
    }
  }

  /* 접속 인원을 출력하는 메소드 */
  void viewCUser(String _m) {
    if (!_m.equals("")) {
      String[] user = _m.split("@");
      client.wrf.cuList.setListData(user);
    }
  }

  /* 방 목록을 출력하는 메소드 */
  void roomList(String _m) {
    if (!_m.equals("")) {
      String[] room = _m.split("@");
      client.wrf.rList.setListData(room);
    }
  }

  /* 방 입장 여부를 확인하는 메소드 */
  void enterRoom(String _m) {
    if (_m.equals("OKAY")) { // 방 입장에 성공 했다면.
      System.out.println("[Client] 게임방 입장 완료.");
      client.grf.setVisible(true);
      client.wrf.setVisible(false);
      client.grf.setTitle(client.wrf.selRoom); // 선택한 방의 이름으로 들어간 방이름 설정.
      client.grf.host = false; // 들어간사람은게스트
    }
  }

  /* 방 인원 목록을 출력하는 메소드 */
  void roomUser(String _m) {
    if (!_m.equals("")) {
      String[] user = _m.split("@");
      client.grf.userList.setListData(user);
    }
  }

  void exitRoom() {
    System.out.println("[Client] 게임방을 나갑니다.");
    client.grf.dispose();
    client.wrf.setVisible(true);
  }

  void setrandomImg(String _m) {
    System.out.println("[Client] 이미지를 랜덤하게 넣습니다!");
    System.out.println(_m);
    client.grf.ranImg.setIcon(new ImageIcon(_m));
    String image = _m;
    String answerTemp = null;
    if (image.indexOf("over") != -1) {
      answerTemp = image.substring(image.lastIndexOf("over"), image.lastIndexOf("."));
    }
    if (image.indexOf("lol") != -1) {
      answerTemp = image.substring(image.lastIndexOf("lol"), image.lastIndexOf("."));
    }
    if (image.indexOf("animation") != -1) {
      answerTemp = image.substring(image.lastIndexOf("animation"), image.lastIndexOf("."));
    }
    if (image.indexOf("animal") != -1) {
      answerTemp = image.substring(image.lastIndexOf("animal"), image.lastIndexOf("."));
    }
    if (image.indexOf("poke") != -1) {
      answerTemp = image.substring(image.lastIndexOf("poke"), image.lastIndexOf("."));
    }
    client.grf.ranAnswer.setText(switchString(answerTemp));
  }



  String switchString(String data) {
    String temp = data;
    switch (temp) {
      // 롤
      case "lolVeigar":
        temp = "베이가";
        break;
      case "lolAshe":
        temp = "애쉬";
        break;
      case "lolGalio":
        temp = "갈리오";
        break;
      case "lolJanna":
        temp = "잔나";
        break;
      case "lolKaisa":
        temp = "카이사";
        break;
      case "lolRakan":
        temp = "라칸";
        break;
      case "lolLuLu":
        temp = "룰루";
        break;
      case "lolMaokai":
        temp = "마오카이";
        break;
      case "lolTeemo":
        temp = "티모";
        break;
      case "lolThresh":
        temp = "쓰레쉬";
        break;
      case "lolViego":
        temp = "비에고";
        break;
      case "lolNocturn":
        temp = "녹턴";
        break;
      case "lolRiven":
        temp = "리븐";
        break;
      case "lolYuumi":
        temp = "유미";
        break;
      case "lolYorick":
        temp = "요릭";
        break;
      // 오버워치
      case "overWinston":
        temp = "윈스턴";
        break;
      case "overTracer":
        temp = "트레이서";
        break;
      case "overSombra":
        temp = "솜브라";
        break;
      case "overReaper":
        temp = "리퍼";
        break;
      case "overRack":
        temp = "레킹볼";
        break;
      case "overDva":
        temp = "디바";
        break;
      case "overEcho":
        temp = "에코";
        break;
      case "overAshe":
        temp = "애쉬";
        break;
      case "overDoom":
        temp = "둠피스트";
        break;
      case "overAna":
        temp = "아나";
        break;
      case "over76":
        temp = "솔져76";
        break;
      case "overGenji":
        temp = "겐지";
        break;
      case "overMercy":
        temp = "메르시";
        break;
      case "overOrisa":
        temp = "오리사";
        break;
      case "overHog":
        temp = "로드호그";
        break;
      // 동물
      case "animalAlpaca":
        temp = "알파카";
        break;
      case "animalCamel":
        temp = "낙타";
        break;
      case "animalCat":
        temp = "고양이";
        break;
      case "animalDuck":
        temp = "오리";
        break;
      case "animalHamster":
        temp = "햄스터";
        break;
      case "animalHorse":
        temp = "말";
        break;
      case "animalMeerkat":
        temp = "미어캣";
        break;
      case "animalMonkey":
        temp = "원숭이";
        break;
      case "animalOstrich":
        temp = "타조";
        break;
      case "animalPanda":
        temp = "판다";
        break;
      case "animalPig":
        temp = "돼지";
        break;
      case "animalPuppy":
        temp = "강아지";
        break;
      case "animalSheep":
        temp = "양";
        break;
      case "animalSquirrel":
        temp = "다람쥐";
        break;
      case "animalTiger":
        temp = "호랑이";
        break;
      // 애니메이션
      case "animationAgumon":
        temp = "아구몬";
        break;
      case "animationBisiri":
        temp = "비실이";
        break;
      case "animationBonobono":
        temp = "보노보노";
        break;
      case "animationBreadman":
        temp = "식빵맨";
        break;
      case "animationDdung":
        temp = "뚱이";
        break;
      case "animationDulli":
        temp = "둘리";
        break;
      case "animationInuyasha":
        temp = "이누야샤";
        break;
      case "animationKeroro":
        temp = "케로로";
        break;
      case "animationKonan":
        temp = "코난";
        break;
      case "animationLoopy":
        temp = "루피";
        break;
      case "animationNaruto":
        temp = "나루토";
        break;
      case "animationSonongong":
        temp = "손오공";
        break;
      case "animationTotoro":
        temp = "토토로";
        break;
      case "animationZoro":
        temp = "조로";
        break;
      case "animationZzanggu":
        temp = "짱구";
        break;
      // 포켓몬
      case "pokeChikorita":
        temp = "치코리타";
        break;
      case "pokeDdogas":
        temp = "또가스";
        break;
      case "pokeEvee":
        temp = "이브이";
        break;
      case "pokeFiry":
        temp = "파이리";
        break;
      case "pokeGorapaduck":
        temp = "고라파덕";
        break;
      case "pokeJammanbo":
        temp = "잠만보";
        break;
      case "pokeKkobugi":
        temp = "꼬부기";
        break;
      case "pokeKkojimo":
        temp = "꼬지모";
        break;
      case "pokeMangnanyong":
        temp = "망나뇽";
        break;
      case "pokeModapi":
        temp = "모다피";
        break;
      case "pokeNamujigi":
        temp = "나무지기";
        break;
      case "pokeNyaong":
        temp = "냐옹이";
        break;
      case "pokePikachu":
        temp = "피카츄";
        break;
      case "pokeTogepi":
        temp = "토게피";
        break;
      case "pokeYadoran":
        temp = "야도란";
        break;
      case "pokeYisanghaessi":
        temp = "이상해씨";
        break;
    }
    return temp;
  }
}
