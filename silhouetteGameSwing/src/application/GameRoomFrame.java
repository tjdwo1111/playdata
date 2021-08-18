package application;

import javax.swing.*;
import application.LoginFrame.ButtonListener;
import java.awt.*;
import java.awt.event.*;

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
  JLabel userListL = new JLabel("������ ���");
  JLabel ranImg = new JLabel();
  JLabel ranAnswer = new JLabel();

  /* Button */
  JButton startBtn = new JButton("���ӽ���");
  JButton skipBtn = new JButton("��ŵ");
  JButton exitBtn = new JButton("������");
  JButton sendChatBtn = new JButton("����");
  JButton answerBtn = new JButton("����");

  /* List */
  JList<String> userList = new JList<String>();

  /* TextField + TextArea(ä�ñ���) */
  JTextArea output = new JTextArea();
  JTextField chatField = new JTextField();
  JScrollPane scroll = new JScrollPane(output);

  boolean host = false;
  final int answers = 15;
  int count = 0;
  int myCount = 0;

  GameClient c = null;
  String answer = ""; // ����

  final String gameStart = "START"; // ���ӽ���
  final String gameEnd = "END"; // ���� ��
  final String gameSkip = "SKIP"; // ���� ��ŵ
  final String gameAnswer = "ANSWER"; // �������
  final String rexitTag = "REXIT"; // ���ӹ� ����

  public GameRoomFrame(GameClient _c) {
    c = _c;

    /* List ũ�� �۾� */
    userList.setPreferredSize(new Dimension(140, 50));

    /* Label ũ�� �۾� */
    userListL.setPreferredSize(new Dimension(80, 20));
    userListL.setHorizontalAlignment(JLabel.LEFT);
    ranImg.setPreferredSize(new Dimension(500, 500));
    ranImg.setHorizontalAlignment(JLabel.CENTER);
    ranAnswer.setPreferredSize(new Dimension(50, 50));
    ranAnswer.setHorizontalAlignment(JLabel.RIGHT);

    /* Button ũ�� �۾� */
    startBtn.setPreferredSize(new Dimension(90, 50));
    answerBtn.setPreferredSize(new Dimension(90, 50));
    answerBtn.setVisible(false);
    skipBtn.setVisible(false);
    skipBtn.setPreferredSize(new Dimension(235, 30));
    exitBtn.setPreferredSize(new Dimension(235, 30));
    sendChatBtn.setPreferredSize(new Dimension(40, 30));
    sendChatBtn.setHorizontalAlignment(JLabel.RIGHT);



    /* TextField ũ�� �۾� */
    chatField.setPreferredSize(new Dimension(200, 20));

    /* Panel �߰� �۾� */
    setContentPane(basePanel); // panel�� �⺻ �����̳ʷ� ����

    centerPanel.setPreferredSize(new Dimension(625, 652));
    centerPanel.setLayout(new FlowLayout());

    eastPanel.setPreferredSize(new Dimension(250, 652));
    eastPanel.setLayout(new FlowLayout());

    eastChatPanel.setPreferredSize(new Dimension(250, 300));
    eastChatPanel.setLayout(new FlowLayout());

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

    eastPanel.add(eastChatPanel, new FlowLayout());
    // eastChatPanel.add(output);
    eastChatPanel.add(scroll);
    eastChatPanel.add(chatField);
    eastChatPanel.add(sendChatBtn);
    eastChatPanel.add(ranAnswer);


    /* Button �̺�Ʈ ������ �߰� */
    ButtonListener bl = new ButtonListener();
    skipBtn.addActionListener(bl);
    startBtn.addActionListener(bl);
    exitBtn.addActionListener(bl);
    sendChatBtn.addActionListener(bl);
    answerBtn.addActionListener(bl);

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
        if (host == true) {
          System.out.println("[Client] ���� ����! ");
          c.sendMsg(gameStart + "//");

        } else if (host == false) {
          JOptionPane.showMessageDialog(null, "HOST�� ������ �ֽ��ϴ�.", "������ �����ϴ�.",
              JOptionPane.ERROR_MESSAGE);
          System.out.println("[Client] ���ӽ��� ����! : ȣ��Ʈ ���� ����");
        }

      }

      /* ��ŵ�ϱ� ��ư �̺�Ʈ */
      else if (b.getText().equals("��ŵ")) {
        if (host == true) {
          System.out.println("[Client] ���� ��ŵ!");
          c.sendMsg(gameSkip + "//");
          c.sendMsg(chatTag + "//" + "���� ������" + (answers - count) + "���Դϴ�!.");
          if (answers - count == 0) {
            c.sendMsg(gameEnd + "//");
            c.sendMsg(chatTag + "//" + answers + "���� ������ ���� ���� ����˴ϴ�.");
          }
        } else if (host == false) {
          JOptionPane.showMessageDialog(null, "HOST�� ������ �ֽ��ϴ�.", "������ �����ϴ�.",
              JOptionPane.ERROR_MESSAGE);
          System.out.println("[Client] ��ŵ ����! : ȣ��Ʈ ���� ����");
        }
      }

      /* �� ������ �̺�Ʈ */
      else if (b.getText().equals("������")) {
        c.sendMsg(rexitTag + "//");
      }

      /* ���� ��ư �̺�Ʈ */
      else if (b.getText().equals("����")) {
        String chatMsg = chatField.getText();

        if (chatMsg.equals("")) { // ä�ó��� �Է� ������
          System.out.println("[Client] ä�� ���� ����!!! : ������ ���Է� ");
        } else {// ä�ó��� �Է� ���� ��
          chatField.setText("");
          c.sendMsg(chatTag + "//" + chatMsg); // ������ ä�� ������ �����Ѵ�!
        }
      }

      /* ���� ��ư �̺�Ʈ */
      else if (b.getText().equals("����")) {
        answer =
            JOptionPane.showInputDialog(null, "������ �Է��ϼ��� !", "����", JOptionPane.QUESTION_MESSAGE);
        if (answer != "" && answer.equals(ranAnswer.getText())) {
          c.sendMsg(gameAnswer + "//");
          c.sendMsg(chatTag + "//" + "���� ������" + (answers - count) + "���Դϴ�!.");
          System.out.println("[Client] ���� : ������ �Է��߽��ϴ�.");

          if (answers - count == 0) {
            c.sendMsg(gameEnd + "//");
            c.sendMsg(chatTag + "//" + answers + "���� ������ ���� ���� ����˴ϴ�.");
          }

        } else if (answer == "") {
          JOptionPane.showMessageDialog(null, "������ �Է��ϼ���.", "����", JOptionPane.ERROR_MESSAGE);
          System.out.println("[Client] ���� �Է� ���� : ������ �Է����� ����.");
        } else if (answer != "" && !answer.equals(ranAnswer.getText())) {
          JOptionPane.showMessageDialog(null, "�����Դϴ�.", "����", JOptionPane.ERROR_MESSAGE);
          System.out.println("[Client] ���� : �Է°��� �ٸ��ϴ�.");
        }
      }
    }
  }
}
