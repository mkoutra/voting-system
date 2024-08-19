package personal.viewcontroller;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;
import org.mindrot.jbcrypt.BCrypt;
import personal.dao.IUserDAO;
import personal.dao.UserDAOImpl;
import personal.dao.exceptions.UserDAOException;
import personal.dto.UserInsertDTO;
import personal.service.IUserService;
import personal.service.UserServiceImpl;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class InsertNewUser extends JFrame {

	private final IUserDAO userDAO = new UserDAOImpl();
	private final IUserService userService = new UserServiceImpl(userDAO);

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField usernameText;
	private JTextField emailText;
	private JTextField firstnameText;
	private JTextField lastnameText;
	private JPasswordField passwordField;
	private JPasswordField reEnterPasswordField;

	/**
	 * Create the frame.
	 */
	public InsertNewUser() {
		setTitle("Sign up");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 442, 436);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(52, 101, 164));
		panel.setBounds(0, -12, 442, 50);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblSignUp = new JLabel("Please enter your details to register for voting.");
		lblSignUp.setForeground(new Color(238, 238, 236));
		lblSignUp.setBounds(12, 23, 344, 15);
		panel.add(lblSignUp);
		
		JLabel usernameLabel = new JLabel("Username");
		usernameLabel.setForeground(new Color(52, 101, 164));
		usernameLabel.setBounds(108, 52, 72, 15);
		contentPane.add(usernameLabel);
		
		usernameText = new JTextField();
		usernameText.setBounds(187, 50, 205, 19);
		contentPane.add(usernameText);
		usernameText.setColumns(10);
		
		JLabel emailLabel = new JLabel("E-mail");
		emailLabel.setForeground(new Color(52, 101, 164));
		emailLabel.setBounds(137, 94, 42, 15);
		contentPane.add(emailLabel);
		
		emailText = new JTextField();
		emailText.setColumns(10);
		emailText.setBounds(187, 92, 205, 19);
		contentPane.add(emailText);
		
		JLabel firstnameLabel = new JLabel("Firstname");
		firstnameLabel.setForeground(new Color(52, 101, 164));
		firstnameLabel.setBounds(109, 134, 71, 15);
		contentPane.add(firstnameLabel);
		
		firstnameText = new JTextField();
		firstnameText.setColumns(10);
		firstnameText.setBounds(187, 132, 205, 19);
		contentPane.add(firstnameText);
		
		JLabel lastnameLabel = new JLabel("Lastname");
		lastnameLabel.setForeground(new Color(52, 101, 164));
		lastnameLabel.setBounds(108, 180, 71, 15);
		contentPane.add(lastnameLabel);
		
		lastnameText = new JTextField();
		lastnameText.setColumns(10);
		lastnameText.setBounds(187, 178, 205, 19);
		contentPane.add(lastnameText);
		
		JLabel dateOfBirthLabel = new JLabel("Date of Birth");
		dateOfBirthLabel.setForeground(new Color(52, 101, 164));
		dateOfBirthLabel.setBounds(89, 230, 90, 15);
		contentPane.add(dateOfBirthLabel);
		
		JDateChooser dateOfBirth = new JDateChooser();
		dateOfBirth.setBounds(186, 228, 205, 19);
		contentPane.add(dateOfBirth);
		
		JButton submitBtn = new JButton("Submit");
		submitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserInsertDTO insertDTO = new UserInsertDTO();
				insertDTO.setUsername(usernameText.getText().trim());
				insertDTO.setEmail(emailText.getText().trim());
				insertDTO.setFirstname(firstnameText.getText().trim());
				insertDTO.setLastname(lastnameText.getText().trim());
				insertDTO.setDateOfBirth(dateOfBirth.getDate());
				insertDTO.setPassword(new String(passwordField.getPassword()).trim());
				insertDTO.setReEnteredPassword(new String(reEnterPasswordField.getPassword()).trim());

				// TODO: Validation

				try {
					userService.insertUser(insertDTO);
					JOptionPane.showMessageDialog(null,
							"Welcome " + insertDTO.getUsername() + "! Your account has been created successfully.",
							"Successful Insertion", JOptionPane.INFORMATION_MESSAGE);
				} catch (UserDAOException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "User insertion error", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		submitBtn.setForeground(new Color(52, 101, 164));
		submitBtn.setBounds(160, 362, 117, 25);
		contentPane.add(submitBtn);
		
		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setForeground(new Color(52, 101, 164));
		passwordLabel.setBounds(109, 277, 71, 15);
		contentPane.add(passwordLabel);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(187, 275, 205, 19);
		contentPane.add(passwordField);
		
		JLabel reEnterPasswordLabel = new JLabel("Re-enter Password");
		reEnterPasswordLabel.setForeground(new Color(52, 101, 164));
		reEnterPasswordLabel.setBounds(44, 318, 136, 15);
		contentPane.add(reEnterPasswordLabel);
		
		reEnterPasswordField = new JPasswordField();
		reEnterPasswordField.setBounds(187, 316, 205, 19);
		contentPane.add(reEnterPasswordField);
		
		JLabel usernameErrorLabel = new JLabel("");
		usernameErrorLabel.setForeground(new Color(204, 0, 0));
		usernameErrorLabel.setFont(new Font("Dialog", Font.PLAIN, 10));
		usernameErrorLabel.setBounds(187, 68, 244, 15);
		contentPane.add(usernameErrorLabel);
		
		JLabel emailErrorLabel = new JLabel("");
		emailErrorLabel.setForeground(new Color(204, 0, 0));
		emailErrorLabel.setFont(new Font("Dialog", Font.PLAIN, 10));
		emailErrorLabel.setBounds(187, 109, 244, 15);
		contentPane.add(emailErrorLabel);
		
		JLabel firstnameErrorLabel = new JLabel("");
		firstnameErrorLabel.setForeground(new Color(204, 0, 0));
		firstnameErrorLabel.setFont(new Font("Dialog", Font.PLAIN, 10));
		firstnameErrorLabel.setBounds(187, 151, 244, 15);
		contentPane.add(firstnameErrorLabel);
		
		JLabel lastnameErrorLabel = new JLabel("");
		lastnameErrorLabel.setForeground(new Color(204, 0, 0));
		lastnameErrorLabel.setFont(new Font("Dialog", Font.PLAIN, 10));
		lastnameErrorLabel.setBounds(187, 199, 244, 15);
		contentPane.add(lastnameErrorLabel);
		
		JLabel dateOfBirthErrorLabel = new JLabel("");
		dateOfBirthErrorLabel.setForeground(new Color(204, 0, 0));
		dateOfBirthErrorLabel.setFont(new Font("Dialog", Font.PLAIN, 10));
		dateOfBirthErrorLabel.setBounds(187, 248, 244, 15);
		contentPane.add(dateOfBirthErrorLabel);
		
		JLabel passwordErrorLabel = new JLabel("");
		passwordErrorLabel.setForeground(new Color(204, 0, 0));
		passwordErrorLabel.setFont(new Font("Dialog", Font.PLAIN, 10));
		passwordErrorLabel.setBounds(187, 294, 244, 15);
		contentPane.add(passwordErrorLabel);
		
		JLabel reEnterPasswordErrorLabel = new JLabel("");
		reEnterPasswordErrorLabel.setForeground(new Color(204, 0, 0));
		reEnterPasswordErrorLabel.setFont(new Font("Dialog", Font.PLAIN, 10));
		reEnterPasswordErrorLabel.setBounds(187, 336, 244, 15);
		contentPane.add(reEnterPasswordErrorLabel);
	}
}
