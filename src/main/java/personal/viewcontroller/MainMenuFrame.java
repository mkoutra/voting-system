package personal.viewcontroller;

import personal.App;
import personal.dao.IUserDAO;
import personal.dao.UserDAOImpl;
import personal.dao.exceptions.UserDAOException;
import personal.dto.UserLoginDTO;
import personal.dto.UserReadOnlyDTO;
import personal.model.User;
import personal.service.IUserService;
import personal.service.UserServiceImpl;
import personal.service.exceptions.UserNotFoundException;
import personal.validator.Validator;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import javax.swing.border.BevelBorder;

/**
 * @author Michail E. Koutrakis
 */
public class MainMenuFrame extends JFrame {
	private final IUserDAO userDAO = new UserDAOImpl();
	private final IUserService userService = new UserServiceImpl(userDAO);

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField usernameText;
	private JPasswordField passwordField;
	private JTextArea failedLoginText;
	private JButton submitBtn;

	/**
	 * Create the frame.
	 */
	public MainMenuFrame() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				onWindowOpened();
			}

			@Override
			public void windowClosing(WindowEvent e) {
				if (isEnabled()) {
					System.exit(0);
				}
			}
		});
		setResizable(false);
		setTitle("Voting");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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
		lblNewLabel.setBounds(138, 12, 47, 15);
		panel.add(lblNewLabel);
		lblNewLabel.setForeground(new Color(52, 101, 164));
		
		JLabel usernameLabel = new JLabel("Username");
		usernameLabel.setBounds(12, 47, 77, 15);
		panel.add(usernameLabel);
		usernameLabel.setForeground(new Color(52, 101, 164));
		
		usernameText = new JTextField();
		usernameText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					submitBtn.doClick();
				}
			}
		});
		usernameText.setBounds(98, 42, 201, 25);
		panel.add(usernameText);
		usernameText.setColumns(10);
		
		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(12, 81, 77, 15);
		panel.add(passwordLabel);
		passwordLabel.setForeground(new Color(52, 101, 164));
		
		passwordField = new JPasswordField();
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					submitBtn.doClick();
				}
			}
		});
		passwordField.setBounds(98, 76, 201, 25);
		panel.add(passwordField);
		passwordField.setForeground(new Color(52, 101, 164));

		failedLoginText = new JTextArea();
		failedLoginText.setForeground(new Color(204, 0, 0));
		failedLoginText.setBackground(new Color(255, 255, 255));
		failedLoginText.setWrapStyleWord(true);
		failedLoginText.setLineWrap(true);
		failedLoginText.setFont(new Font("Dialog", Font.PLAIN, 11));
		failedLoginText.setText("Login failed. Please check your credentials and try again.");
		failedLoginText.setBounds(98, 107, 201, 28);
		failedLoginText.setVisible(false);
		panel.add(failedLoginText);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(null);
		panel_1.setBounds(64, 240, 323, 57);
		contentPane.add(panel_1);
		
		JLabel createAccountLabel = new JLabel("If you don't have an account, you can create one here:");
		createAccountLabel.setFont(new Font("Dialog", Font.PLAIN, 11));
		panel_1.add(createAccountLabel);
		createAccountLabel.setVerticalAlignment(SwingConstants.TOP);

		submitBtn = new JButton("Sign in");
		submitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				onSignInClicked();
			}
		});
		submitBtn.setBounds(81, 140, 161, 25);
		panel.add(submitBtn);
		submitBtn.setForeground(new Color(52, 101, 164));

		JButton createAccountBtn = new JButton("Sign up");
		createAccountBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				App.getInsertNewUser().setVisible(true);
				App.getMainMenuFrame().setEnabled(false);
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

	private void onWindowOpened() {
		try {
			if (userDAO.usernameExists("admin")) return;

			userService.createAdminAccount();
		} catch (UserDAOException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Admin insertion error",
					"Admin Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void onSignInClicked() {
		try {
			UserLoginDTO userLoginDTO = createUserLoginDTO();
			boolean isValidInput = false;
			boolean isAuthenticated = false;

			// Validation
			isValidInput = Validator.validate(userLoginDTO).isEmpty();
			if (!isValidInput) {
				failedLoginText.setVisible(true);
				return;
			}

			// Authentication
			isAuthenticated = userService.authenticateUser(userLoginDTO.getUsername(), userLoginDTO.getPassword());
			if (!isAuthenticated) {
				failedLoginText.setVisible(true);
				return;
			}

			// Activate Voting or Admin Window
			if (userLoginDTO.getUsername().equals("admin")) {
				activateAdminOptionsWindow();
			} else {
				activateVotingWindow(userLoginDTO);
			}

			cleanTexts();

			// Disable and hide Main Menu Frame
			App.getMainMenuFrame().setEnabled(false);
			App.getMainMenuFrame().setVisible(false);
		} catch (UserNotFoundException | UserDAOException e1) {
			failedLoginText.setVisible(true);
		}
	}

	private UserLoginDTO createUserLoginDTO() {
		String usernameInput = usernameText.getText().trim();
		String usernamePassword = new String(passwordField.getPassword()).trim();
		return new UserLoginDTO(usernameInput, usernamePassword);
	}

	private UserReadOnlyDTO mapToReadOnlyDTO(UserLoginDTO loginDTO) {
		UserReadOnlyDTO readOnlyDTO = null;
		try {
			User user = userService.getUserByUsername(loginDTO.getUsername());
			readOnlyDTO = new UserReadOnlyDTO(user.getUsername(), user.getFirstname(), user.getLastname());
		} catch (UserNotFoundException | UserDAOException e) {
			e.printStackTrace();
		}
		return readOnlyDTO;
	}

	private void activateVotingWindow(UserLoginDTO userLoginDTO) {
		UserReadOnlyDTO userReadOnlyDTO = null;
		// Map to read only dto
		userReadOnlyDTO = mapToReadOnlyDTO(userLoginDTO);

		// Send user information to votingWindow and activate it
		App.getVotingWindow().setVoterReadOnlyDTO(userReadOnlyDTO);
		App.getVotingWindow().setVisible(true);
	}

	private void activateAdminOptionsWindow() {
		App.getAdminOptionsFrame().setVisible(true);
	}

	private void cleanTexts() {
		usernameText.setText("");
		passwordField.setText("");
		failedLoginText.setVisible(false);
	}
}
