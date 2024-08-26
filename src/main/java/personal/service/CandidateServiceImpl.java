package personal.service;

import personal.dao.ICandidateDAO;
import personal.dao.exceptions.CandidateDAOException;
import personal.dto.CandidateInsertDTO;
import personal.dto.CandidateReadOnlyDTO;
import personal.dto.CandidateUpdateDTO;
import personal.dto.CandidatesWithVotesReadOnlyDTO;
import personal.model.Candidate;
import personal.service.exceptions.CandidateIOException;
import personal.service.exceptions.CandidateNotFoundException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class CandidateServiceImpl implements ICandidateService {
    private final ICandidateDAO candidateDAO;

    public CandidateServiceImpl(ICandidateDAO candidateDAO) {
        this.candidateDAO = candidateDAO;
    }

    @Override
    public void insertCandidate(CandidateInsertDTO dto) throws CandidateDAOException {
        Candidate candidate = mapInsertDTOToCandidate(dto);
        candidateDAO.insert(candidate);
    }

    @Override
    public void updateCandidate(CandidateUpdateDTO dto) throws CandidateNotFoundException, CandidateDAOException {
        Candidate candidate = mapUpdateDTOToCandidate(dto);
        if (!candidateDAO.cidExists(candidate.getCid())) {
            throw new CandidateNotFoundException("Candidate with id: " + candidate.getCid() + " was not found");
        }
        candidateDAO.update(candidate);
    }

    @Override
    public void deleteCandidate(Integer cid) throws CandidateNotFoundException, CandidateDAOException {
        if (!candidateDAO.cidExists(cid)) {
            throw new CandidateNotFoundException("Candidate with id: " + cid + " was not found");
        }
        candidateDAO.delete(cid);
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
    public void saveVotingResults(List<CandidatesWithVotesReadOnlyDTO> candidatesDTOs, File file)
            throws CandidateIOException {
        if (file == null) return;
        try (PrintWriter pw = new PrintWriter(file)) {
            pw.println("ID,First name,Last name,Total Votes");
            candidatesDTOs.stream()
                          .sorted(Comparator.comparing(CandidatesWithVotesReadOnlyDTO::getCid))
                          .forEach(pw::println);
        } catch (FileNotFoundException e) {
            throw new CandidateIOException("File: " + file + " not found.");
        }
    }

    private Candidate mapInsertDTOToCandidate(CandidateInsertDTO dto) {
        Candidate candidate = new Candidate();
        candidate.setFirstname(dto.getFirstname());
        candidate.setLastname(dto.getLastname());
        return candidate;
    }

    private Candidate mapUpdateDTOToCandidate(CandidateUpdateDTO dto) {
        Candidate candidate = new Candidate();
        candidate.setCid(dto.getCid());
        candidate.setFirstname(dto.getFirstname());
        candidate.setLastname(dto.getLastname());
        return candidate;
    }
}
