package personal.service;

import personal.dao.exceptions.CandidateDAOException;
import personal.dto.CandidateReadOnlyDTO;
import personal.model.Candidate;
import personal.service.exceptions.CandidateNotFoundException;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface ICandidateService {
    List<Candidate> getAllCandidates() throws CandidateDAOException;
    boolean candidateExistsById(CandidateReadOnlyDTO candidateReadOnlyDTO) throws CandidateDAOException;
    Candidate getCandidateById(Integer cid) throws CandidateNotFoundException, CandidateDAOException;
    Map<Candidate, Integer> getAllCandidatesWithVotes() throws CandidateDAOException;
    void saveVotingResults(File file);
}
