package application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//로그인 기능을 수행하는 인터페이스.
public class LoginFrame extends JFrame{

  /*Panel*/
  JPanel basePanel = new JPanel(new BorderLayout());
  JPanel centerPanel = new JPanel(new BorderLayout());
  JPanel westPanel = new JPanel();
  JPanel eastPanel = new JPanel();
  JPanel southPanel = new JPanel();
  
  /* Label */
  JLabel naL = new JLabel("이름");
  /* TextField */
  JTextField nameField = new JTextField();
  /* Button */
  JButton loginBtn = new JButton("대기실입장");
  JButton exitBtn = new JButton("종료");
  
  GameClient c = null;
  
  final String loginTag = "LOGIN";
  
  LoginFrame(GameClient _c) {
    c = _c;
    
    setTitle("이름을 입력해서 로그인 하세요!");
    
    /* Panel 크기 작업 */
    centerPanel.setPreferredSize(new Dimension(260, 80));
    westPanel.setPreferredSize(new Dimension(210, 75));
    eastPanel.setPreferredSize(new Dimension(90, 75));
    southPanel.setPreferredSize(new Dimension(290, 40));
    
    /* Label 크기 작업 */
    naL.setPreferredSize(new Dimension(50, 60));
    
    /* TextField 크기 작업 */
    nameField.setPreferredSize(new Dimension(140, 60));
    
    /* Button 크기 작업 */
    loginBtn.setPreferredSize(new Dimension(75, 63));
    exitBtn.setPreferredSize(new Dimension(135, 50));
    
    /* Panel 추가 작업 */
    setContentPane(basePanel);  //panel을 기본 컨테이너로 설정
    
    basePanel.add(centerPanel, BorderLayout.CENTER);
    basePanel.add(southPanel, BorderLayout.SOUTH);
    centerPanel.add(westPanel, BorderLayout.WEST);
    centerPanel.add(eastPanel, BorderLayout.EAST);
    
    westPanel.setLayout(new FlowLayout());
    eastPanel.setLayout(new FlowLayout());
    southPanel.setLayout(new FlowLayout());
    
    /* westPanel 컴포넌트 */
    westPanel.add(naL);
    westPanel.add(nameField);
    
    /* eastPanel 컴포넌트 */
    eastPanel.add(loginBtn);
    
    /* southPanel 컴포넌트 */
    southPanel.add(exitBtn);
    
    /* Button 이벤트 리스너 추가 */
    ButtonListener bl = new ButtonListener();
    
    loginBtn.addActionListener(bl);
    exitBtn.addActionListener(bl);
    

    /* Key 이벤트 리스너 추가 */
    KeyBoardListener kl = new KeyBoardListener();
    naL.addKeyListener(kl);
    setSize(310, 150);
    setLocationRelativeTo(null);
    setVisible(true);
    setResizable(false);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
  
  /* Button 이벤트 리스너 */
  class ButtonListener implements ActionListener{

    @Override
    public void actionPerformed(ActionEvent e) {
      JButton b = (JButton)e.getSource();
      
      /* TextField에 입력된 이름을 변수에 초기화 */
      String name = nameField.getText();
      
      /* 종료버튼 이벤트 */
      if(b.getText().equals("종료")) {
        System.out.println("[Client] 게임 종료");
        System.exit(0);
      }
      
      else if(b.getText().equals("대기실입장")) {
        if(name.equals("")) { // 이름 미입력시 대기실입장 실패
          JOptionPane.showMessageDialog(null, "이름을 입력하셔야지 입장이 됩니다!!!", "입장 실패!", JOptionPane.ERROR_MESSAGE);
          System.out.println("[Client] 로그인 실패!!! : 이름을 미입력 ");
        } else if (!name.equals("")) { // 입장 성공시
          c.sendMsg(loginTag + "//" + name); // 서버에 입장 정보를 전송한다!
        }
      }
    }
    
  } // ButtonEvent
  
  /* Key 이벤트 리스너 */
  class KeyBoardListener implements KeyListener{
    @Override
    public void keyPressed(KeyEvent e) {
      // Enter Key Event
      
      if(e.getKeyCode() == KeyEvent.VK_ENTER) {
        /* TextField에 입력된 이름을 변수에 초기화 */
        String name = nameField.getText();
        
        if(name.equals("")) { // 이름 미입력시 대기실입장 실패
          JOptionPane.showMessageDialog(null, "이름을 입력하셔야지 입장이 됩니다!!!", "입장 실패!", JOptionPane.ERROR_MESSAGE);
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
