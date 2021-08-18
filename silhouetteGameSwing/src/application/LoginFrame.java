package application;

import javax.swing.*;

import application.LoginFrame.ButtonListener;
import application.LoginFrame.KeyBoardListener;

import java.awt.*;
import java.awt.event.*;

// 로그인 기능을 수행하는 인터페이스.
public class LoginFrame extends JFrame {

  /* Panel */
  JPanel basePanel = new JPanel(new BorderLayout());
  JPanel centerPanel = new JPanel();

  GameClient c = null;

  final String loginTag = "LOGIN";

  JButton loginBtn = new JButton("대기실 입장");
  JLabel naL = new JLabel("이름");
  JTextField nameField = new JTextField();
  JButton exitBtn = new JButton("종료");

  LoginFrame(GameClient _c) {
    c = _c;
    nameField.setBackground(new Color(230, 230, 250));

    nameField.setBounds(85, 53, 226, 38);
    nameField.setColumns(10);

    setTitle("이름을 입력해서 로그인 하세요!");

    /* Panel 크기 작업 */
    centerPanel.setBackground(Color.PINK);
    centerPanel.setPreferredSize(new Dimension(260, 80));
    loginBtn.setBackground(new Color(230, 230, 250));
    loginBtn.setFont(new Font("맑은 고딕", Font.BOLD, 12));
    loginBtn.setBounds(85, 120, 107, 33);
    naL.setFont(new Font("맑은 고딕", Font.BOLD, 14));
    naL.setForeground(Color.BLACK);
    naL.setHorizontalAlignment(SwingConstants.CENTER);
    naL.setBounds(23, 53, 62, 35);
    exitBtn.setBackground(new Color(230, 230, 250));
    exitBtn.setFont(new Font("맑은 고딕", Font.BOLD, 12));
    exitBtn.setBounds(204, 120, 107, 33);

    setContentPane(basePanel); // panel을 기본 컨테이너로 설정

    basePanel.add(centerPanel, BorderLayout.CENTER);
    centerPanel.setLayout(null);
    loginBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {}
    });

    /* Panel 추가 작업 */
    centerPanel.add(loginBtn);
    centerPanel.add(naL);
    centerPanel.add(nameField);
    centerPanel.add(exitBtn);



    exitBtn.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {}
    });

    /* Button 이벤트 리스너 추가 */
    ButtonListener bl = new ButtonListener();

    loginBtn.addActionListener(bl);
    exitBtn.addActionListener(bl);


    /* Key 이벤트 리스너 추가 */
    KeyBoardListener kl = new KeyBoardListener();
    naL.addKeyListener(kl);
    setSize(370, 192);
    setLocationRelativeTo(null);
    setVisible(true);
    setResizable(false);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  /* Button 이벤트 리스너 */
  class ButtonListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      JButton b = (JButton) e.getSource();

      /* TextField에 입력된 이름을 변수에 초기화 */
      String name = nameField.getText();

      /* 종료버튼 이벤트 */
      if (b.getText().equals("종료")) {
        System.out.println("[Client] 게임 종료");
        System.exit(0);
      }

      else if (b.getText().equals("대기실 입장")) {
        if (name.equals("")) { // 이름 미입력시 대기실입장 실패
          JOptionPane.showMessageDialog(null, "이름을 입력하셔야지 입장이 됩니다!!!", "입장 실패!",
              JOptionPane.ERROR_MESSAGE);
          System.out.println("[Client] 로그인 실패!!! : 이름을 미입력 ");
        } else if (!name.equals("")) { // 입장 성공시
          c.sendMsg(loginTag + "//" + name); // 서버에 입장 정보를 전송한다!
        }
      }
    }
  }// ButtonEvent

  /* Key 이벤트 리스너 */
  class KeyBoardListener implements KeyListener {
    @Override
    public void keyPressed(KeyEvent e) {
      // Enter Key Event

      if (e.getKeyCode() == KeyEvent.VK_ENTER) {
        /* TextField에 입력된 이름을 변수에 초기화 */
        String name = nameField.getText();

        if (name.equals("")) { // 이름 미입력시 대기실입장 실패
          JOptionPane.showMessageDialog(null, "이름을 입력하셔야지 입장이 됩니다!!!", "입장 실패!",
              JOptionPane.ERROR_MESSAGE);
          System.out.println("[Client] 로그인 실패!!! : 이름을 미입력 ");
        } else if (!name.equals("")) { // 입장 성공시
          c.sendMsg(loginTag + "//" + name); // 서버에 입장 정보를 전송한다!
        }
      }

    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
  }
}
