package personal.viewcontroller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import personal.App;
import personal.dao.IUserDAO;
import personal.dao.UserDAOImpl;
import personal.dao.exceptions.UserDAOException;
import personal.model.User;
import personal.service.IUserService;
import personal.service.UserServiceImpl;
import personal.service.exceptions.UserNotFoundException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AdminOptionsFrame extends JFrame {
	private final static IUserDAO userDAO = new UserDAOImpl();
	private final static IUserService userService = new UserServiceImpl(userDAO);

	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(AdminOptionsFrame.class);

	private JPanel contentPane;
	private JButton logoutBtn;

	/**
	 * Create the frame.
	 */
	public AdminOptionsFrame() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (isEnabled()) {
					logoutBtn.doClick();
				}
			}
		});
		setResizable(false);
		setTitle("Administator Options");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 295, 228);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(null);
		titlePanel.setBackground(new Color(52, 101, 164));
		titlePanel.setBounds(0, 0, 296, 40);
		contentPane.add(titlePanel);

		JLabel administatorLabel = new JLabel("Administrator");
		administatorLabel.setForeground(new Color(238, 238, 236));
		administatorLabel.setBounds(97, 12, 102, 15);
		titlePanel.add(administatorLabel);

		JPanel panel = new JPanel();
		panel.setBounds(27, 52, 231, 130);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton candidatesBtn = new JButton("Candidates");
		candidatesBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				activateCandidateWindow();

				App.getAdminOptionsFrame().setEnabled(false);
			}
		});
		candidatesBtn.setBounds(34, 13, 162, 25);
		candidatesBtn.setForeground(new Color(52, 101, 164));
		panel.add(candidatesBtn);

		JButton changePasswordBtn = new JButton("Change Password");
		changePasswordBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					User user = userService.getUserByUsername("admin");
					App.getChangePasswordFrame().setUser(user);
					App.getChangePasswordFrame().setParentFrame(App.getAdminOptionsFrame());
					App.getChangePasswordFrame().setVisible(true);

					// Deactivate current window
					App.getAdminOptionsFrame().setEnabled(false);
				} catch (UserNotFoundException | UserDAOException e1) {
					JOptionPane.showMessageDialog(null, "Unable to open change password window",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		changePasswordBtn.setForeground(new Color(52, 101, 164));
		changePasswordBtn.setBounds(34, 51, 162, 25);
		panel.add(changePasswordBtn);

		logoutBtn = new JButton("Logout");
		logoutBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to log out?",
						"Logout Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if (response == JOptionPane.YES_OPTION) {
					App.getMainMenuFrame().setEnabled(true);
					App.getMainMenuFrame().setVisible(true);
					dispose();
				}
			}
		});
		logoutBtn.setBounds(34, 89, 162, 25);
		panel.add(logoutBtn);
		logoutBtn.setForeground(new Color(52, 101, 164));
	}

	private void activateCandidateWindow() {
		App.getCandidatesFrame().setVisible(true);
	}
}
