package personal.viewcontroller;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import java.awt.Color;
import javax.swing.JButton;

public class ChangePasswordFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPasswordField newPasswordField;
	private JPasswordField reEnteredPasswordField;
	private JPasswordField currentPasswordField;


	/**
	 * Create the frame.
	 */
	public ChangePasswordFrame() {
		setResizable(false);
		setTitle("Change Password");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 453, 260);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(54, 60, 335, 153);
		contentPane.add(panel);
		
		JLabel currentPasswordLabel = new JLabel("Current Password");
		currentPasswordLabel.setForeground(new Color(52, 101, 164));
		currentPasswordLabel.setBounds(31, 15, 128, 15);
		panel.add(currentPasswordLabel);
		
		currentPasswordField = new JPasswordField();
		currentPasswordField.setBounds(164, 10, 158, 25);
		panel.add(currentPasswordField);
		
		JLabel newPasswordLabel = new JLabel("New Password");
		newPasswordLabel.setForeground(new Color(52, 101, 164));
		newPasswordLabel.setBounds(54, 50, 105, 15);
		panel.add(newPasswordLabel);
		
		newPasswordField = new JPasswordField();
		newPasswordField.setBounds(164, 45, 158, 25);
		panel.add(newPasswordField);
		
		JLabel reEnterPasswordLabel = new JLabel("Re-enter Password");
		reEnterPasswordLabel.setForeground(new Color(52, 101, 164));
		reEnterPasswordLabel.setBounds(23, 87, 136, 15);
		panel.add(reEnterPasswordLabel);
		
		reEnteredPasswordField = new JPasswordField();
		reEnteredPasswordField.setBounds(164, 80, 158, 28);
		panel.add(reEnteredPasswordField);
		
		JButton submitBtn = new JButton("Submit");
		submitBtn.setForeground(new Color(52, 101, 164));
		submitBtn.setBounds(120, 116, 117, 25);
		panel.add(submitBtn);
		
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(null);
		titlePanel.setBackground(new Color(52, 101, 164));
		titlePanel.setBounds(0, 10, 453, 40);
		contentPane.add(titlePanel);
		
		JLabel changePasswordLabel = new JLabel("Change Password");
		changePasswordLabel.setForeground(new Color(238, 238, 236));
		changePasswordLabel.setBounds(160, 12, 133, 15);
		titlePanel.add(changePasswordLabel);
	}
}
