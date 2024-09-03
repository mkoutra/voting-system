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

/**
 * Service layer interface for managing candidate-related operations.
 */
public interface ICandidateService {

    /**
     * Inserts a new candidate into the system.
     *
     * @param dto The {@link CandidateInsertDTO} containing the candidate's details.
     * @throws CandidateDAOException If an error occurs during the database operation.
     */
    void insertCandidate(CandidateInsertDTO dto) throws CandidateDAOException;

    /**
     * Updates an existing candidate's details in the system.
     *
     * @param dto The {@link CandidateUpdateDTO} containing the updated candidate details.
     * @throws CandidateNotFoundException If the candidate is not found in the system.
     * @throws CandidateDAOException      If an error occurs during the database operation.
     */
    void updateCandidate(CandidateUpdateDTO dto) throws CandidateNotFoundException, CandidateDAOException;

    /**
     * Deletes a candidate from the system by their ID.
     *
     * @param cid The ID of the candidate to delete.
     * @throws CandidateNotFoundException If the candidate is not found in the system.
     * @throws CandidateDAOException      If an error occurs during the database operation.
     */
    void deleteCandidate(Integer cid) throws CandidateNotFoundException, CandidateDAOException;

    /**
     * Retrieves a list of all candidates in the system.
     *
     * @return  A {@link List} of {@link Candidate} objects representing all candidates.
     *          If no candidates are found, an empty list is returned.
     * @throws CandidateDAOException If an error occurs during the database operation.
     */
    List<Candidate> getAllCandidates() throws CandidateDAOException;

    /**
     * Checks if a candidate exists in the system by their ID.
     *
     * @param candidateReadOnlyDTO The {@link CandidateReadOnlyDTO} representing the candidate to check.
     * @return {@code true} if the candidate exists, {@code false} otherwise.
     * @throws CandidateDAOException If an error occurs during the database operation.
     */
    boolean candidateExistsById(CandidateReadOnlyDTO candidateReadOnlyDTO) throws CandidateDAOException;

    /**
     * Retrieves a candidate from the system by their ID.
     *
     * @param cid The ID of the candidate to retrieve.
     * @return The {@link Candidate} object representing the candidate.
     * @throws CandidateNotFoundException If the candidate is not found in the system.
     * @throws CandidateDAOException      If an error occurs during the database operation.
     */
    Candidate getCandidateById(Integer cid) throws CandidateNotFoundException, CandidateDAOException;

    /**
     * Retrieves a map of all candidates and their corresponding vote counts.
     *
     * @return A {@link Map} where the keys are {@link Candidate} objects and the values are the vote counts.
     * @throws CandidateDAOException If an error occurs during the database operation.
     */
    Map<Candidate, Integer> getAllCandidatesWithVotes() throws CandidateDAOException;

    /**
     * Saves the voting results to a specified file.
     *
     * @param candidatesDTOs A {@link List} of {@link CandidatesWithVotesReadOnlyDTO} representing the candidates and their votes.
     * @param file           The {@link File} to which the voting results will be saved.
     * @throws CandidateIOException If an error occurs during the file operation.
     */
    void saveVotingResults(List<CandidatesWithVotesReadOnlyDTO> candidatesDTOs, File file) throws CandidateIOException;
}
