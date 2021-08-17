package application;

import javax.swing.*;
import application.LoginFrame.ButtonListener;
import java.awt.*;
import java.awt.event.*;

// ���� ����� �ϴ� �������̽�!
public class WaitRoomFrame extends JFrame {

  /* Panel */
  JPanel basePanel = new JPanel(new BorderLayout());
  JPanel centerPanel = new JPanel();
  JPanel eastPanel = new JPanel();

  /* Label */
  JLabel roomListL = new JLabel("================ ���ӹ� ��� ================");
  JLabel cuListL = new JLabel("======= ���� �ο� =======");

  /* ScrollPane */
  JScrollPane rL_sp;
  JScrollPane cL_sp;

  /* List */
  JList<String> rList = new JList<String>();
  JList<String> cuList = new JList<String>();

  /* Button */
  JButton createRoom = new JButton("�� �����ϱ�");
  JButton enterRoom = new JButton("�� �����ϱ�");
  JButton exitGame = new JButton("���� �����ϱ�");

  String selRoom; // ���õ� �� ����
  String roomName; // ������ �� ����

  GameClient c = null;

  final String croomTag = "CROOM"; // �� ���� ��� �±�
  final String eroomTag = "EROOM"; // �� ���� ��� �±�
  final String pexitTag = "PEXIT"; // ���α׷� ���� ��� �±�

  WaitRoomFrame(GameClient _c) {
    c = _c;

    setTitle("���� �����Դϴ�!");

    /* Panel ũ�� �۾� */
    centerPanel.setPreferredSize(new Dimension(310, basePanel.getHeight()));
    eastPanel.setPreferredSize(new Dimension(180, basePanel.getHeight()));

    /* Label ũ�� �۾� */
    roomListL.setPreferredSize(new Dimension(290, 20));
    cuListL.setPreferredSize(new Dimension(160, 20));

    /* ScrollPane ũ�� �۾� */
    rL_sp = new JScrollPane(rList);
    cL_sp = new JScrollPane(cuList);
    rL_sp.setPreferredSize(new Dimension(300, 350));
    cL_sp.setPreferredSize(new Dimension(160, 188));

    /* Button ũ�� �۾� */
    createRoom.setPreferredSize(new Dimension(160, 35));
    enterRoom.setPreferredSize(new Dimension(160, 35));
    exitGame.setPreferredSize(new Dimension(160, 35));

    /* Panel �߰� �۾� */
    setContentPane(basePanel); // panel�� �⺻ �����̳ʷ� ����

    basePanel.add(centerPanel, BorderLayout.CENTER);
    basePanel.add(eastPanel, BorderLayout.EAST);

    centerPanel.setLayout(new FlowLayout());
    eastPanel.setLayout(new FlowLayout());

    centerPanel.add(roomListL);
    centerPanel.add(rL_sp);

    /* eastPanel ������Ʈ */
    eastPanel.add(cuListL);
    eastPanel.add(cL_sp);
    eastPanel.add(createRoom);
    eastPanel.add(enterRoom);
    eastPanel.add(exitGame);

    /* ��ư �̺�Ʈ ������ �߰� */
    ButtonListener b1 = new ButtonListener();

    createRoom.addActionListener(b1);
    enterRoom.addActionListener(b1);
    exitGame.addActionListener(b1);

    /* ���콺 �̺�Ʈ ������ �߰� */
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

  /* Button �̺�Ʈ ������ */
  class ButtonListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {
      JButton b = (JButton) e.getSource();

      /* �� �����ϱ� �̺�Ʈ */
      if (b.getText().equals("�� �����ϱ�")) {
        // ������ ���� ������ �Է¹޴´�.
        roomName = JOptionPane.showInputDialog(null, "������ �� ������ �Է��ϼ���.", "���ӹ� ����",
            JOptionPane.QUESTION_MESSAGE);

        if (roomName != null) { // roomName�� null�� �ƴϸ� ������ "Tag//���̸�" ���·� �޽����� ����
          c.sendMsg(croomTag + "//" + roomName);
        } else { // null�̸� ���� ���з� dialog ���
          JOptionPane.showMessageDialog(null, "�� ������ �Է��ϼž��� �����˴ϴ�.", "���ӹ� ���� ����",
              JOptionPane.ERROR_MESSAGE);
          System.out.println("[Client] �� ���� ���� : �� ������ �Է��� ���ؼ� �����߻�.");
        }
      }
      /* �� �����ϱ� ��ư �̺�Ʈ */
      else if (b.getText().equals("�� �����ϱ�")) {
        if (selRoom != null) { // selRoom�� null�� �ƴϸ� ������ "Tag//���̸�" ������ �޽����� ����
          c.sendMsg(eroomTag + "//" + selRoom);
        } else { // null�̸� ���� ���з� dialog ���
          JOptionPane.showMessageDialog(null, "������ ���� �����ϼž��� ����˴ϴ�.", "���ӹ� �������",
              JOptionPane.ERROR_MESSAGE);
          System.out.println("[Client] ���� ���� : ���� ���õ��� �ʰų� �ùٸ��� ���� ���� ������.");
        }
      }
      /* ���� �����ϱ� ��ư �̺�Ʈ */
      else if (b.getText().equals("���� �����ϱ�")) {
        System.out.println("[Client] ���� ����.");
        c.sendMsg(pexitTag + "//"); // ������ ���α׷��� ����ƴٴ� �±׸� ����
        System.exit(0);
      }
    }
  }
}
