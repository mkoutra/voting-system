package personal.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import personal.dao.exceptions.CandidateDAOException;
import personal.dao.exceptions.UserDAOException;
import personal.dao.util.DbHelper;
import personal.model.Candidate;
import personal.model.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CandidateDAOImplTest {
    private static ICandidateDAO candidateDAO;
    private static IUserDAO userDAO;

    @BeforeAll
    public static void preparation() throws SQLException {
        userDAO = new UserDAOImpl();
        candidateDAO = new CandidateDAOImpl();
        DbHelper.eraseAllData();
    }

    @BeforeEach
    public void setup() throws UserDAOException, CandidateDAOException {
        DbHelper.createDummyCandidates(3);
        DbHelper.createDummyUsers(3);
    }

    @AfterEach
    public void tearDown() throws SQLException {
        DbHelper.eraseAllData();
    }

    @Test
    void getAllCandidates() throws CandidateDAOException {
        List<Candidate> candidates = candidateDAO.getAllCandidates();
        assertEquals(3, candidates.size());
    }

    @Test
    void candidateIdExists() throws CandidateDAOException {
        assertTrue(candidateDAO.cidExists(2));
        assertFalse(candidateDAO.cidExists(45));
    }

    @Test
    void getAllCandidatesWithVotes() throws CandidateDAOException {
        Map<Candidate, Integer> candidatesWithVotes = candidateDAO.getAllCandidatesWithVotes();

//        System.out.println(candidatesWithVotes);

        for (Candidate candidate : candidatesWithVotes.keySet()) {
            assertEquals(0, candidatesWithVotes.get(candidate));
        }

        try {
            User user = userDAO.getById(1);

            user.setHasVoted(1);
            user.setVotedCid(2);
            userDAO.update(user);

            candidatesWithVotes = candidateDAO.getAllCandidatesWithVotes();


            for (Candidate candidate : candidatesWithVotes.keySet()) {
                if (candidate.getCid() == 2) {
                    assertEquals(1, candidatesWithVotes.get(candidate));
                }
            }

        } catch (UserDAOException e) {
            e.printStackTrace();
        }
    }
}