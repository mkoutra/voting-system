package personal.dao;

import personal.dao.exceptions.UserDAOException;
import personal.model.User;

public interface IUserDAO {
    User insert(User user) throws UserDAOException;
    User update(User user) throws UserDAOException;
    void delete(Integer uid) throws UserDAOException;
    User getById(Integer uid) throws UserDAOException;
    User getByUsername(String username) throws UserDAOException;
    boolean usernameExists(String username) throws UserDAOException;
    boolean emailExists(String email) throws UserDAOException;
}
