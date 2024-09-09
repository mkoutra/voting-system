package personal.viewcontroller.util;

import personal.dao.CandidateDAOImpl;
import personal.dao.ICandidateDAO;
import personal.dao.exceptions.CandidateDAOException;
import personal.dto.CandidatesWithVotesReadOnlyDTO;
import personal.model.Candidate;
import personal.service.CandidateServiceImpl;
import personal.service.ICandidateService;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Utility class to help wit CandidatesWithVotesDTOs handling.
 *
 * @author Michail E. Koutrakis
 */
public class CandidatesWithVotesDTOsUtil {

    private static final ICandidateDAO candidateDAO = new CandidateDAOImpl();
    private static final ICandidateService candidateService = new CandidateServiceImpl(candidateDAO);

    private CandidatesWithVotesDTOsUtil() {}

    public static List<CandidatesWithVotesReadOnlyDTO> createList() {
        try {
            Map<Candidate, Integer> candidatesWithVotes = candidateService.getAllCandidatesWithVotes();
            return mapToCandidatesWithVotesReadOnlyDTOList(candidatesWithVotes);
        } catch (CandidateDAOException e) {
            JOptionPane.showMessageDialog(null, "Error in retrieving the results",
                    "Results table error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    private static List<CandidatesWithVotesReadOnlyDTO> mapToCandidatesWithVotesReadOnlyDTOList(Map<Candidate, Integer> candidatesWithVotes) {
        List<CandidatesWithVotesReadOnlyDTO> candidatesWithVotesArray = new ArrayList<>();
        CandidatesWithVotesReadOnlyDTO dto;

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
