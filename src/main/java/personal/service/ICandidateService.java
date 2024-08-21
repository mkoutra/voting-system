package personal.service;

import personal.dao.exceptions.CandidateDAOException;
import personal.model.Candidate;

import java.util.List;

public interface ICandidateService {
    List<Candidate> getAllCandidates() throws CandidateDAOException;
}
