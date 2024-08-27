package personal.dao;

import personal.dao.exceptions.UserDAOException;
import personal.model.User;

import java.util.List;

public interface IUserDAO {
    User insert(User user) throws UserDAOException;
    User update(User user) throws UserDAOException;
    void delete(Integer uid) throws UserDAOException;
    User getById(Integer uid) throws UserDAOException;
    User getByUsername(String username) throws UserDAOException;
    boolean usernameExists(String username) throws UserDAOException;
    boolean emailExists(String email) throws UserDAOException;
    List<User> getAllUsers() throws UserDAOException;
    List<User> getUsersByVotedCid(Integer cid) throws UserDAOException;
    void removeAllVotesOfSpecificCid(Integer votedCid) throws UserDAOException;
}
