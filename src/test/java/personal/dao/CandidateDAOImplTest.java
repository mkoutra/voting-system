package personal.dao;

import org.junit.jupiter.api.Test;
import personal.dao.exceptions.CandidateDAOException;
import personal.model.Candidate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CandidateDAOImplTest {
    private final ICandidateDAO candidateDAO = new CandidateDAOImpl();

    @Test
    void getAllCandidates() throws CandidateDAOException {
        List<Candidate> candidates = candidateDAO.getAllCandidates();
        System.out.println(candidates);
        assertEquals(3, candidates.get(0).getCid());
    }
}