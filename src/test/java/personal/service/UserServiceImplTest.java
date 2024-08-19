package personal.service;

import org.junit.jupiter.api.Test;
import personal.dao.IUserDAO;
import personal.dao.UserDAOImpl;
import personal.dao.exceptions.UserDAOException;
import personal.dto.UserInsertDTO;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {
    private final IUserDAO userDAO = new UserDAOImpl();
    private final IUserService userService = new UserServiceImpl(userDAO);

    @Test
    void insertUser() throws UserDAOException {
        UserInsertDTO insertDTO = new UserInsertDTO("gkoutra", "123@123.sa", "G", "K", new Date(System.currentTimeMillis()),
                "pass", "passo");

        userService.insertUser(insertDTO);
    }
}