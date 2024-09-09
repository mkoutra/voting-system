package personal.viewcontroller;

import personal.App;
import personal.dao.CandidateDAOImpl;
import personal.dao.ICandidateDAO;
import personal.dto.CandidatesWithVotesReadOnlyDTO;
import personal.service.CandidateServiceImpl;
import personal.service.ICandidateService;
import personal.service.exceptions.CandidateIOException;
import personal.viewcontroller.util.CandidatesWithVotesDTOsUtil;

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

/**
 * @author Michail E. Koutrakis
 */
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
				candidatesWithVotesReadOnlyDTOs = CandidatesWithVotesDTOsUtil.createList();
				sortCandidates(0);
				renderCandidatesTable(candidatesWithVotesReadOnlyDTOs);
			}

			@Override
			public void windowActivated(WindowEvent e) {
				candidatesWithVotesReadOnlyDTOs = CandidatesWithVotesDTOsUtil.createList();
				sortCandidates(0);
				renderCandidatesTable(candidatesWithVotesReadOnlyDTOs);
			}

			@Override
			public void windowClosing(WindowEvent e) {
				if (isEnabled()) {
					App.getVotingWindow().setEnabled(true);
					dispose();
				}
			}
		});
		setResizable(false);
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
				onSaveResultsClicked();
			}
		});
		saveResultsBtn.setBounds(0, 0, 125, 25);
		buttonsPanel.add(saveResultsBtn);
		saveResultsBtn.setForeground(new Color(52, 101, 164));
		
		JButton closeBtn = new JButton("Close");
		closeBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				onCloseClicked();
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
				onComboBoxActivation();
			}
		});
	}

	private void renderCandidatesTable(List<CandidatesWithVotesReadOnlyDTO> dtos) {
		RenderingUtil.renderCandidatesModelTable(dtos, resultsModel);
	}

	private void sortCandidates(Integer sortByIndex) {
		CandidatesWithVotesDTOsUtil.sortCandidatesWithVotesReadonlyDTOs(candidatesWithVotesReadOnlyDTOs, sortByIndex);
	}

	private void onSaveResultsClicked() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("CSV Files", "csv"));

		if (fileChooser.showSaveDialog(contentPane) == JFileChooser.APPROVE_OPTION) {
			try {
				File file = candidateService.fixFileExtension(fileChooser.getSelectedFile(), "csv");
				List<CandidatesWithVotesReadOnlyDTO> readOnlyDTOs = CandidatesWithVotesDTOsUtil.createList();
				candidateService.saveVotingResults(readOnlyDTOs, file);
			} catch (CandidateIOException e1) {
				JOptionPane.showMessageDialog(contentPane, "An error occurred while saving the file",
						"File Saving Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void onCloseClicked() {
		App.getVotingWindow().setEnabled(true);
		dispose();
	}

	private void onComboBoxActivation() {
		int optionInt = comboBox.getSelectedIndex();
		sortCandidates(optionInt);
		renderCandidatesTable(candidatesWithVotesReadOnlyDTOs);
	}
}
