package personal.service;

import personal.dao.exceptions.CandidateDAOException;
import personal.dto.CandidateInsertDTO;
import personal.dto.CandidateReadOnlyDTO;
import personal.dto.CandidateUpdateDTO;
import personal.dto.CandidatesWithVotesReadOnlyDTO;
import personal.model.Candidate;
import personal.service.exceptions.CandidateIOException;
import personal.service.exceptions.CandidateNotFoundException;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface ICandidateService {
    void insertCandidate(CandidateInsertDTO dto) throws CandidateDAOException;
    void updateCandidate(CandidateUpdateDTO dto) throws CandidateNotFoundException, CandidateDAOException;
    void deleteCandidate(Integer cid) throws CandidateNotFoundException, CandidateDAOException;
    List<Candidate> getAllCandidates() throws CandidateDAOException;
    boolean candidateExistsById(CandidateReadOnlyDTO candidateReadOnlyDTO) throws CandidateDAOException;
    Candidate getCandidateById(Integer cid) throws CandidateNotFoundException, CandidateDAOException;
    Map<Candidate, Integer> getAllCandidatesWithVotes() throws CandidateDAOException;
    void saveVotingResults(List<CandidatesWithVotesReadOnlyDTO> candidatesDTOs, File file) throws CandidateIOException;
}
