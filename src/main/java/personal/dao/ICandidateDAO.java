package personal.dao;

import personal.dao.exceptions.CandidateDAOException;
import personal.model.Candidate;

import java.util.List;

public interface ICandidateDAO {
    List<Candidate> getAllCandidates() throws CandidateDAOException;
}
