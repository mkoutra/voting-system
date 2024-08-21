package personal.service;

import personal.service.exceptions.UserNotFoundException;
import personal.dao.exceptions.UserDAOException;

import personal.dto.UserInsertDTO;
import personal.model.User;

public interface IUserService {
    void insertUser(UserInsertDTO dto) throws UserDAOException;
    boolean userExistsByUsername(String username) throws UserDAOException;
    boolean userExistsByEmail(String email) throws UserDAOException;
    User getUserByUsername(String username) throws UserNotFoundException,UserDAOException;
    boolean authenticateUser(String username, String plainPassword) throws UserNotFoundException, UserDAOException;
}
