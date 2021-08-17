package application;

import javax.swing.*;
import application.LoginFrame.ButtonListener;
import java.awt.*;
import java.awt.event.*;

// 대기실 기능을 하는 인터페이스!
public class WaitRoomFrame extends JFrame {

  /* Panel */
  JPanel basePanel = new JPanel(new BorderLayout());
  JPanel centerPanel = new JPanel();
  JPanel eastPanel = new JPanel();

  /* Label */
  JLabel roomListL = new JLabel("================ 게임방 목록 ================");
  JLabel cuListL = new JLabel("======= 접속 인원 =======");

  /* ScrollPane */
  JScrollPane rL_sp;
  JScrollPane cL_sp;

  /* List */
  JList<String> rList = new JList<String>();
  JList<String> cuList = new JList<String>();

  /* Button */
  JButton createRoom = new JButton("방 생성하기");
  JButton enterRoom = new JButton("방 입장하기");
  JButton exitGame = new JButton("게임 종료하기");

  String selRoom; // 선택된 방 제목
  String roomName; // 생성할 방 제목

  GameClient c = null;

  final String croomTag = "CROOM"; // 방 생성 기능 태그
  final String eroomTag = "EROOM"; // 방 입장 기능 태그
  final String pexitTag = "PEXIT"; // 프로그램 종료 기능 태그

  WaitRoomFrame(GameClient _c) {
    c = _c;

    setTitle("게임 대기실입니다!");

    /* Panel 크기 작업 */
    centerPanel.setPreferredSize(new Dimension(310, basePanel.getHeight()));
    eastPanel.setPreferredSize(new Dimension(180, basePanel.getHeight()));

    /* Label 크기 작업 */
    roomListL.setPreferredSize(new Dimension(290, 20));
    cuListL.setPreferredSize(new Dimension(160, 20));

    /* ScrollPane 크기 작업 */
    rL_sp = new JScrollPane(rList);
    cL_sp = new JScrollPane(cuList);
    rL_sp.setPreferredSize(new Dimension(300, 350));
    cL_sp.setPreferredSize(new Dimension(160, 188));

    /* Button 크기 작업 */
    createRoom.setPreferredSize(new Dimension(160, 35));
    enterRoom.setPreferredSize(new Dimension(160, 35));
    exitGame.setPreferredSize(new Dimension(160, 35));

    /* Panel 추가 작업 */
    setContentPane(basePanel); // panel을 기본 컨테이너로 설정

    basePanel.add(centerPanel, BorderLayout.CENTER);
    basePanel.add(eastPanel, BorderLayout.EAST);

    centerPanel.setLayout(new FlowLayout());
    eastPanel.setLayout(new FlowLayout());

    centerPanel.add(roomListL);
    centerPanel.add(rL_sp);

    /* eastPanel 컴포넌트 */
    eastPanel.add(cuListL);
    eastPanel.add(cL_sp);
    eastPanel.add(createRoom);
    eastPanel.add(enterRoom);
    eastPanel.add(exitGame);

    /* 버튼 이벤트 리스너 추가 */
    ButtonListener b1 = new ButtonListener();

    createRoom.addActionListener(b1);
    enterRoom.addActionListener(b1);
    exitGame.addActionListener(b1);

    /* 마우스 이벤트 리스너 추가 */
    rList.addMouseListener(new MouseListener() {

      @Override
      public void mouseReleased(MouseEvent e) {}

      public void mousePressed(MouseEvent e) {}

      public void mouseExited(MouseEvent e) {}

      public void mouseEntered(MouseEvent e) {}

      public void mouseClicked(MouseEvent e) {
        if (!rList.isSelectionEmpty()) {
          String[] m = rList.getSelectedValue().split(" : ");
          selRoom = m[0];
        }
      }
    });

    setSize(510, 450);
    setLocationRelativeTo(null);
    setResizable(false);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  /* Button 이벤트 리스너 */
  class ButtonListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      JButton b = (JButton) e.getSource();

      /* 방 생성하기 이벤트 */
      if (b.getText().equals("방 생성하기")) {
        // 생성할 방의 제목을 입력받는다.
        roomName = JOptionPane.showInputDialog(null, "생성할 방 제목을 입력하세요.", "게임방 생성",
            JOptionPane.QUESTION_MESSAGE);

        if (roomName != null) { // roomName이 null이 아니면 서버에 "Tag//방이름" 형태로 메시지를 전송
          c.sendMsg(croomTag + "//" + roomName);
        } else { // null이면 생성 실패로 dialog 출력
          JOptionPane.showMessageDialog(null, "방 제목을 입력하셔야지 생성됩니다.", "게임방 생성 실패",
              JOptionPane.ERROR_MESSAGE);
          System.out.println("[Client] 방 생성 오류 : 방 제목을 입력을 안해서 오류발생.");
        }
      }
      /* 방 입장하기 버튼 이벤트 */
      else if (b.getText().equals("방 입장하기")) {
        if (selRoom != null) { // selRoom이 null이 아니면 서버에 "Tag//방이름" 형태의 메시지를 전송
          c.sendMsg(eroomTag + "//" + selRoom);
        } else { // null이면 입장 실패로 dialog 출력
          JOptionPane.showMessageDialog(null, "입장할 방을 선택하셔야지 입장됩니다.", "게임방 입장실패",
              JOptionPane.ERROR_MESSAGE);
          System.out.println("[Client] 입장 오류 : 방이 선택되지 않거나 올바르지 않은 값을 선택함.");
        }
      }
      /* 게임 종료하기 버튼 이벤트 */
      else if (b.getText().equals("게임 종료하기")) {
        System.out.println("[Client] 게임 종료.");
        c.sendMsg(pexitTag + "//"); // 서버에 프로그램이 종료됐다는 태그를 전송
        System.exit(0);
      }
    }
  }
}
