package personal.viewcontroller;

import personal.App;
import personal.dao.CandidateDAOImpl;
import personal.dao.ICandidateDAO;
import personal.dao.exceptions.CandidateDAOException;
import personal.dto.CandidatesWithVotesReadOnlyDTO;
import personal.model.Candidate;
import personal.service.CandidateServiceImpl;
import personal.service.ICandidateService;
import personal.service.exceptions.CandidateIOException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.*;

public class VotingResultsFrame extends JFrame {
	private final ICandidateDAO candidateDAO = new CandidateDAOImpl();
	private final ICandidateService candidateService = new CandidateServiceImpl(candidateDAO);

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable resultsTable;
	private DefaultTableModel resultsModel;
	private DefaultComboBoxModel sortByModel;
	private JComboBox comboBox;
	private List<CandidatesWithVotesReadOnlyDTO> candidatesWithVotesReadOnlyDTOs;

	/**
	 * Create the frame.
	 */
	public VotingResultsFrame() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				candidatesWithVotesReadOnlyDTOs = createCandidatesWithVotesReadOnlyDTO();
				sortCandidatesTable(0);
				buildCandidatesTable(candidatesWithVotesReadOnlyDTOs);
			}
			@Override
			public void windowActivated(WindowEvent e) {
				candidatesWithVotesReadOnlyDTOs = createCandidatesWithVotesReadOnlyDTO();
				sortCandidatesTable(0);
				buildCandidatesTable(candidatesWithVotesReadOnlyDTOs);
			}
		});
		setTitle("Voting Results");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 525, 275);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 46, 366, 181);
		contentPane.add(scrollPane);
		
		resultsTable = new JTable() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
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
				fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("CSV Files", "csv"));

				if (fileChooser.showSaveDialog(contentPane) == JFileChooser.APPROVE_OPTION) {
					File file = fixFileExtension(fileChooser.getSelectedFile());
					try {
						Map<Candidate, Integer> candidatesWithVotes = candidateService.getAllCandidatesWithVotes();
						List<CandidatesWithVotesReadOnlyDTO> readOnlyDTOs = mapToCandidatesWithVotesReadOnlyDTO(candidatesWithVotes);
						candidateService.saveVotingResults(readOnlyDTOs, file);
					} catch (CandidateIOException | CandidateDAOException e1) {
						JOptionPane.showMessageDialog(contentPane, "An error occurred while saving the file",
								"File Saving Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		saveResultsBtn.setBounds(0, 0, 125, 25);
		buttonsPanel.add(saveResultsBtn);
		saveResultsBtn.setForeground(new Color(52, 101, 164));
		
		JButton closeBtn = new JButton("Close");
		closeBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				App.getVotingWindow().setEnabled(true);
				dispose();
			}
		});
		closeBtn.setBounds(0, 35, 125, 25);
		buttonsPanel.add(closeBtn);
		closeBtn.setForeground(new Color(52, 101, 164));
		
		JPanel panel = new JPanel();
		panel.setBounds(384, 67, 125, 45);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel sortByLabel = new JLabel("Sort By");
		sortByLabel.setFont(new Font("Dialog", Font.BOLD, 12));
		sortByLabel.setBounds(0, 0, 51, 15);
		panel.add(sortByLabel);
		sortByLabel.setForeground(new Color(52, 101, 164));
		
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
				sortCandidatesTable(optionInt);
				buildCandidatesTable(candidatesWithVotesReadOnlyDTOs);
			}
		});
	}

	private void buildCandidatesTable(List<CandidatesWithVotesReadOnlyDTO> readOnlyDTOs) {
		Vector<String> vector;

		// Remove table rows
		for (int i = resultsModel.getRowCount() - 1; i >= 0; i--) {
			resultsModel.removeRow(i);
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
	}

	private void sortCandidatesTable(Integer sortByIndex) {
		switch (sortByIndex) {
			case 0:
				candidatesWithVotesReadOnlyDTOs.sort(Comparator.comparing(CandidatesWithVotesReadOnlyDTO::getTotalVotes).reversed());
				break;
			case 1:
				candidatesWithVotesReadOnlyDTOs.sort(Comparator.comparing(CandidatesWithVotesReadOnlyDTO::getFirstname));
				break;
			case 2:
				candidatesWithVotesReadOnlyDTOs.sort(Comparator.comparing(CandidatesWithVotesReadOnlyDTO::getLastname));
				break;
			case 3:
				candidatesWithVotesReadOnlyDTOs.sort(Comparator.comparing(CandidatesWithVotesReadOnlyDTO::getCid));
				break;
		}
	}

//	private void buildCandidatesTable(List<CandidatesWithVotesReadOnlyDTO> readOnlyDTOs ,Integer sortByIndex) {
////		try {
////			Map<Candidate, Integer> candidatesWithVotes = candidateService.getAllCandidatesWithVotes();
////			List<CandidatesWithVotesReadOnlyDTO> readOnlyDTOs = mapToCandidatesWithVotesReadOnlyDTO(candidatesWithVotes);
//			Vector<String> vector;
//
//			// Remove table rows
//			for (int i = resultsModel.getRowCount() - 1; i >= 0; i--) {
//				resultsModel.removeRow(i);
//			}
//
//			// Sort candidates by sort Index
//			switch (sortByIndex) {
//				case 0:
//					readOnlyDTOs.sort(Comparator.comparing(CandidatesWithVotesReadOnlyDTO::getTotalVotes));
//					break;
//				case 1:
//					readOnlyDTOs.sort(Comparator.comparing(CandidatesWithVotesReadOnlyDTO::getFirstname));
//					break;
//				case 2:
//					readOnlyDTOs.sort(Comparator.comparing(CandidatesWithVotesReadOnlyDTO::getLastname));
//					break;
//				case 3:
//					readOnlyDTOs.sort(Comparator.comparing(CandidatesWithVotesReadOnlyDTO::getCid));
//					break;
//			}
//
//			// Add to model
//			for (CandidatesWithVotesReadOnlyDTO dto : readOnlyDTOs) {
//				vector = new Vector<>(4);
//				vector.add(Integer.toString(dto.getCid()));
//				vector.add(dto.getFirstname());
//				vector.add(dto.getLastname());
//				vector.add(String.valueOf(dto.getTotalVotes()));
//
//				resultsModel.addRow(vector);
//			}
////		} catch (CandidateDAOException e1) {
////			JOptionPane.showMessageDialog(null, "Unable to create candidate table.",
////					"Table error", JOptionPane.ERROR_MESSAGE);
////		}
//	}

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

	private File fixFileExtension(File file) {
		File correctExtensionFile = file;
		if (!(file.getAbsolutePath().endsWith(".csv") || file.getAbsolutePath().endsWith(".txt"))) {
			correctExtensionFile = new File(file.getAbsolutePath() + ".csv");
		}
		return correctExtensionFile;
	}
}
