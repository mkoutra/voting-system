package personal.viewcontroller;

import personal.dao.CandidateDAOImpl;
import personal.dao.ICandidateDAO;
import personal.dao.IUserDAO;
import personal.dao.UserDAOImpl;
import personal.dao.exceptions.CandidateDAOException;
import personal.dao.exceptions.UserDAOException;
import personal.dto.CandidateReadOnlyDTO;
import personal.dto.UserReadOnlyDTO;
import personal.model.Candidate;
import personal.model.User;
import personal.service.CandidateServiceImpl;
import personal.service.ICandidateService;
import personal.service.IUserService;
import personal.service.UserServiceImpl;
import personal.service.exceptions.UserNotFoundException;
import personal.service.util.DBUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.border.BevelBorder;

public class VotingFrame extends JFrame {
	private final ICandidateDAO candidateDAO = new CandidateDAOImpl();
	private final ICandidateService candidateService = new CandidateServiceImpl(candidateDAO);
	private final IUserDAO userDAO = new UserDAOImpl();
	private final IUserService userService = new UserServiceImpl(userDAO);

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable candidatesTable;
	private JTextField idText;
	private JTextField firstnameText;
	private JTextField lastnameText;
	private JLabel usernameLabel;
	private JLabel userFirstnameLabel;
	private JLabel userLastnameLabel;
	private DefaultTableModel model;
	private CandidateReadOnlyDTO selectedCandidateReadOnlyDTO = null;
	private UserReadOnlyDTO voterReadOnlyDTO = null;

	public UserReadOnlyDTO getVoterReadOnlyDTO() {
		return voterReadOnlyDTO;
	}

	public void setVoterReadOnlyDTO(UserReadOnlyDTO voterReadOnlyDTO) {
		this.voterReadOnlyDTO = voterReadOnlyDTO;
	}

	/**
	 * Create the frame.
	 */
	public VotingFrame() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				buildCandidatesTable();
				buildUserInformation(getVoterReadOnlyDTO());
			}
			@Override
			public void windowActivated(WindowEvent e) {
				buildUserInformation(getVoterReadOnlyDTO());
			}
		});
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
		userIcon.setIcon(new ImageIcon(Thread.currentThread().getContextClassLoader().getResource(
				"userIcon_x_small.png")));

		usernameLabel = new JLabel("Username");
		usernameLabel.setFont(new Font("Dialog", Font.BOLD, 11));
		usernameLabel.setBounds(50, 0, 120, 15);
		userPanel.add(usernameLabel);

		userFirstnameLabel = new JLabel("Firstname");
		userFirstnameLabel.setFont(new Font("Dialog", Font.PLAIN, 11));
		userFirstnameLabel.setBounds(50, 15, 120, 15);
		userPanel.add(userFirstnameLabel);

		userLastnameLabel = new JLabel("Lastname");
		userLastnameLabel.setFont(new Font("Dialog", Font.PLAIN, 11));
		userLastnameLabel.setBounds(50, 30, 120, 15);
		userPanel.add(userLastnameLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(16, 62, 301, 196);
		contentPane.add(scrollPane);
		
		candidatesTable = new JTable();
		candidatesTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selectedCandidateReadOnlyDTO = createSelectedCandidateReadOnlyDTO();

				buildSelectedCandidate(selectedCandidateReadOnlyDTO);
			}
		});
		scrollPane.setViewportView(candidatesTable);
		candidatesTable.setModel(new DefaultTableModel(
			new Object[][] {},
			new String[] {"ID", "First name", "Last name"}
		));

		model = (DefaultTableModel) candidatesTable.getModel();

		JPanel selectedCandidatePanel = new JPanel();
		selectedCandidatePanel.setBorder(new BevelBorder(BevelBorder.LOWERED,
				null, null, null, null));
		selectedCandidatePanel.setBounds(322, 84, 172, 171);
		contentPane.add(selectedCandidatePanel);
		selectedCandidatePanel.setLayout(null);

		JLabel selectedCandidateLabel = new JLabel("Selected Candidate");
		selectedCandidateLabel.setForeground(new Color(52, 101, 164));
		selectedCandidateLabel.setHorizontalAlignment(SwingConstants.CENTER);
		selectedCandidateLabel.setBounds(12, 5, 148, 15);
		selectedCandidatePanel.add(selectedCandidateLabel);

		idText = new JTextField();
		idText.setEditable(false);
		idText.setBounds(12, 33, 49, 19);
		selectedCandidatePanel.add(idText);
		idText.setColumns(10);

		firstnameText = new JTextField();
		firstnameText.setEditable(false);
		firstnameText.setColumns(10);
		firstnameText.setBounds(12, 64, 148, 19);
		selectedCandidatePanel.add(firstnameText);

		lastnameText = new JTextField();
		lastnameText.setEditable(false);
		lastnameText.setColumns(10);
		lastnameText.setBounds(12, 95, 148, 19);
		selectedCandidatePanel.add(lastnameText);
		
		JButton voteBtn = new JButton("Vote");
		voteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (selectedCandidateReadOnlyDTO == null || voterReadOnlyDTO == null) {
					return;
				}
				try {
					User user = userService.getUserByUsername(voterReadOnlyDTO.getUsername());
					user.setHasVoted(1);
					user.setVotedCid(selectedCandidateReadOnlyDTO.getCid());
					userDAO.update(user);
				} catch (UserNotFoundException | UserDAOException e1) {
						e1.printStackTrace();
				}
			}
		});
		voteBtn.setForeground(new Color(52, 101, 164));
		voteBtn.setBounds(30, 134, 117, 25);
		selectedCandidatePanel.add(voteBtn);
		
		JLabel selectACandidateLabel = new JLabel("Select a candidate to vote.");
		selectACandidateLabel.setBounds(16, 19, 243, 15);
		contentPane.add(selectACandidateLabel);
	}

	private void buildCandidatesTable() {
		try {
			List<Candidate> candidates = candidateService.getAllCandidates();
			List<CandidateReadOnlyDTO> readOnlyDTOCandidates = new ArrayList<>();
			Vector<String> vector;

			// Insert readOnlyDTO to arrayList.
			for (Candidate candidate : candidates) {
				readOnlyDTOCandidates.add(mapCandidateToCandidateReadOnlyDTO(candidate));
			}

			// Remove table rows
			for (int i = model.getRowCount() - 1; i >= 0; i--) {
				model.removeRow(i);
			}

			// Add to model
			for (CandidateReadOnlyDTO dto : readOnlyDTOCandidates) {
				vector = new Vector<>(3);
				vector.add(Integer.toString(dto.getCid()));
				vector.add(dto.getFirstname());
				vector.add(dto.getLastname());

				model.addRow(vector);
			}

		} catch (CandidateDAOException e1) {
			JOptionPane.showMessageDialog(null, "Unable to create candidate table.",
					"Table error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private CandidateReadOnlyDTO mapCandidateToCandidateReadOnlyDTO(Candidate candidate) {
		CandidateReadOnlyDTO candidateReadOnlyDTO = new CandidateReadOnlyDTO();
		candidateReadOnlyDTO.setCid(candidate.getCid());
		candidateReadOnlyDTO.setFirstname(candidate.getFirstname());
		candidateReadOnlyDTO.setLastname(candidate.getLastname());

		return candidateReadOnlyDTO;
	}

	private CandidateReadOnlyDTO createSelectedCandidateReadOnlyDTO() {
		CandidateReadOnlyDTO candidateReadOnlyDTO = new CandidateReadOnlyDTO();
		candidateReadOnlyDTO.setCid(Integer.parseInt((String) model.getValueAt(candidatesTable.getSelectedRow(), 0)));
		candidateReadOnlyDTO.setFirstname((String) model.getValueAt(candidatesTable.getSelectedRow(), 1));
		candidateReadOnlyDTO.setLastname((String) model.getValueAt(candidatesTable.getSelectedRow(), 2));
		return candidateReadOnlyDTO;
	}

	private void buildSelectedCandidate(CandidateReadOnlyDTO selectedCandidateReadOnlyDTO) {
		idText.setText(Integer.toString(selectedCandidateReadOnlyDTO.getCid()));
		firstnameText.setText(selectedCandidateReadOnlyDTO.getFirstname());
		lastnameText.setText(selectedCandidateReadOnlyDTO.getLastname());
	}

	private void buildUserInformation(UserReadOnlyDTO userReadOnlyDTO) {
		if (userReadOnlyDTO == null) return;
		usernameLabel.setText(userReadOnlyDTO.getUsername());
		userFirstnameLabel.setText(userReadOnlyDTO.getFirstname());
		userLastnameLabel.setText(userReadOnlyDTO.getLastname());
	}
}
