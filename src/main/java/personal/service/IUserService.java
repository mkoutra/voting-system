package personal.service;

import personal.dao.exceptions.UserDAOException;
import personal.dao.exceptions.CandidateDAOException;
import personal.dto.CandidateReadOnlyDTO;
import personal.dto.ChangePasswordDTO;
import personal.dto.UserReadOnlyDTO;
import personal.service.exceptions.UserNotFoundException;
import personal.service.exceptions.CandidateNotFoundException;

import personal.dto.UserInsertDTO;
import personal.model.User;
import personal.service.exceptions.WrongPasswordException;

import java.util.List;

/**
 * Service layer interface for managing user-related operations.
 *
 * @author Michail E. Koutrakis
 */
public interface IUserService {
    /**
     * Inserts a new user into the system.
     *
     * @param dto The {@link UserInsertDTO} containing the user's registration details.
     * @throws UserDAOException If an error occurs during the database operation.
     */
    void insertUser(UserInsertDTO dto) throws UserDAOException;

    /**
     * Registers a vote for a specific candidate by a user.
     *
     * @param userReadOnlyDTO     The {@link UserReadOnlyDTO} representing the user who is voting.
     * @param candidateReadOnlyDTO The {@link CandidateReadOnlyDTO} representing the candidate being voted for.
     * @throws CandidateNotFoundException If the candidate is not found in the system.
     * @throws UserNotFoundException      If the user is not found in the system.
     * @throws UserDAOException           If an error occurs during the database operation for the user.
     * @throws CandidateDAOException      If an error occurs during the database operation for the candidate.
     */
    void voteACandidate(UserReadOnlyDTO userReadOnlyDTO, CandidateReadOnlyDTO candidateReadOnlyDTO)
            throws CandidateNotFoundException, UserNotFoundException, UserDAOException, CandidateDAOException;

    /**
     * Checks if a user exists in the system by their username.
     *
     * @param username The username to check.
     * @return {@code true} if the user exists, {@code false} otherwise.
     * @throws UserDAOException If an error occurs during the database operation.
     */
    boolean userExistsByUsername(String username) throws UserDAOException;

    /**
     * Checks if a user exists in the system by their email.
     *
     * @param email The email to check.
     * @return {@code true} if the user exists, {@code false} otherwise.
     * @throws UserDAOException If an error occurs during the database operation.
     */
    boolean userExistsByEmail(String email) throws UserDAOException;

    /**
     * Retrieves a user from the system by their username.
     *
     * @param username The username of the user to retrieve.
     * @return The {@link User} object corresponding to the username.
     * @throws UserNotFoundException If the user is not found in the system.
     * @throws UserDAOException      If an error occurs during the database operation.
     */
    User getUserByUsername(String username) throws UserNotFoundException, UserDAOException;

    /**
     * Authenticates a user by verifying their username and plain-text password.
     *
     * @param username      The username of the user.
     * @param plainPassword The plain-text password provided for authentication.
     * @return {@code true} if authentication is successful, {@code false} otherwise.
     * @throws UserNotFoundException If the user is not found in the system.
     * @throws UserDAOException      If an error occurs during the database operation.
     */
    boolean authenticateUser(String username, String plainPassword) throws UserNotFoundException, UserDAOException;

    /**
     * Checks if a user has already voted.
     *
     * @param username The username of the user to check.
     * @return {@code true} if the user has voted, {@code false} otherwise.
     * @throws UserNotFoundException If the user is not found in the system.
     * @throws UserDAOException      If an error occurs during the database operation.
     */
    boolean checkIfUserHasVoted(String username) throws UserNotFoundException, UserDAOException;

    /**
     * Retrieves a list of all users in the system.
     *
     * @return  A {@link List} of {@link User} objects representing all users.
     *          If no users are found, an empty list is returned.
     * @throws UserDAOException If an error occurs during the database operation.
     */
    List<User> getAllUsers() throws UserDAOException;

    /**
     * Retrieves a list of users who voted for a specific candidate.
     *
     * @param votedCid The ID of the candidate.
     * @return  A {@link List} of {@link User} objects who voted for the specified candidate.
     *          If a candidate has no votes, it returns an empty list.
     * @throws UserDAOException           If an error occurs during the database operation.
     * @throws CandidateDAOException      If an error occurs during the database operation for the candidate.
     * @throws CandidateNotFoundException If the candidate is not found in the system.
     */
    List<User> getAllUsersByVotedCid(Integer votedCid) throws UserDAOException, CandidateDAOException, CandidateNotFoundException;

    /**
     * Removes all votes for a specific candidate.
     *
     * @param votedCid                    The ID of the candidate whose votes are to be removed.
     * @throws UserDAOException           If an error occurs during the database operation.
     * @throws CandidateDAOException      If an error occurs during the database operation for the candidate.
     * @throws CandidateNotFoundException If the candidate is not found in the system.
     */
    void removeAllVotesOfSpecificCid(Integer votedCid) throws UserDAOException, CandidateDAOException, CandidateNotFoundException;

    /**
     * Changes the password for a specific user.
     *
     * @param user The {@link User} object representing the user whose password is to be changed.
     * @param dto  The {@link ChangePasswordDTO} containing the current and new passwords.
     * @return The updated {@link User} object after the password change.
     * @throws UserNotFoundException   If the user is not found in the system.
     * @throws WrongPasswordException  If the current password provided is incorrect.
     * @throws UserDAOException        If an error occurs during the database operation.
     */
    User changePassword(User user, ChangePasswordDTO dto) throws UserNotFoundException, WrongPasswordException, UserDAOException;

    /**
     * Creates an admin user.
     * @throws UserDAOException
     */
    void createAdminAccount() throws UserDAOException;
}
