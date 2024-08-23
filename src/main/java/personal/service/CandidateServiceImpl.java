package personal.service;

import personal.dao.ICandidateDAO;
import personal.dao.exceptions.CandidateDAOException;
import personal.dto.CandidateReadOnlyDTO;
import personal.model.Candidate;
import personal.service.exceptions.CandidateNotFoundException;

import java.io.File;
import java.util.List;
import java.util.Map;

public class CandidateServiceImpl implements ICandidateService {
    private final ICandidateDAO candidateDAO;

    public CandidateServiceImpl(ICandidateDAO candidateDAO) {
        this.candidateDAO = candidateDAO;
    }

    @Override
    public List<Candidate> getAllCandidates() throws CandidateDAOException {
        return candidateDAO.getAllCandidates();
    }

    @Override
    public boolean candidateExistsById(CandidateReadOnlyDTO candidateReadOnlyDTO) throws CandidateDAOException {
        return candidateDAO.cidExists(candidateReadOnlyDTO.getCid());
    }

    @Override
    public Candidate getCandidateById(Integer cid) throws CandidateNotFoundException, CandidateDAOException {
        if (!candidateDAO.cidExists(cid)) {
            throw new CandidateNotFoundException("Candidate with id=" + cid + " does not exist.");
        }
        try {
            return candidateDAO.getCandidateById(cid);
        } catch (CandidateDAOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Map<Candidate, Integer> getAllCandidatesWithVotes() throws CandidateDAOException {
        return candidateDAO.getAllCandidatesWithVotes();
    }

    @Override
    public void saveVotingResults(File file) {
        return;
    }
}
