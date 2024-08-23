package personal.dao;

import personal.dao.exceptions.CandidateDAOException;
import personal.model.Candidate;

import java.util.List;
import java.util.Map;

public interface ICandidateDAO {
    boolean cidExists(Integer cid) throws CandidateDAOException;
    Candidate getCandidateById(Integer cid) throws CandidateDAOException;
    List<Candidate> getAllCandidates() throws CandidateDAOException;
    Map<Candidate, Integer> getAllCandidatesWithVotes() throws CandidateDAOException;
}
