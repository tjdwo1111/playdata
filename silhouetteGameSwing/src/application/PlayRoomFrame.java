package application;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import javax.swing.JButton;
import javax.swing.ImageIcon;

public class PlayRoomFrame extends JFrame{

	private JFrame frame;
	private JTextField textField;

	GameClient c = null;
	/**
	 * Launch the application.
	 */
	/*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PlayRoomFrame window = new PlayRoomFrame();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	 */
	/**
	 * Create the application.
	 * @wbp.parser.entryPoint
	 */
	public PlayRoomFrame(GameClient _c) {
		c = _c;
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame();
		frame.setBounds(100, 100, 800, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(12, 430, 477, 21);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel imageLabel = new JLabel("");
		imageLabel.setIcon(new ImageIcon(PlayRoomFrame.class.getResource("/resource/lolAshe.jpg")));
		imageLabel.setBounds(12, 10, 531, 235);
		frame.getContentPane().add(imageLabel);
		
		JList list = new JList();
		list.setBounds(555, 10, 199, 345);
		frame.getContentPane().add(list);
		
		JTextArea textArea = new JTextArea();
		textArea.setBounds(12, 255, 531, 165);
		frame.getContentPane().add(textArea);
		
		JScrollBar scrollBar = new JScrollBar();
		scrollBar.setBounds(526, 255, 17, 165);
		frame.getContentPane().add(scrollBar);
		
		JButton btnSendButton = new JButton("전송");
		btnSendButton.setBounds(495, 429, 57, 23);
		frame.getContentPane().add(btnSendButton);
		
		JButton btnGameStartButton = new JButton("게임시작");
		btnGameStartButton.setBounds(623, 365, 97, 41);
		frame.getContentPane().add(btnGameStartButton);
		
		JButton btnExitButton = new JButton("나가기");
		btnExitButton.setBounds(623, 416, 97, 23);
		frame.getContentPane().add(btnExitButton);
		
		setSize(510, 450);
	}
}
