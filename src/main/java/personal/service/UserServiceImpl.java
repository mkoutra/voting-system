package personal.service;

import org.mindrot.jbcrypt.BCrypt;
import personal.dao.IUserDAO;
import personal.dao.exceptions.UserDAOException;
import personal.dto.UserInsertDTO;
import personal.model.User;

public class UserServiceImpl implements IUserService {
    private final IUserDAO userDAO;

    public UserServiceImpl(IUserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public void insertUser(UserInsertDTO dto) throws UserDAOException {
        User user = mapUserInsertDTOToUser(dto);
        userDAO.insert(user);
    }

    @Override
    public boolean userExistsByUsername(String username) throws UserDAOException {
        return userDAO.usernameExists(username);
    }

    @Override
    public boolean userExistsByEmail(String email) throws UserDAOException {
        return userDAO.emailExists(email);
    }

    private static User mapUserInsertDTOToUser(UserInsertDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setFirstname(dto.getFirstname());
        user.setLastname(dto.getLastname());
        user.setDob(dto.getDateOfBirth());
        user.setPassword(BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt(12)));
        user.setHasVoted(0);
        return user;
    }
}
