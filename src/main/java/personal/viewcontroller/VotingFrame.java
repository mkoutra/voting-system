package personal.viewcontroller;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;

public class VotingFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable candidatesTable;
	private JTextField idText;
	private JTextField firstnameText;
	private JTextField lastnameText;

	/**
	 * Create the frame.
	 */
	public VotingFrame() {
		setResizable(false);
		setTitle("Voting");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 503, 303);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel userPanel = new JPanel();
		userPanel.setBackground(new Color(238, 238, 236));
		userPanel.setBorder(null);
		userPanel.setBounds(322, 3, 172, 48);
		contentPane.add(userPanel);
		userPanel.setLayout(null);
		
		JLabel userIcon = new JLabel("");
		userIcon.setBounds(10, 0, 38, 48);
		userPanel.add(userIcon);
		userIcon.setHorizontalAlignment(SwingConstants.LEFT);
		userIcon.setIcon(new ImageIcon(Thread.currentThread().getContextClassLoader().getResource("userIcon_x_small.png")));
		
		JLabel usernameLabel = new JLabel("Username");
		usernameLabel.setFont(new Font("Dialog", Font.BOLD, 11));
		usernameLabel.setBounds(50, 0, 120, 15);
		userPanel.add(usernameLabel);
		
		JLabel userFirstnameLabel = new JLabel("Firstname");
		userFirstnameLabel.setFont(new Font("Dialog", Font.PLAIN, 11));
		userFirstnameLabel.setBounds(50, 17, 120, 15);
		userPanel.add(userFirstnameLabel);
		
		JLabel userLastnameLabel = new JLabel("Lastname");
		userLastnameLabel.setFont(new Font("Dialog", Font.PLAIN, 11));
		userLastnameLabel.setBounds(50, 33, 120, 15);
		userPanel.add(userLastnameLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(16, 62, 301, 196);
		contentPane.add(scrollPane);
		
		candidatesTable = new JTable();
		scrollPane.setViewportView(candidatesTable);
		candidatesTable.setModel(new DefaultTableModel(
			new Object[][] {},
			new String[] {"ID", "First name", "Last name"}
		));
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(322, 84, 172, 171);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Selected Candidate");
		lblNewLabel.setForeground(new Color(52, 101, 164));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(12, 5, 148, 15);
		panel.add(lblNewLabel);
		
		idText = new JTextField();
		idText.setEditable(false);
		idText.setBounds(12, 33, 49, 19);
		panel.add(idText);
		idText.setColumns(10);
		
		firstnameText = new JTextField();
		firstnameText.setEditable(false);
		firstnameText.setColumns(10);
		firstnameText.setBounds(12, 64, 148, 16);
		panel.add(firstnameText);
		
		lastnameText = new JTextField();
		lastnameText.setEditable(false);
		lastnameText.setColumns(10);
		lastnameText.setBounds(12, 92, 148, 19);
		panel.add(lastnameText);
		
		JButton voteBtn = new JButton("Vote");
		voteBtn.setForeground(new Color(52, 101, 164));
		voteBtn.setBounds(30, 134, 117, 25);
		panel.add(voteBtn);
		
		JLabel lblPickACandidate = new JLabel("Select a candidate to vote.");
		lblPickACandidate.setBounds(16, 19, 243, 15);
		contentPane.add(lblPickACandidate);
	}
}
