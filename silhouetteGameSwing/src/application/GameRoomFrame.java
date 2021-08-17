package application;

import javax.swing.*;
import application.LoginFrame.ButtonListener;
import java.awt.*;
import java.awt.event.*;

public class GameRoomFrame extends JFrame {

  /* Panel */
  JPanel basePanel = new JPanel(new BorderLayout());
  JPanel centerPanel = new JPanel();
  JPanel eastPanel = new JPanel();


  /* Label */
  JLabel userListL = new JLabel("참가자 목록");

  /* Button */
  JButton startBtn = new JButton("게임시작");
  JButton skipBtn = new JButton("스킵");

  /* List */
  JList<String> userList = new JList<String>();

  boolean host = false;
  final int answer = 15;
  int count = 0;
  int myCount = 0;

  GameClient c = null;

  final String gameStart = "START"; // 게임시작
  final String gameEnd = "END"; // 게임 끝
  final String gameSkip = "SKIP"; // 게임 스킵

  public GameRoomFrame(GameClient _c) {
    c = _c;

    /* List 크기 작업 */
    userList.setPreferredSize(new Dimension(140, 50));

    /* Label 크기 작업 */
    userListL.setPreferredSize(new Dimension(80, 20));
    userListL.setHorizontalAlignment(JLabel.LEFT);

    /* Button 크기 작업 */
    startBtn.setPreferredSize(new Dimension(90, 50));
    skipBtn.setPreferredSize(new Dimension(235, 30));

    /* Panel 추가 작업 */
    setContentPane(basePanel); // panel을 기본 컨테이너로 설정

    centerPanel.setPreferredSize(new Dimension(625, 652));
    centerPanel.setLayout(new FlowLayout());

    eastPanel.setPreferredSize(new Dimension(250, 652));
    eastPanel.setLayout(new FlowLayout());

    centerPanel.setBackground(new Color(206, 167, 61));
    centerPanel.setLayout(null);

    basePanel.add(centerPanel, BorderLayout.CENTER);
    basePanel.add(eastPanel, BorderLayout.EAST);

    eastPanel.add(userListL);
    eastPanel.add(userList);
    eastPanel.add(startBtn);
    eastPanel.add(skipBtn);

    /* Button 이벤트 리스너 추가 */
    ButtonListener bl = new ButtonListener();
    skipBtn.addActionListener(bl);
    startBtn.addActionListener(bl);

    setSize(885, 652);
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
        if(host == true) {
          System.out.println("게임시작!");
        }else if(host == false) {
          JOptionPane.showMessageDialog(null, "HOST만 권한이 있습니다.", "권한이 없습니다.",JOptionPane.ERROR_MESSAGE);
          System.out.println("[Client] 게임시작 실패! : 호스트 권한 없음");
        }
        
      }

      /* 기권하기 버튼 이벤트 */
      else if (b.getText().equals("스킵")) {
        if(host == true) {
          System.out.println("스킵!");
        }else if(host == false) {
          JOptionPane.showMessageDialog(null, "HOST만 권한이 있습니다.", "권한이 없습니다.",JOptionPane.ERROR_MESSAGE);
          System.out.println("[Client] 스킵 실패! : 호스트 권한 없음");
        }
      }
    }
  }
}
