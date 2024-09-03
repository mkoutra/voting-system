package personal.dao;

import personal.dao.exceptions.UserDAOException;
import personal.model.User;

import java.util.List;

/**
 * An interface for managing user data in the DAO (Data Access Object) layer.
 */
public interface IUserDAO {
    /**
     * Inserts a new user into the database.
     *
     * @param user The user object to insert.
     * @return The inserted user object.
     * @throws UserDAOException If there is an error accessing the database, such as a SQL exception.
     */
    User insert(User user) throws UserDAOException;

    /**
     * Updates an existing user in the database.
     * The user's ID (uid) must match the ID of an existing user.
     *
     * @param user The user object containing updated information.
     * @return The updated user object.
     * @throws UserDAOException If there is an error accessing the database, such as a SQL exception.
     */
    User update(User user) throws UserDAOException;

    /**
     * Deletes the user with the specified ID from the database.
     *
     * @param uid The ID of the user to delete.
     * @throws UserDAOException If there is an error accessing the database, such as a SQL exception.
     */
    void delete(Integer uid) throws UserDAOException;

    /**
     * Retrieves the user with the specified ID from the database.
     *
     * @param uid The ID of the user to retrieve.
     * @return The user object with the specified ID, or null if no user is found.
     * @throws UserDAOException If there is an error accessing the database, such as a SQL exception.
     */
    User getById(Integer uid) throws UserDAOException;

    /**
     * Retrieves the user with the specified username from the database.
     *
     * @param username The username of the user to retrieve.
     * @return The user object with the specified username, or null if no user is found.
     * @throws UserDAOException If there is an error accessing the database, such as a SQL exception.
     */
    User getByUsername(String username) throws UserDAOException;

    /**
     * Checks if a user with the specified username exists in the database.
     *
     * @param username The username to check for existence.
     * @return True if a user with the given username exists, false otherwise.
     * @throws UserDAOException If there is an error accessing the database, such as a SQL exception.
     */
    boolean usernameExists(String username) throws UserDAOException;

    /**
     * Checks if a user with the specified email exists in the database.
     *
     * @param email The email to check for existence.
     * @return True if a user with the given email exists, false otherwise.
     * @throws UserDAOException If there is an error accessing the database, such as a SQL exception.
     */
    boolean emailExists(String email) throws UserDAOException;

    /**
     * Retrieves all users from the database.
     *
     * @return A list of all user objects. If no users are found, an empty list is returned.
     * @throws UserDAOException If there is an error accessing the database, such as a SQL exception.
     */
    List<User> getAllUsers() throws UserDAOException;

    /**
     * Retrieves a list of users who have voted for the candidate with the specified ID.
     *
     * @param cid The ID of the candidate for whom the users have voted.
     * @return A list of user objects who have voted for the specified candidate.
     *         If no users have voted for the candidate, an empty list is returned.
     * @throws UserDAOException If there is an error accessing the database, such as a SQL exception.
     */
    List<User> getUsersByVotedCid(Integer cid) throws UserDAOException;

    /**
     * Removes all votes associated with the specified candidate ID.
     *
     * @param votedCid The ID of the candidate for whom all votes should be removed.
     * @throws UserDAOException If there is an error accessing the database, such as a SQL exception.
     */
    void removeAllVotesOfSpecificCid(Integer votedCid) throws UserDAOException;
}
