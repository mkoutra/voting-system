package personal.viewcontroller;

import personal.dao.CandidateDAOImpl;
import personal.dao.ICandidateDAO;
import personal.dao.IUserDAO;
import personal.dao.UserDAOImpl;
import personal.dao.exceptions.CandidateDAOException;
import personal.dao.exceptions.UserDAOException;
import personal.dto.*;
import personal.dto.CandidateUpdateDTO;
import personal.model.Candidate;
import personal.service.CandidateServiceImpl;
import personal.service.ICandidateService;
import personal.service.IUserService;
import personal.service.UserServiceImpl;
import personal.service.exceptions.CandidateNotFoundException;
import personal.validator.CandidateDTOValidator;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.border.BevelBorder;
import java.awt.event.*;
import java.awt.Font;
import java.util.*;
import javax.swing.table.DefaultTableModel;

public class CandidatesFrame extends JFrame {
	private final ICandidateDAO candidateDAO = new CandidateDAOImpl();
	private final ICandidateService candidateService = new CandidateServiceImpl(candidateDAO);
	private final IUserDAO userDAO = new UserDAOImpl();
	private final IUserService userService = new UserServiceImpl(userDAO);

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
	private CandidateUpdateDTO selectedCandidateUpdateDTO;

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
		deleteBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (candidateId.getText().isEmpty()) {
					return;
				}

				Integer cidToDelete = Integer.parseInt(candidateId.getText());

				try {
					int response = JOptionPane.showConfirmDialog(null,
							"Are you sure you want to delete the candidate with ID: " + cidToDelete + "? This action cannot be undone.",
							"Delete Confirmation", JOptionPane.YES_NO_OPTION);

					if (response == JOptionPane.YES_OPTION) {
						// Remove candidates from users that had voted him/her
						userService.removeAllVotesOfSpecificCid(cidToDelete);

						// Remove candidate from candidates table
						candidateService.deleteCandidate(cidToDelete);

						// Clean Edit candidate
						cleanEditCandidate();
					}
				} catch (CandidateDAOException | UserDAOException e1) {
					JOptionPane.showMessageDialog(null, "Error in storage",
							"SQL-Deletion error", JOptionPane.ERROR_MESSAGE);
					cleanEditCandidate();
				} catch (CandidateNotFoundException e2) {
					JOptionPane.showMessageDialog(null,
							"Candidate with id: " + cidToDelete + " does not exist",
							"SQL-Deletion error", JOptionPane.ERROR_MESSAGE);
					cleanEditCandidate();
				}
			}
		});
		deleteBtn.setForeground(new Color(52, 101, 164));
		deleteBtn.setBounds(12, 149, 81, 25);
		deleteBtn.setEnabled(false);
		editCandidatePanel.add(deleteBtn);
		
		JButton updateBtn = new JButton("Update");
		updateBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (candidateId.getText().isEmpty()) {
					return;
				}

				selectedCandidateUpdateDTO.setFirstname(candidateFirstnameText.getText());
				selectedCandidateUpdateDTO.setLastname(candidateLastnameText.getText());

				Integer cidToDelete = Integer.parseInt(candidateId.getText());

				try {
					// Validation
					CandidateDTOValidator validator = new CandidateDTOValidator();
					List<String> errors = validator.validate(selectedCandidateUpdateDTO);

					if (!errors.isEmpty()) {
						StringBuilder errorStringBuilder = new StringBuilder();
						errors.forEach((s) -> errorStringBuilder.append(s).append('\n'));
						JOptionPane.showMessageDialog(null, errorStringBuilder.toString(),
								"Update error", JOptionPane.ERROR_MESSAGE);
						return;
					}

					candidateService.updateCandidate(selectedCandidateUpdateDTO);

					// Update candidates table
					candidatesWithVotesReadOnlyDTOs = createCandidatesWithVotesReadOnlyDTO();
					buildCandidatesTable(candidatesWithVotesReadOnlyDTOs);

					cleanEditCandidate();
				} catch (CandidateDAOException e1) {
					JOptionPane.showMessageDialog(null, "Error in Candidate storage",
							"SQL-Insertion error", JOptionPane.ERROR_MESSAGE);
					cleanEditCandidate();
				} catch (CandidateNotFoundException e2) {
					JOptionPane.showMessageDialog(null,
							"Candidate with id: " + cidToDelete + " does not exist",
							"SQL-Deletion error", JOptionPane.ERROR_MESSAGE);
					cleanEditCandidate();
				}
			}
		});
		updateBtn.setForeground(new Color(52, 101, 164));
		updateBtn.setBounds(104, 149, 88, 25);
		updateBtn.setEnabled(false);
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
		candidatesTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (candidatesTable.isEnabled()) {
					selectedCandidateUpdateDTO = createCandidateUpdateDTOFromTable();
					buildSelectedCandidate(selectedCandidateUpdateDTO);
					deleteBtn.setEnabled(true);
					updateBtn.setEnabled(true);
				}
			}
		});
		candidatesTable.getTableHeader().setReorderingAllowed(false);
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
		insertBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CandidateInsertDTO candidateInsertDTO = createCandidateInsertDTO();
				List<String> errors;

				// Validation
				CandidateDTOValidator validator = new CandidateDTOValidator();
				errors = validator.validate(candidateInsertDTO);

				if (!errors.isEmpty()) {
					StringBuilder errorStringBuilder = new StringBuilder();
					errors.forEach((s) -> errorStringBuilder.append(s).append('\n'));
					JOptionPane.showMessageDialog(null, errorStringBuilder.toString(),
							"Insertion error", JOptionPane.ERROR_MESSAGE);
					return;
				}

                try {
                    candidateService.insertCandidate(candidateInsertDTO);

					// Update candidates table
					candidatesWithVotesReadOnlyDTOs = createCandidatesWithVotesReadOnlyDTO();
					buildCandidatesTable(candidatesWithVotesReadOnlyDTOs);

					// Clean texts
					insertFirstnameText.setText("");
					insertLastnameText.setText("");
                } catch (CandidateDAOException e1) {
					JOptionPane.showMessageDialog(null, "Error in Candidate storage",
							"SQL-Insertion error", JOptionPane.ERROR_MESSAGE);
                }
            }
		});
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

		readOnlyDTOs.sort(Comparator.comparing(CandidatesWithVotesReadOnlyDTO::getLastname));

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

	private CandidateUpdateDTO createCandidateUpdateDTOFromTable() {
		CandidateUpdateDTO candidateUpdateDTO = new CandidateUpdateDTO();
//		candidateUpdateDTO.setCid(Integer.parseInt((String) candidatesModel.getValueAt(candidatesTable.getSelectedRow(), 0)));
//		candidateUpdateDTO.setFirstname((String) candidatesModel.getValueAt(candidatesTable.getSelectedRow(), 1));
//		candidateUpdateDTO.setLastname((String) candidatesModel.getValueAt(candidatesTable.getSelectedRow(), 2));

		candidateUpdateDTO.setCid(Integer.parseInt((String) candidatesTable.getValueAt(candidatesTable.getSelectedRow(), 0)));
		candidateUpdateDTO.setFirstname((String) candidatesTable.getValueAt(candidatesTable.getSelectedRow(), 1));
		candidateUpdateDTO.setLastname((String) candidatesTable.getValueAt(candidatesTable.getSelectedRow(), 2));

		return candidateUpdateDTO;
	}

	private void buildSelectedCandidate(CandidateUpdateDTO selectedCandidateUpdateDTO) {
		candidateId.setText(Integer.toString(selectedCandidateUpdateDTO.getCid()));
		candidateFirstnameText.setText(selectedCandidateUpdateDTO.getFirstname());
		candidateLastnameText.setText(selectedCandidateUpdateDTO.getLastname());
	}

	private CandidateInsertDTO createCandidateInsertDTO() {
		CandidateInsertDTO candidateInsertDTO = new CandidateInsertDTO();
		candidateInsertDTO.setFirstname(insertFirstnameText.getText().trim());
		candidateInsertDTO.setLastname(insertLastnameText.getText().trim());

		return candidateInsertDTO;
	}

	private void cleanEditCandidate() {
		candidateId.setText("");
		candidateFirstnameText.setText("");
		candidateLastnameText.setText("");
	}
}
