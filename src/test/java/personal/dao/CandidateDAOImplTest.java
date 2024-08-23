package personal.dao;

import org.junit.jupiter.api.Test;
import personal.dao.exceptions.CandidateDAOException;
import personal.model.Candidate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CandidateDAOImplTest {
    private final ICandidateDAO candidateDAO = new CandidateDAOImpl();

    @Test
    void getAllCandidates() throws CandidateDAOException {
        List<Candidate> candidates = candidateDAO.getAllCandidates();
        System.out.println(candidates);
        assertEquals(3, candidates.get(0).getCid());
    }

    @Test
    void candidateIdExists() throws CandidateDAOException {
        assertTrue(candidateDAO.cidExists(2));
        assertFalse(candidateDAO.cidExists(45));
    }

    @Test
    void getAllCandidatesWithVotes() throws CandidateDAOException {
        Map<Candidate, Integer> candidatesWithVotes = candidateDAO.getAllCandidatesWithVotes();

        for (Candidate candidate : candidatesWithVotes.keySet()) {
            System.out.println(candidate);
            System.out.println(candidatesWithVotes.get(candidate));
        }
    }
}