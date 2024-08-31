package personal.viewcontroller;

import personal.App;
import personal.dao.IUserDAO;
import personal.dao.UserDAOImpl;
import personal.dao.exceptions.UserDAOException;
import personal.dto.ChangePasswordDTO;
import personal.dto.UserReadOnlyDTO;
import personal.model.User;
import personal.service.IUserService;
import personal.service.UserServiceImpl;
import personal.service.exceptions.UserNotFoundException;
import personal.service.exceptions.WrongPasswordException;
import personal.validator.ChangePasswordValidator;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class ChangePasswordFrame extends JFrame {
	private final IUserDAO userDAO = new UserDAOImpl();
	private final IUserService userService = new UserServiceImpl(userDAO);


	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPasswordField newPasswordField;
	private JPasswordField reEnteredPasswordField;
	private JPasswordField currentPasswordField;
	private User user = null;
	private JFrame parentFrame = null;

	public JFrame getParentFrame() {
		return parentFrame;
	}

	public void setParentFrame(JFrame parentFrame) {
		this.parentFrame = parentFrame;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Create the frame.
	 */
	public ChangePasswordFrame() {
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
					cleanAll();
					parentFrame.setEnabled(true);
					dispose();
				}
			}
		});
		setResizable(false);
		setTitle("Change Password");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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
		submitBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ChangePasswordDTO changePasswordDTO = createChangePasswordDTO();
				ChangePasswordValidator validator = new ChangePasswordValidator();
				List<String> errors = validator.validate(changePasswordDTO);
				StringBuilder sb = new StringBuilder();

				if (user == null) return;

				if (!errors.isEmpty()) {
					errors.forEach((error) -> sb.append(error).append('\n'));
					JOptionPane.showMessageDialog(null, sb.toString(), "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}

				try {
					int response = JOptionPane.showConfirmDialog(null, "Are you sure?",
							"Confirmation", JOptionPane.YES_NO_OPTION);
					if (response == JOptionPane.YES_OPTION) {
						userService.changePassword(user, changePasswordDTO);
						cleanAll();
						JOptionPane.showMessageDialog(null, "Password changed successfully",
								"Password Changed", JOptionPane.INFORMATION_MESSAGE);
						parentFrame.setEnabled(true);
						dispose();
					}
				} catch (WrongPasswordException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				} catch (UserNotFoundException | UserDAOException e2) {
					JOptionPane.showMessageDialog(null, "Error retrieving the user", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		submitBtn.setForeground(new Color(52, 101, 164));
		submitBtn.setBounds(120, 116, 117, 25);
		panel.add(submitBtn);
		
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(null);
		titlePanel.setBackground(new Color(52, 101, 164));
		titlePanel.setBounds(0, 0, 453, 40);
		contentPane.add(titlePanel);
		
		JLabel changePasswordLabel = new JLabel("Change Password");
		changePasswordLabel.setForeground(new Color(238, 238, 236));
		changePasswordLabel.setBounds(160, 12, 133, 15);
		titlePanel.add(changePasswordLabel);
	}

	private ChangePasswordDTO createChangePasswordDTO() {
		ChangePasswordDTO dto = new ChangePasswordDTO();
		dto.setCurrentPassword(new String(currentPasswordField.getPassword()));
		dto.setNewPassword(new String(newPasswordField.getPassword()));
		dto.setReEnteredPassword(new String(reEnteredPasswordField.getPassword()));

		return dto;
	}

	private void cleanAll() {
		currentPasswordField.setText("");
		newPasswordField.setText("");
		reEnteredPasswordField.setText("");
	}
}
