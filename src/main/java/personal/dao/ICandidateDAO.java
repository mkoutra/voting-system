package personal.dao;

import personal.dao.exceptions.CandidateDAOException;
import personal.model.Candidate;

import java.util.List;
import java.util.Map;

/**
 * An interface for managing candidate data in the DAO (Data Access Object) layer.
 */
public interface ICandidateDAO {

    /**
     * Inserts a new candidate into the database.
     *
     * @param candidate The candidate object to insert.
     * @return The inserted candidate object.
     * @throws CandidateDAOException If there is an error accessing the database, such as a SQL exception.
     */
    Candidate insert(Candidate candidate) throws CandidateDAOException;

    /**
     * Updates an existing candidate in the database.
     * The candidate's ID (cid) must match the ID of an existing candidate.
     *
     * @param candidate The candidate object containing updated information.
     * @return The updated candidate object.
     * @throws CandidateDAOException If there is an error accessing the database, such as a SQL exception.
     */
    Candidate update(Candidate candidate) throws CandidateDAOException;

    /**
     * Deletes the candidate with the specified ID from the database.
     *
     * @param cid The ID of the candidate to delete.
     * @throws CandidateDAOException If there is an error accessing the database, such as a SQL exception.
     */
    void delete(Integer cid) throws CandidateDAOException;

    /**
     * Checks if a candidate with the specified ID exists in the database.
     *
     * @param cid The ID of the candidate to check for existence.
     * @return True if a candidate with the given ID exists, false otherwise.
     * @throws CandidateDAOException If there is an error accessing the database, such as a SQL exception.
     */
    boolean cidExists(Integer cid) throws CandidateDAOException;

    /**
     * Retrieves the candidate with the specified ID from the database.
     *
     * @param cid The ID of the candidate to retrieve.
     * @return The candidate object with the specified ID, or null if no candidate is found.
     * @throws CandidateDAOException If there is an error accessing the database, such as a SQL exception.
     */
    Candidate getCandidateById(Integer cid) throws CandidateDAOException;

    /**
     * Retrieves all candidates from the database.
     *
     * @return A list of all candidate objects. If no candidates are found, an empty list is returned.
     * @throws CandidateDAOException If there is an error accessing the database, such as a SQL exception.
     */
    List<Candidate> getAllCandidates() throws CandidateDAOException;

    /**
     * Retrieves all candidates and their corresponding vote counts from the database.
     *
     * @return  A map where each key is a candidate object and the value is the total vote count for that candidate.
     *          If no candidate exists it returns an empty map.
     * @throws CandidateDAOException If there is an error accessing the database, such as a SQL exception.
     */
    Map<Candidate, Integer> getAllCandidatesWithVotes() throws CandidateDAOException;
}

