package application;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import application.LoginFrame.ButtonListener;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;

public class GameRoomFrame extends JFrame {
  /* TAG */
  final String chatTag = "CHAT";

  /* Panel */
  JPanel basePanel = new JPanel(new BorderLayout());
  JPanel centerPanel = new JPanel();
  JPanel eastPanel = new JPanel();
  JPanel centerImgPanel = new JPanel();
  JPanel eastChatPanel = new JPanel();



  /* Label */
  JLabel userListL = new JLabel("참가자 목록");
  JLabel ranImg = new JLabel();
  JLabel ranAnswer = new JLabel();

  /* Button */
  JButton startBtn = new JButton("게임시작");
  JButton skipBtn = new JButton("스킵");
  JButton exitBtn = new JButton("나가기");
  JButton sendChatBtn = new JButton("전송");
  JButton answerBtn = new JButton("정답");
  JButton bgmBtn = new JButton("Play Music");

  /* List */
  JList<String> userList = new JList<String>();

  /* TextField + TextArea(채팅구조) */
  JTextArea output = new JTextArea();
  JTextField chatField = new JTextField();
  JScrollPane scroll = new JScrollPane(output, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

  boolean host = false;
  final int answers = 15;
  int count = 1;
  int myCount = 0;

  GameClient c = null;
  String answer = ""; // 정답
  Clip clip = null; // 음악클립

  final String gameStart = "START"; // 게임시작
  final String gameEnd = "END"; // 게임 끝
  final String gameSkip = "SKIP"; // 게임 스킵
  final String gameAnswer = "ANSWER"; // 정답맞춤
  final String rexitTag = "REXIT"; // 게임방 퇴장

  public GameRoomFrame(GameClient _c) {
    c = _c;

    /* bgm 관련 작업 */
    try {
      URL file = new URL("https://www2.cs.uic.edu/~i101/SoundFiles/CantinaBand60.wav");
      AudioInputStream ais = AudioSystem.getAudioInputStream(file);
      clip = AudioSystem.getClip();
      clip.open(ais);
    } catch (LineUnavailableException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (UnsupportedAudioFileException e) {
      e.printStackTrace();
    }


    /* List 크기 작업 */
    userList.setPreferredSize(new Dimension(140, 50));

    /* Label 크기 작업 */
    userListL.setFont(new Font("맑은 고딕", Font.BOLD, 12));
    userListL.setPreferredSize(new Dimension(80, 20));
    userListL.setHorizontalAlignment(SwingConstants.CENTER);
    ranImg.setPreferredSize(new Dimension(800, 600));
    ranImg.setHorizontalAlignment(JLabel.CENTER);
    ranAnswer.setVisible(false);


    /* Button 크기 작업 */
    startBtn.setFont(new Font("맑은 고딕", Font.BOLD, 12));
    startBtn.setPreferredSize(new Dimension(90, 50));
    answerBtn.setFont(new Font("맑은 고딕", Font.BOLD, 12));
    answerBtn.setPreferredSize(new Dimension(90, 50));
    answerBtn.setVisible(false);
    skipBtn.setFont(new Font("맑은 고딕", Font.BOLD, 12));
    skipBtn.setVisible(false);
    skipBtn.setPreferredSize(new Dimension(235, 30));
    exitBtn.setFont(new Font("맑은 고딕", Font.BOLD, 12));
    exitBtn.setPreferredSize(new Dimension(235, 30));
    sendChatBtn.setFont(new Font("맑은 고딕", Font.BOLD, 12));
    sendChatBtn.setPreferredSize(new Dimension(70, 40));
    bgmBtn.setPreferredSize(new Dimension(100, 100));


    scroll.setPreferredSize(new Dimension(200,300));

    /* TextField 크기 작업 */
    chatField.setPreferredSize(new Dimension(200, 20));

    /* Panel 추가 작업 */
    setContentPane(basePanel); // panel을 기본 컨테이너로 설정

    centerPanel.setPreferredSize(new Dimension(625, 200));
    centerPanel.setLayout(new FlowLayout());

    eastPanel.setPreferredSize(new Dimension(250, 400));
    eastPanel.setLayout(new FlowLayout());


    centerPanel.setBackground(new Color(206, 167, 61));


    basePanel.add(centerPanel, BorderLayout.CENTER);
    basePanel.add(eastPanel, BorderLayout.EAST);

    centerPanel.add(centerImgPanel, BorderLayout.CENTER);
    centerImgPanel.add(ranImg);

    output.setEditable(false);
    

    eastPanel.add(userListL);
    eastPanel.add(userList);
    eastPanel.add(startBtn);
    eastPanel.add(answerBtn);
    eastPanel.add(skipBtn);
    eastPanel.add(exitBtn);
//    eastPanel.add(scroll);
    eastPanel.add(scroll);
    eastPanel.add(chatField);
    eastPanel.add(sendChatBtn);
    eastPanel.add(bgmBtn);


    /* Button 이벤트 리스너 추가 */
    ButtonListener bl = new ButtonListener();
    skipBtn.addActionListener(bl);
    startBtn.addActionListener(bl);
    exitBtn.addActionListener(bl);
    sendChatBtn.addActionListener(bl);
    answerBtn.addActionListener(bl);
    bgmBtn.addActionListener(bl);

    setSize(1112, 674);
    setResizable(false);
    setLocationRelativeTo(null);
  }

  /* Button 이벤트 리스너 */
  class ButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      JButton b = (JButton) e.getSource();

      /* 게임시작 버튼 이벤트 */
      if (b.getText().equals("게임시작")) {
        if (host == true) {
          System.out.println("[Client] 게임 시작! ");
          c.sendMsg(gameStart + "//");

        } else if (host == false) {
          JOptionPane.showMessageDialog(null, "HOST만 권한이 있습니다.", "권한이 없습니다.",
              JOptionPane.ERROR_MESSAGE);
          System.out.println("[Client] 게임시작 실패! : 호스트 권한 없음");
        }

      }

      /* 스킵하기 버튼 이벤트 */
      else if (b.getText().equals("스킵")) {
        if (host == true) {
          System.out.println("[Client] 게임 스킵!");
          c.sendMsg(gameSkip + "//");
          c.sendMsg(chatTag + "//" + "남은 문제는" + (answers - count) + "개입니다!.");
          if (answers - count == 0) {
            c.sendMsg(gameEnd + "//");
            c.sendMsg(chatTag + "//" + answers + "개의 문제가 끝나 게임 종료됩니다.");
          }
        } else if (host == false) {
          JOptionPane.showMessageDialog(null, "HOST만 권한이 있습니다.", "권한이 없습니다.",
              JOptionPane.ERROR_MESSAGE);
          System.out.println("[Client] 스킵 실패! : 호스트 권한 없음");
        }
      }

      /* 방 나가기 이벤트 */
      else if (b.getText().equals("나가기")) {
        c.sendMsg(rexitTag + "//");
      }

      /* 전송 버튼 이벤트 */
      else if (b.getText().equals("전송")) {
        String chatMsg = chatField.getText();

        if (chatMsg.equals("")) { // 채팅내용 입력 없을때
          System.out.println("[Client] 채팅 전송 실패!!! : 내용을 미입력 ");
        } else {// 채팅내용 입력 했을 때
          chatField.setText("");
          c.sendMsg(chatTag + "//" + chatMsg); // 서버에 채팅 정보를 전송한다!
        }
      }

      /* 정답 버튼 이벤트 */
      else if (b.getText().equals("정답")) {
        answer =
            JOptionPane.showInputDialog(null, "정답을 입력하세요 !", "정답", JOptionPane.QUESTION_MESSAGE);
        if (answer != "" && answer.equals(ranAnswer.getText())) {
          c.sendMsg(gameAnswer + "//");
          c.sendMsg(chatTag + "//" + "남은 문제는" + (answers - count) + "개입니다!.");
          System.out.println("[Client] 정답 : 정답을 입력했습니다.");

          if (answers - count == 0) {
            c.sendMsg(gameEnd + "//");
            c.sendMsg(chatTag + "//" + answers + "개의 문제가 끝나 게임 종료됩니다.");
          }

        } else if (answer == "") {
          JOptionPane.showMessageDialog(null, "정답을 입력하세요.", "에러", JOptionPane.ERROR_MESSAGE);
          System.out.println("[Client] 정답 입력 오류 : 정답을 입력하지 않음.");
        } else if (answer != "" && !answer.equals(ranAnswer.getText())) {
          JOptionPane.showMessageDialog(null, "오답입니다.", "오답", JOptionPane.ERROR_MESSAGE);
          System.out.println("[Client] 오답 : 입력값이 다릅니다.");
        }
      }
      /* 음악재생 이벤트 */
      else if (b.getText().equals("Play Music")) {
        clip.setFramePosition(0);
        clip.start();
      }
    }
  }
}
