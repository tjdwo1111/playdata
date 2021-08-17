package application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//�α��� ����� �����ϴ� �������̽�.
public class LoginFrame extends JFrame{

  /*Panel*/
  JPanel basePanel = new JPanel(new BorderLayout());
  JPanel centerPanel = new JPanel(new BorderLayout());
  JPanel westPanel = new JPanel();
  JPanel eastPanel = new JPanel();
  JPanel southPanel = new JPanel();
  
  /* Label */
  JLabel naL = new JLabel("�̸�");
  /* TextField */
  JTextField nameField = new JTextField();
  /* Button */
  JButton loginBtn = new JButton("��������");
  JButton exitBtn = new JButton("����");
  
  GameClient c = null;
  
  final String loginTag = "LOGIN";
  
  LoginFrame(GameClient _c) {
    c = _c;
    
    setTitle("�̸��� �Է��ؼ� �α��� �ϼ���!");
    
    /* Panel ũ�� �۾� */
    centerPanel.setPreferredSize(new Dimension(260, 80));
    westPanel.setPreferredSize(new Dimension(210, 75));
    eastPanel.setPreferredSize(new Dimension(90, 75));
    southPanel.setPreferredSize(new Dimension(290, 40));
    
    /* Label ũ�� �۾� */
    naL.setPreferredSize(new Dimension(50, 60));
    
    /* TextField ũ�� �۾� */
    nameField.setPreferredSize(new Dimension(140, 60));
    
    /* Button ũ�� �۾� */
    loginBtn.setPreferredSize(new Dimension(75, 63));
    exitBtn.setPreferredSize(new Dimension(135, 50));
    
    /* Panel �߰� �۾� */
    setContentPane(basePanel);  //panel�� �⺻ �����̳ʷ� ����
    
    basePanel.add(centerPanel, BorderLayout.CENTER);
    basePanel.add(southPanel, BorderLayout.SOUTH);
    centerPanel.add(westPanel, BorderLayout.WEST);
    centerPanel.add(eastPanel, BorderLayout.EAST);
    
    westPanel.setLayout(new FlowLayout());
    eastPanel.setLayout(new FlowLayout());
    southPanel.setLayout(new FlowLayout());
    
    /* westPanel ������Ʈ */
    westPanel.add(naL);
    westPanel.add(nameField);
    
    /* eastPanel ������Ʈ */
    eastPanel.add(loginBtn);
    
    /* southPanel ������Ʈ */
    southPanel.add(exitBtn);
    
    /* Button �̺�Ʈ ������ �߰� */
    ButtonListener bl = new ButtonListener();
    
    loginBtn.addActionListener(bl);
    exitBtn.addActionListener(bl);
    

    /* Key �̺�Ʈ ������ �߰� */
    KeyBoardListener kl = new KeyBoardListener();
    naL.addKeyListener(kl);
    setSize(310, 150);
    setLocationRelativeTo(null);
    setVisible(true);
    setResizable(false);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
  
  /* Button �̺�Ʈ ������ */
  class ButtonListener implements ActionListener{

    @Override
    public void actionPerformed(ActionEvent e) {
      JButton b = (JButton)e.getSource();
      
      /* TextField�� �Էµ� �̸��� ������ �ʱ�ȭ */
      String name = nameField.getText();
      
      /* �����ư �̺�Ʈ */
      if(b.getText().equals("����")) {
        System.out.println("[Client] ���� ����");
        System.exit(0);
      }
      
      else if(b.getText().equals("��������")) {
        if(name.equals("")) { // �̸� ���Է½� �������� ����
          JOptionPane.showMessageDialog(null, "�̸��� �Է��ϼž��� ������ �˴ϴ�!!!", "���� ����!", JOptionPane.ERROR_MESSAGE);
          System.out.println("[Client] �α��� ����!!! : �̸��� ���Է� ");
        } else if (!name.equals("")) { // ���� ������
          c.sendMsg(loginTag + "//" + name); // ������ ���� ������ �����Ѵ�!
        }
      }
    }
    
  } // ButtonEvent
  
  /* Key �̺�Ʈ ������ */
  class KeyBoardListener implements KeyListener{
    @Override
    public void keyPressed(KeyEvent e) {
      // Enter Key Event
      
      if(e.getKeyCode() == KeyEvent.VK_ENTER) {
        /* TextField�� �Էµ� �̸��� ������ �ʱ�ȭ */
        String name = nameField.getText();
        
        if(name.equals("")) { // �̸� ���Է½� �������� ����
          JOptionPane.showMessageDialog(null, "�̸��� �Է��ϼž��� ������ �˴ϴ�!!!", "���� ����!", JOptionPane.ERROR_MESSAGE);
          System.out.println("[Client] �α��� ����!!! : �̸��� ���Է� ");
        } else if (!name.equals("")) { // ���� ������
          c.sendMsg(loginTag + "//" + name); // ������ ���� ������ �����Ѵ�!
        }
      }
      
    }
    
    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
  }
}
