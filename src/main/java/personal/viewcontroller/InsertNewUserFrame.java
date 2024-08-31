package personal.viewcontroller;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;
import personal.App;
import personal.dao.IUserDAO;
import personal.dao.UserDAOImpl;
import personal.dao.exceptions.UserDAOException;
import personal.dto.UserInsertDTO;
import personal.service.IUserService;
import personal.service.UserServiceImpl;
import personal.validator.NewUserValidator;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import java.util.Map;

public class InsertNewUserFrame extends JFrame {

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
	private JDateChooser dateOfBirth;
	private JLabel usernameErrorLabel;
	private JLabel emailErrorLabel;
	private JLabel firstnameErrorLabel;
	private JLabel lastnameErrorLabel;
	private JLabel passwordErrorLabel;
	private JLabel dateOfBirthErrorLabel;
	private JLabel reEnterPasswordErrorLabel;

	/**
	 * Create the frame.
	 */
	public InsertNewUserFrame() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				cleanAll();
			}

			@Override
			public void windowActivated(WindowEvent e) {
				cleanAll();
			}

			@Override
			public void windowClosing(WindowEvent e) {
				if (isEnabled()) {
					App.getMainMenuFrame().setEnabled(true);
					dispose();
				}
			}
		});
		setResizable(false);
		setTitle("Sign up");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 442, 436);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JTextPane usernameInfoText = new JTextPane();
		usernameInfoText.setForeground(new Color(51, 51, 51));
		usernameInfoText.setEditable(false);
		usernameInfoText.setBackground(new Color(204, 206, 154));
		usernameInfoText.setText("Username must be 3 to 45 characters long and can"
				+ " include only Latin letters, dots, and underscores.");
		usernameInfoText.setBounds(23, 72, 254, 50);
		usernameInfoText.setVisible(false);

		JTextPane firstnameInfoText = new JTextPane();
		firstnameInfoText.setBackground(new Color(204, 206, 154));
		firstnameInfoText.setText("First name should not contain spaces. If necessary, replace spaces with hyphens.");
		firstnameInfoText.setBounds(23, 158, 254, 50);
		firstnameInfoText.setVisible(false);

		JTextPane lastnameInfoText = new JTextPane();
		lastnameInfoText.setBackground(new Color(204, 206, 154));
		lastnameInfoText.setText("Last name should not contain spaces. If necessary, replace spaces with hyphens.");
		lastnameInfoText.setBounds(23, 209, 254, 50);
		lastnameInfoText.setVisible(false);

		JTextPane passwordInfoText = new JTextPane();
		passwordInfoText.setText("Password must be between 8 and 20 characters long, and include at least one" +
				" lowercase letter, one uppercase letter, one digit, and one special character (#?!@$%^&*-).");
		passwordInfoText.setBackground(new Color(204, 206, 154));
		passwordInfoText.setBounds(23, 312, 314, 75);
		passwordInfoText.setVisible(false);

		contentPane.add(passwordInfoText);
		contentPane.add(lastnameInfoText);
		contentPane.add(firstnameInfoText);
		contentPane.add(usernameInfoText);

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
		
		JLabel firstnameLabel = new JLabel("First Name");
		firstnameLabel.setForeground(new Color(52, 101, 164));
		firstnameLabel.setBounds(105, 134, 82, 15);
		contentPane.add(firstnameLabel);
		
		firstnameText = new JTextField();
		firstnameText.setColumns(10);
		firstnameText.setBounds(187, 132, 205, 19);
		contentPane.add(firstnameText);
		
		JLabel lastnameLabel = new JLabel("Last Name");
		lastnameLabel.setForeground(new Color(52, 101, 164));
		lastnameLabel.setBounds(105, 180, 83, 15);
		contentPane.add(lastnameLabel);
		
		lastnameText = new JTextField();
		lastnameText.setColumns(10);
		lastnameText.setBounds(187, 178, 205, 19);
		contentPane.add(lastnameText);
		
		JLabel dateOfBirthLabel = new JLabel("Date of Birth");
		dateOfBirthLabel.setForeground(new Color(52, 101, 164));
		dateOfBirthLabel.setBounds(89, 230, 90, 15);
		contentPane.add(dateOfBirthLabel);
		
		dateOfBirth = new JDateChooser();
		dateOfBirth.setBounds(186, 228, 205, 19);
		contentPane.add(dateOfBirth);
		
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
		
		usernameErrorLabel = new JLabel("");
		usernameErrorLabel.setForeground(new Color(204, 0, 0));
		usernameErrorLabel.setFont(new Font("Dialog", Font.PLAIN, 10));
		usernameErrorLabel.setBounds(187, 68, 244, 15);
		contentPane.add(usernameErrorLabel);
		
		emailErrorLabel = new JLabel("");
		emailErrorLabel.setForeground(new Color(204, 0, 0));
		emailErrorLabel.setFont(new Font("Dialog", Font.PLAIN, 10));
		emailErrorLabel.setBounds(187, 109, 244, 15);
		contentPane.add(emailErrorLabel);
		
		firstnameErrorLabel = new JLabel("");
		firstnameErrorLabel.setForeground(new Color(204, 0, 0));
		firstnameErrorLabel.setFont(new Font("Dialog", Font.PLAIN, 10));
		firstnameErrorLabel.setBounds(187, 151, 244, 15);
		contentPane.add(firstnameErrorLabel);
		
		lastnameErrorLabel = new JLabel("");
		lastnameErrorLabel.setForeground(new Color(204, 0, 0));
		lastnameErrorLabel.setFont(new Font("Dialog", Font.PLAIN, 10));
		lastnameErrorLabel.setBounds(187, 199, 244, 15);
		contentPane.add(lastnameErrorLabel);
		
		dateOfBirthErrorLabel = new JLabel("");
		dateOfBirthErrorLabel.setForeground(new Color(204, 0, 0));
		dateOfBirthErrorLabel.setFont(new Font("Dialog", Font.PLAIN, 10));
		dateOfBirthErrorLabel.setBounds(187, 248, 244, 15);
		contentPane.add(dateOfBirthErrorLabel);
		
		passwordErrorLabel = new JLabel("");
		passwordErrorLabel.setForeground(new Color(204, 0, 0));
		passwordErrorLabel.setFont(new Font("Dialog", Font.PLAIN, 10));
		passwordErrorLabel.setBounds(187, 294, 244, 15);
		contentPane.add(passwordErrorLabel);
		
		reEnterPasswordErrorLabel = new JLabel("");
		reEnterPasswordErrorLabel.setForeground(new Color(204, 0, 0));
		reEnterPasswordErrorLabel.setFont(new Font("Dialog", Font.PLAIN, 10));
		reEnterPasswordErrorLabel.setBounds(187, 336, 244, 15);
		contentPane.add(reEnterPasswordErrorLabel);


		JLabel usernameInfoLabel = new JLabel("");
		usernameInfoLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				usernameInfoText.setVisible(true);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				usernameInfoText.setVisible(false);
			}
		});
		usernameInfoLabel.setIcon(new ImageIcon(Thread.currentThread().getContextClassLoader().getResource(
				"information_icon_x_small.png")));
		usernameInfoLabel.setBounds(85, 52, 15, 15);
		contentPane.add(usernameInfoLabel);

		JLabel firstnameInfoLabel = new JLabel("");
		firstnameInfoLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				firstnameInfoText.setVisible(true);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				firstnameInfoText.setVisible(false);
			}
		});
		firstnameInfoLabel.setIcon(new ImageIcon(Thread.currentThread().getContextClassLoader().getResource(
				"information_icon_x_small.png")));
		firstnameInfoLabel.setBounds(85, 134, 15, 15);
		contentPane.add(firstnameInfoLabel);

		JLabel lastnameInfoLabel = new JLabel("");
		lastnameInfoLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				lastnameInfoText.setVisible(true);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				lastnameInfoText.setVisible(false);
			}
		});
		lastnameInfoLabel.setIcon(new ImageIcon(Thread.currentThread().getContextClassLoader().getResource(
				"information_icon_x_small.png")));
		lastnameInfoLabel.setBounds(85, 180, 15, 15);
		contentPane.add(lastnameInfoLabel);

		JLabel passwordInfoLabel = new JLabel("");
		passwordInfoLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				passwordInfoText.setVisible(true);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				passwordInfoText.setVisible(false);
			}
		});
		passwordInfoLabel.setIcon(new ImageIcon(Thread.currentThread().getContextClassLoader().getResource(
				"information_icon_x_small.png")));
		passwordInfoLabel.setBounds(85, 277, 15, 15);
		contentPane.add(passwordInfoLabel);

		JButton submitBtn = new JButton("Submit");
		submitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserInsertDTO insertDTO = new UserInsertDTO();
				Map<String, String> errors;
				NewUserValidator newUservalidator = new NewUserValidator();

				// Binding
				insertDTO.setUsername(usernameText.getText().trim());
				insertDTO.setEmail(emailText.getText().trim());
				insertDTO.setFirstname(firstnameText.getText().trim());
				insertDTO.setLastname(lastnameText.getText().trim());
				insertDTO.setDateOfBirth(dateOfBirth.getDate());
				insertDTO.setPassword(new String(passwordField.getPassword()).trim());
				insertDTO.setReEnteredPassword(new String(reEnterPasswordField.getPassword()).trim());

				// Validation
				errors = newUservalidator.validate(insertDTO);

				if (!errors.isEmpty()) {
					usernameErrorLabel.setText(errors.getOrDefault("username", ""));
					emailErrorLabel.setText(errors.getOrDefault("email", ""));
					firstnameErrorLabel.setText(errors.getOrDefault("firstname", ""));
					lastnameErrorLabel.setText(errors.getOrDefault("lastname", ""));
					dateOfBirthErrorLabel.setText(errors.getOrDefault("dob", ""));
					passwordErrorLabel.setText(errors.getOrDefault("password", ""));
					reEnterPasswordErrorLabel.setText(errors.getOrDefault("reEnteredPassword", ""));

					return;
				}

				// Insert to DB, clean texts and close window
				try {
					userService.insertUser(insertDTO);

					JOptionPane.showMessageDialog(null,
							"Welcome " + insertDTO.getUsername() + "!\nYour account has been created successfully.",
							"Successful Insertion", JOptionPane.INFORMATION_MESSAGE);

					App.getMainMenuFrame().setEnabled(true);
					cleanAll();
					dispose();
				} catch (UserDAOException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "User insertion error",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		submitBtn.setForeground(new Color(52, 101, 164));
		submitBtn.setBounds(160, 362, 117, 25);
		contentPane.add(submitBtn);
	}

	private void cleanErrorLabels() {
		usernameErrorLabel.setText("");
		emailErrorLabel.setText("");
		firstnameErrorLabel.setText("");
		lastnameErrorLabel.setText("");
		passwordErrorLabel.setText("");
		dateOfBirthErrorLabel.setText("");
		reEnterPasswordErrorLabel.setText("");
	}

	private void cleanTexts() {
		usernameText.setText("");
		emailText.setText("");
		firstnameText.setText("");
		lastnameText.setText("");
		passwordField.setText("");
		dateOfBirth.setDate(null);
		reEnterPasswordField.setText("");
	}

	private void cleanAll() {
		cleanErrorLabels();
		cleanTexts();
	}
}
