package personal.viewcontroller;

import personal.dao.CandidateDAOImpl;
import personal.dao.ICandidateDAO;
import personal.dao.exceptions.CandidateDAOException;
import personal.dto.CandidatesWithVotesReadOnlyDTO;
import personal.model.Candidate;
import personal.service.CandidateServiceImpl;
import personal.service.ICandidateService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.border.BevelBorder;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Font;
import java.util.*;
import javax.swing.table.DefaultTableModel;

public class CandidatesFrame extends JFrame {
	private final ICandidateDAO candidateDAO = new CandidateDAOImpl();
	private final ICandidateService candidateService = new CandidateServiceImpl(candidateDAO);

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTextField candidateId;
	private JTextField candidateFirstnameText;
	private JTextField candidateLastnameText;
	private JTable candidatesTable;
	private JTextField insertFirstnameText;
	private JTextField insertLastnameText;
	private DefaultTableModel candidatesModel;
	private List<CandidatesWithVotesReadOnlyDTO> candidatesWithVotesReadOnlyDTOs;

	/**
	 * Create the frame.
	 */
	public CandidatesFrame() {
		setResizable(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				candidatesWithVotesReadOnlyDTOs = createCandidatesWithVotesReadOnlyDTO();
				buildCandidatesTable(candidatesWithVotesReadOnlyDTOs);
			}
			@Override
			public void windowOpened(WindowEvent e) {
				candidatesWithVotesReadOnlyDTOs = createCandidatesWithVotesReadOnlyDTO();
				buildCandidatesTable(candidatesWithVotesReadOnlyDTOs);
			}
			@Override
			public void windowClosing(WindowEvent e) {
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 577, 420);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel titlePanel = new JPanel();
		titlePanel.setBackground(new Color(52, 101, 164));
		titlePanel.setBounds(0, 0, 577, 38);
		contentPane.add(titlePanel);
		titlePanel.setLayout(null);
		
		JLabel candidatesLabel = new JLabel("Candidates");
		candidatesLabel.setBounds(248, 11, 81, 15);
		candidatesLabel.setForeground(new Color(238, 238, 236));
		titlePanel.add(candidatesLabel);
		
		JPanel editCandidatePanel = new JPanel();
		editCandidatePanel.setLayout(null);
		editCandidatePanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		editCandidatePanel.setBackground(new Color(238, 238, 236));
		editCandidatePanel.setBounds(362, 185, 203, 179);
		contentPane.add(editCandidatePanel);
		
		JLabel editCandidateLabel = new JLabel("Edit Candidate");
		editCandidateLabel.setHorizontalAlignment(SwingConstants.CENTER);
		editCandidateLabel.setForeground(new Color(52, 101, 164));
		editCandidateLabel.setBounds(39, 4, 124, 15);
		editCandidatePanel.add(editCandidateLabel);
		
		candidateId = new JTextField();
		candidateId.setEditable(false);
		candidateId.setColumns(10);
		candidateId.setBounds(12, 42, 49, 19);
		editCandidatePanel.add(candidateId);
		
		candidateFirstnameText = new JTextField();
		candidateFirstnameText.setColumns(10);
		candidateFirstnameText.setBounds(12, 84, 148, 19);
		editCandidatePanel.add(candidateFirstnameText);
		
		candidateLastnameText = new JTextField();
		candidateLastnameText.setColumns(10);
		candidateLastnameText.setBounds(12, 126, 148, 19);
		editCandidatePanel.add(candidateLastnameText);
		
		JButton deleteBtn = new JButton("Delete");
		deleteBtn.setForeground(new Color(52, 101, 164));
		deleteBtn.setBounds(12, 149, 81, 25);
		editCandidatePanel.add(deleteBtn);
		
		JButton updateBtn = new JButton("Update");
		updateBtn.setForeground(new Color(52, 101, 164));
		updateBtn.setBounds(104, 149, 88, 25);
		editCandidatePanel.add(updateBtn);
		
		JLabel firstnameLabel = new JLabel("First name");
		firstnameLabel.setFont(new Font("Dialog", Font.PLAIN, 11));
		firstnameLabel.setBounds(12, 65, 70, 15);
		editCandidatePanel.add(firstnameLabel);
		
		JLabel lastnameLabel = new JLabel("Last name");
		lastnameLabel.setFont(new Font("Dialog", Font.PLAIN, 11));
		lastnameLabel.setBounds(12, 107, 70, 15);
		editCandidatePanel.add(lastnameLabel);
		
		JLabel idLabel = new JLabel("ID");
		idLabel.setFont(new Font("Dialog", Font.PLAIN, 11));
		idLabel.setBounds(12, 23, 11, 15);
		editCandidatePanel.add(idLabel);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(6, 106, 350, 258);
		contentPane.add(scrollPane);
		
		candidatesTable = new JTable() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		candidatesTable.setModel(new DefaultTableModel(
				new Object[][] {},
				new String[] {"ID", "First Name", "Last Name", "Total Votes"}
		));
		candidatesTable.setAutoCreateRowSorter(true);
		scrollPane.setViewportView(candidatesTable);
		candidatesModel = (DefaultTableModel) candidatesTable.getModel();
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(362, 54, 203, 119);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel insertCandidateLabel = new JLabel("Insert Candidate");
		insertCandidateLabel.setHorizontalAlignment(SwingConstants.CENTER);
		insertCandidateLabel.setForeground(new Color(52, 101, 164));
		insertCandidateLabel.setBounds(39, 1, 124, 15);
		panel.add(insertCandidateLabel);
		
		JLabel firstnameInsertLabel = new JLabel("First name");
		firstnameInsertLabel.setFont(new Font("Dialog", Font.PLAIN, 11));
		firstnameInsertLabel.setBounds(12, 17, 70, 15);
		panel.add(firstnameInsertLabel);
		
		insertFirstnameText = new JTextField();
		insertFirstnameText.setColumns(10);
		insertFirstnameText.setBounds(12, 33, 148, 19);
		panel.add(insertFirstnameText);
		
		JLabel lastnameInsertLabel = new JLabel("Last name");
		lastnameInsertLabel.setFont(new Font("Dialog", Font.PLAIN, 11));
		lastnameInsertLabel.setBounds(12, 53, 70, 15);
		panel.add(lastnameInsertLabel);
		
		insertLastnameText = new JTextField();
		insertLastnameText.setColumns(10);
		insertLastnameText.setBounds(12, 69, 148, 19);
		panel.add(insertLastnameText);
		
		JButton insertBtn = new JButton("Insert");
		insertBtn.setBounds(12, 89, 81, 25);
		panel.add(insertBtn);
		insertBtn.setForeground(new Color(52, 101, 164));
		
		JTextArea selectACandidateText = new JTextArea();
		selectACandidateText.setWrapStyleWord(true);
		selectACandidateText.setLineWrap(true);
		selectACandidateText.setBackground(new Color(238, 238, 236));
		selectACandidateText.setText("Please select a candidate from the table if you'd like to edit their details.");
		selectACandidateText.setBounds(10, 54, 350, 44);
		selectACandidateText.setEditable(false);
		contentPane.add(selectACandidateText);
	}

	private void buildCandidatesTable(List<CandidatesWithVotesReadOnlyDTO> readOnlyDTOs) {
		Vector<String> vector;

		// Remove table rows
		for (int i = candidatesModel.getRowCount() - 1; i >= 0; i--) {
			candidatesModel.removeRow(i);
		}

		readOnlyDTOs.sort(Comparator.comparing(CandidatesWithVotesReadOnlyDTO::getCid));

		// Add to model
		for (CandidatesWithVotesReadOnlyDTO dto : readOnlyDTOs) {
			vector = new Vector<>(4);
			vector.add(Integer.toString(dto.getCid()));
			vector.add(dto.getFirstname());
			vector.add(dto.getLastname());
			vector.add(String.valueOf(dto.getTotalVotes()));

			candidatesModel.addRow(vector);
		}
	}

	private List<CandidatesWithVotesReadOnlyDTO> mapToCandidatesWithVotesReadOnlyDTO(Map<Candidate, Integer> candidatesWithVotes) {
		List<CandidatesWithVotesReadOnlyDTO> candidatesWithVotesArray = new ArrayList<>();
		CandidatesWithVotesReadOnlyDTO dto = null;

		for (Candidate candidate : candidatesWithVotes.keySet()) {
			dto = new CandidatesWithVotesReadOnlyDTO();
			dto.setCid(candidate.getCid());
			dto.setFirstname(candidate.getFirstname());
			dto.setLastname(candidate.getLastname());
			dto.setTotalVotes(candidatesWithVotes.get(candidate));

			candidatesWithVotesArray.add(dto);
		}

		return candidatesWithVotesArray;
	}

	private List<CandidatesWithVotesReadOnlyDTO> createCandidatesWithVotesReadOnlyDTO() {
		try {
			Map<Candidate, Integer> candidatesWithVotes = candidateService.getAllCandidatesWithVotes();
			return mapToCandidatesWithVotesReadOnlyDTO(candidatesWithVotes);
		} catch (CandidateDAOException e) {
			JOptionPane.showMessageDialog(null, "Error in retrieving the results",
					"Results table error", JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}
}
