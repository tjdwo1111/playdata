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
  JLabel userListL = new JLabel("������ ���");

  /* Button */
  JButton startBtn = new JButton("���ӽ���");
  JButton skipBtn = new JButton("��ŵ");

  /* List */
  JList<String> userList = new JList<String>();

  boolean host = false;
  final int answer = 15;
  int count = 0;
  int myCount = 0;

  GameClient c = null;

  final String gameStart = "START"; // ���ӽ���
  final String gameEnd = "END"; // ���� ��
  final String gameSkip = "SKIP"; // ���� ��ŵ

  public GameRoomFrame(GameClient _c) {
    c = _c;

    /* List ũ�� �۾� */
    userList.setPreferredSize(new Dimension(140, 50));

    /* Label ũ�� �۾� */
    userListL.setPreferredSize(new Dimension(80, 20));
    userListL.setHorizontalAlignment(JLabel.LEFT);

    /* Button ũ�� �۾� */
    startBtn.setPreferredSize(new Dimension(90, 50));
    skipBtn.setPreferredSize(new Dimension(235, 30));

    /* Panel �߰� �۾� */
    setContentPane(basePanel); // panel�� �⺻ �����̳ʷ� ����

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

    /* Button �̺�Ʈ ������ �߰� */
    ButtonListener bl = new ButtonListener();
    skipBtn.addActionListener(bl);
    startBtn.addActionListener(bl);

    setSize(885, 652);
    setResizable(false);
    setLocationRelativeTo(null);
  }

  /* Button �̺�Ʈ ������ */
  class ButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      JButton b = (JButton) e.getSource();

      /* ���ӽ��� ��ư �̺�Ʈ */
      if (b.getText().equals("���ӽ���")) {
        if(host == true) {
          System.out.println("���ӽ���!");
        }else if(host == false) {
          JOptionPane.showMessageDialog(null, "HOST�� ������ �ֽ��ϴ�.", "������ �����ϴ�.",JOptionPane.ERROR_MESSAGE);
          System.out.println("[Client] ���ӽ��� ����! : ȣ��Ʈ ���� ����");
        }
        
      }

      /* ����ϱ� ��ư �̺�Ʈ */
      else if (b.getText().equals("��ŵ")) {
        if(host == true) {
          System.out.println("��ŵ!");
        }else if(host == false) {
          JOptionPane.showMessageDialog(null, "HOST�� ������ �ֽ��ϴ�.", "������ �����ϴ�.",JOptionPane.ERROR_MESSAGE);
          System.out.println("[Client] ��ŵ ����! : ȣ��Ʈ ���� ����");
        }
      }
    }
  }
}
