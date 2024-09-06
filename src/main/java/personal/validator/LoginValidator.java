package personal.validator;

import personal.dao.IUserDAO;
import personal.dao.UserDAOImpl;
import personal.dao.exceptions.UserDAOException;
import personal.dto.UserLoginDTO;
import personal.service.IUserService;
import personal.service.UserServiceImpl;
import personal.service.exceptions.UserNotFoundException;

/**
 * @author Michail E. Koutrakis
 */
public class LoginValidator {
    private final IUserDAO userDAO = new UserDAOImpl();
    private final IUserService userService = new UserServiceImpl(userDAO);

    public LoginValidator() {}

    public boolean validate(UserLoginDTO dto) {
        if (dto == null || dto.getUsername() == null || dto.getPassword() == null
            || dto.getUsername().isEmpty() || dto.getPassword().isEmpty()) {
            return false;
        }

        try {
            return userService.authenticateUser(dto.getUsername(), dto.getPassword());
        } catch (UserNotFoundException | UserDAOException e) {
            return false;
        }
    }
}
