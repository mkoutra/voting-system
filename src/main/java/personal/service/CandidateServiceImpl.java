package personal.service;

import personal.dao.ICandidateDAO;
import personal.dao.exceptions.CandidateDAOException;
import personal.model.Candidate;

import java.util.List;

public class CandidateServiceImpl implements ICandidateService {
    private final ICandidateDAO candidateDAO;

    public CandidateServiceImpl(ICandidateDAO candidateDAO) {
        this.candidateDAO = candidateDAO;
    }

    @Override
    public List<Candidate> getAllCandidates() throws CandidateDAOException {
        return candidateDAO.getAllCandidates();
    }
}
