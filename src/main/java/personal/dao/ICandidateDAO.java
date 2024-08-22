package personal.dao;

import personal.dao.exceptions.CandidateDAOException;
import personal.model.Candidate;

import java.util.List;

public interface ICandidateDAO {
    boolean cidExists(Integer cid) throws CandidateDAOException;
    List<Candidate> getAllCandidates() throws CandidateDAOException;
}
