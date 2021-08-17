package application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//·Î±×ÀÎ ±â´ÉÀ» ¼öÇàÇÏ´Â ÀÎÅÍÆäÀÌ½º.
public class LoginFrame extends JFrame{
<<<<<<< HEAD
 
=======

>>>>>>> parent of 4647299 (ì´ë¯¸ì§€ ì¶œë ¥ì‹œ ì¤‘ë³µì œê±°ì¶”ê°€)
  /*Panel*/
  JPanel basePanel = new JPanel(new BorderLayout());
  JPanel centerPanel = new JPanel(new BorderLayout());
  JPanel westPanel = new JPanel();
  JPanel eastPanel = new JPanel();
  JPanel southPanel = new JPanel();
  
  /* Label */
  JLabel naL = new JLabel("ÀÌ¸§");
  /* TextField */
  JTextField nameField = new JTextField();
  /* Button */
  JButton loginBtn = new JButton("´ë±â½ÇÀÔÀå");
  JButton exitBtn = new JButton("Á¾·á");
  
  GameClient c = null;
  
  final String loginTag = "LOGIN";
  
  LoginFrame(GameClient _c) {
    c = _c;
    
    setTitle("ÀÌ¸§À» ÀÔ·ÂÇØ¼­ ·Î±×ÀÎ ÇÏ¼¼¿ä!");
    
    /* Panel Å©±â ÀÛ¾÷ */
    centerPanel.setPreferredSize(new Dimension(260, 80));
    westPanel.setPreferredSize(new Dimension(210, 75));
    eastPanel.setPreferredSize(new Dimension(90, 75));
    southPanel.setPreferredSize(new Dimension(290, 40));
    
    /* Label Å©±â ÀÛ¾÷ */
    naL.setPreferredSize(new Dimension(50, 60));
    
    /* TextField Å©±â ÀÛ¾÷ */
    nameField.setPreferredSize(new Dimension(140, 60));
    
    /* Button Å©±â ÀÛ¾÷ */
    loginBtn.setPreferredSize(new Dimension(75, 63));
    exitBtn.setPreferredSize(new Dimension(135, 50));
    
    /* Panel Ãß°¡ ÀÛ¾÷ */
    setContentPane(basePanel);  //panelÀ» ±âº» ÄÁÅ×ÀÌ³Ê·Î ¼³Á¤
    
    basePanel.add(centerPanel, BorderLayout.CENTER);
    basePanel.add(southPanel, BorderLayout.SOUTH);
    centerPanel.add(westPanel, BorderLayout.WEST);
    centerPanel.add(eastPanel, BorderLayout.EAST);
    
    westPanel.setLayout(new FlowLayout());
    eastPanel.setLayout(new FlowLayout());
    southPanel.setLayout(new FlowLayout());
    
    /* westPanel ÄÄÆ÷³ÍÆ® */
    westPanel.add(naL);
    westPanel.add(nameField);
    
    /* eastPanel ÄÄÆ÷³ÍÆ® */
    eastPanel.add(loginBtn);
    
    /* southPanel ÄÄÆ÷³ÍÆ® */
    southPanel.add(exitBtn);
    
    /* Button ÀÌº¥Æ® ¸®½º³Ê Ãß°¡ */
    ButtonListener bl = new ButtonListener();
    
    loginBtn.addActionListener(bl);
    exitBtn.addActionListener(bl);
    

    /* Key ÀÌº¥Æ® ¸®½º³Ê Ãß°¡ */
    KeyBoardListener kl = new KeyBoardListener();
    naL.addKeyListener(kl);
    setSize(310, 150);
    setLocationRelativeTo(null);
    setVisible(true);
    setResizable(false);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
  
  /* Button ÀÌº¥Æ® ¸®½º³Ê */
  class ButtonListener implements ActionListener{

    @Override
    public void actionPerformed(ActionEvent e) {
      JButton b = (JButton)e.getSource();
      
      /* TextField¿¡ ÀÔ·ÂµÈ ÀÌ¸§À» º¯¼ö¿¡ ÃÊ±âÈ­ */
      String name = nameField.getText();
      
      /* Á¾·á¹öÆ° ÀÌº¥Æ® */
      if(b.getText().equals("Á¾·á")) {
        System.out.println("[Client] °ÔÀÓ Á¾·á");
        System.exit(0);
      }
      
      else if(b.getText().equals("´ë±â½ÇÀÔÀå")) {
        if(name.equals("")) { // ÀÌ¸§ ¹ÌÀÔ·Â½Ã ´ë±â½ÇÀÔÀå ½ÇÆĞ
          JOptionPane.showMessageDialog(null, "ÀÌ¸§À» ÀÔ·ÂÇÏ¼Å¾ßÁö ÀÔÀåÀÌ µË´Ï´Ù!!!", "ÀÔÀå ½ÇÆĞ!", JOptionPane.ERROR_MESSAGE);
          System.out.println("[Client] ·Î±×ÀÎ ½ÇÆĞ!!! : ÀÌ¸§À» ¹ÌÀÔ·Â ");
        } else if (!name.equals("")) { // ÀÔÀå ¼º°ø½Ã
          c.sendMsg(loginTag + "//" + name); // ¼­¹ö¿¡ ÀÔÀå Á¤º¸¸¦ Àü¼ÛÇÑ´Ù!
        }
      }
    }
    
  } // ButtonEvent
  
  /* Key ÀÌº¥Æ® ¸®½º³Ê */
  class KeyBoardListener implements KeyListener{
    @Override
    public void keyPressed(KeyEvent e) {
      // Enter Key Event
      
      if(e.getKeyCode() == KeyEvent.VK_ENTER) {
        /* TextField¿¡ ÀÔ·ÂµÈ ÀÌ¸§À» º¯¼ö¿¡ ÃÊ±âÈ­ */
        String name = nameField.getText();
        
        if(name.equals("")) { // ÀÌ¸§ ¹ÌÀÔ·Â½Ã ´ë±â½ÇÀÔÀå ½ÇÆĞ
          JOptionPane.showMessageDialog(null, "ÀÌ¸§À» ÀÔ·ÂÇÏ¼Å¾ßÁö ÀÔÀåÀÌ µË´Ï´Ù!!!", "ÀÔÀå ½ÇÆĞ!", JOptionPane.ERROR_MESSAGE);
          System.out.println("[Client] ·Î±×ÀÎ ½ÇÆĞ!!! : ÀÌ¸§À» ¹ÌÀÔ·Â ");
        } else if (!name.equals("")) { // ÀÔÀå ¼º°ø½Ã
          c.sendMsg(loginTag + "//" + name); // ¼­¹ö¿¡ ÀÔÀå Á¤º¸¸¦ Àü¼ÛÇÑ´Ù!
        }
      }
      
    }
    
    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
  }
}
