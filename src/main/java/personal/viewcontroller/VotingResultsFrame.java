package personal.viewcontroller;

import personal.dao.CandidateDAOImpl;
import personal.dao.ICandidateDAO;
import personal.dao.exceptions.CandidateDAOException;
import personal.dto.CandidateReadOnlyDTO;
import personal.dto.CandidatesWithVotesReadOnlyDTO;
import personal.model.Candidate;
import personal.service.CandidateServiceImpl;
import personal.service.ICandidateService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;

public class VotingResultsFrame extends JFrame {
	private final ICandidateDAO candidateDAO = new CandidateDAOImpl();
	private final ICandidateService candidateService = new CandidateServiceImpl(candidateDAO);

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable resultsTable;
	private DefaultTableModel resultsModel;
	private DefaultComboBoxModel sortByModel;
	private JComboBox comboBox;

	/**
	 * Create the frame.
	 */
	public VotingResultsFrame() {
		setTitle("Voting Results");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 525, 275);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 46, 366, 181);
		contentPane.add(scrollPane);
		
		resultsTable = new JTable();
		resultsTable.setModel(new DefaultTableModel(
			new Object[][] {},
			new String[] {"ID", "First Name", "Last Name", "Total Votes"}
		));
		scrollPane.setViewportView(resultsTable);
		resultsModel = (DefaultTableModel) resultsTable.getModel();
//		resultsTable.setAutoCreateRowSorter(true);

		JPanel votingResultsPanel = new JPanel();
		votingResultsPanel.setLayout(null);
		votingResultsPanel.setBackground(new Color(52, 101, 164));
		votingResultsPanel.setBounds(0, 0, 525, 40);
		contentPane.add(votingResultsPanel);
		
		JLabel votingResultsLabel = new JLabel("Voting Results");
		votingResultsLabel.setForeground(new Color(255, 255, 255));
		votingResultsLabel.setBounds(211, 12, 103, 15);
		votingResultsPanel.add(votingResultsLabel);
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setBounds(384, 167, 125, 60);
		contentPane.add(buttonsPanel);
		buttonsPanel.setLayout(null);
		
		JButton saveResultsBtn = new JButton("Save Results");
		saveResultsBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				int num = fileChooser.showSaveDialog(null);
				File file = fileChooser.getSelectedFile();
				System.out.println(num);
				System.out.println(file);
				try {
					PrintWriter pw = new PrintWriter(new FileWriter(file));
					pw.println("Test");
					pw.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		saveResultsBtn.setBounds(0, 0, 125, 25);
		buttonsPanel.add(saveResultsBtn);
		saveResultsBtn.setForeground(new Color(52, 101, 164));
		
		JButton closeBtn = new JButton("Close");
		closeBtn.setBounds(0, 35, 125, 25);
		buttonsPanel.add(closeBtn);
		closeBtn.setForeground(new Color(52, 101, 164));
		
		JPanel panel = new JPanel();
		panel.setBounds(384, 67, 125, 45);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Sort By");
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 12));
		lblNewLabel.setBounds(0, 0, 51, 15);
		panel.add(lblNewLabel);
		lblNewLabel.setForeground(new Color(52, 101, 164));
		
		comboBox = new JComboBox();
		comboBox.setBounds(0, 20, 125, 24);
		panel.add(comboBox);
		comboBox.setFont(new Font("Dialog", Font.PLAIN, 12));
		comboBox.setForeground(new Color(0, 0, 0));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Total Votes", "First Name", "Last Name", "ID"}));
		sortByModel = (DefaultComboBoxModel) comboBox.getModel();
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int optionInt = comboBox.getSelectedIndex();
				buildCandidatesTable(optionInt);
			}
		});

	}

	private void buildCandidatesTable(Integer sortByIndex) {
		try {
			Map<Candidate, Integer> candidatesWithVotes = candidateService.getAllCandidatesWithVotes();
			List<CandidatesWithVotesReadOnlyDTO> readOnlyDTOs = mapToCandidatesWithVotesReadOnlyDTO(candidatesWithVotes);
			Vector<String> vector;

			// Remove table rows
			for (int i = resultsModel.getRowCount() - 1; i >= 0; i--) {
				resultsModel.removeRow(i);
			}


			// Sort candidates by sort Index
			switch (sortByIndex) {
				case 0:
					readOnlyDTOs.sort(Comparator.comparing(CandidatesWithVotesReadOnlyDTO::getTotalVotes));
					break;
				case 1:
					readOnlyDTOs.sort(Comparator.comparing(CandidatesWithVotesReadOnlyDTO::getFirstname));
					break;
				case 2:
					readOnlyDTOs.sort(Comparator.comparing(CandidatesWithVotesReadOnlyDTO::getLastname));
					break;
				case 3:
					readOnlyDTOs.sort(Comparator.comparing(CandidatesWithVotesReadOnlyDTO::getCid));
					break;
			}

			// Add to model
			for (CandidatesWithVotesReadOnlyDTO dto : readOnlyDTOs) {
				vector = new Vector<>(4);
				vector.add(Integer.toString(dto.getCid()));
				vector.add(dto.getFirstname());
				vector.add(dto.getLastname());
				vector.add(String.valueOf(dto.getTotalVotes()));

				resultsModel.addRow(vector);
			}

		} catch (CandidateDAOException e1) {
			JOptionPane.showMessageDialog(null, "Unable to create candidate table.",
					"Table error", JOptionPane.ERROR_MESSAGE);
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
}
