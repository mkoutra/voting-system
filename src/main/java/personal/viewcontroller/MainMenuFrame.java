package personal.viewcontroller;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.BevelBorder;

public class MainMenuFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField usernameText;
	private JPasswordField passwordField;

	/**
	 * Create the frame.
	 */
	public MainMenuFrame() {
		setTitle("Voting");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 448, 346);
		contentPane = new JPanel();
		contentPane.setForeground(new Color(52, 101, 164));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setForeground(new Color(136, 138, 133));
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(64, 51, 323, 177);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Login");
		lblNewLabel.setBounds(133, 12, 47, 15);
		panel.add(lblNewLabel);
		lblNewLabel.setForeground(new Color(52, 101, 164));
		
		JLabel usernameLabel = new JLabel("Username");
		usernameLabel.setBounds(12, 47, 77, 15);
		panel.add(usernameLabel);
		usernameLabel.setForeground(new Color(52, 101, 164));
		
		usernameText = new JTextField();
		usernameText.setBounds(98, 43, 201, 25);
		panel.add(usernameText);
		usernameText.setColumns(10);
		
		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(14, 95, 77, 15);
		panel.add(passwordLabel);
		passwordLabel.setForeground(new Color(52, 101, 164));
		
		passwordField = new JPasswordField();
		passwordField.setBounds(98, 91, 201, 25);
		panel.add(passwordField);
		passwordField.setForeground(new Color(52, 101, 164));
		
		JButton submitBtn = new JButton("Sign in");
		submitBtn.setBounds(76, 140, 161, 25);
		panel.add(submitBtn);
		submitBtn.setForeground(new Color(52, 101, 164));
		
		JLabel usernameErrorLabel = new JLabel("");
		usernameErrorLabel.setForeground(new Color(204, 0, 0));
		usernameErrorLabel.setFont(new Font("Dialog", Font.PLAIN, 10));
		usernameErrorLabel.setBounds(98, 65, 225, 15);
		panel.add(usernameErrorLabel);
		
		JLabel passwordErrorLabel = new JLabel("");
		passwordErrorLabel.setForeground(new Color(204, 0, 0));
		passwordErrorLabel.setFont(new Font("Dialog", Font.PLAIN, 10));
		passwordErrorLabel.setBounds(98, 113, 225, 15);
		panel.add(passwordErrorLabel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(null);
		panel_1.setBounds(64, 240, 323, 57);
		contentPane.add(panel_1);
		
		JLabel createAccountLabel = new JLabel("If you don't have an account, you can create one here:");
		createAccountLabel.setFont(new Font("Dialog", Font.PLAIN, 11));
		panel_1.add(createAccountLabel);
		createAccountLabel.setVerticalAlignment(SwingConstants.TOP);
		
		JButton createAccountBtn = new JButton("Sign up");
		createAccountBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.getInsertNewUser().setVisible(true);
				Main.getMainMenuFrame().setEnabled(false);
			}
		});
		createAccountBtn.setFont(new Font("Dialog", Font.BOLD, 12));
		panel_1.add(createAccountBtn);
		createAccountBtn.setForeground(new Color(52, 101, 164));
		
		JPanel panel_2 = new JPanel();
		panel_2.setLayout(null);
		panel_2.setBackground(new Color(52, 101, 164));
		panel_2.setBounds(0, -13, 448, 50);
		contentPane.add(panel_2);
		
		JLabel lblLogInTo = new JLabel("Log in to submit your vote.");
		lblLogInTo.setForeground(new Color(238, 238, 236));
		lblLogInTo.setBounds(12, 23, 344, 15);
		panel_2.add(lblLogInTo);
	}
}
