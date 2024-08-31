package personal.dao;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import personal.dao.exceptions.UserDAOException;
import personal.model.User;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOImplTest {
    private final IUserDAO userDAO = new UserDAOImpl();

    @Test
    void insert() throws UserDAOException {
        User user = new User();
        user.setUsername("darkKnight");
        user.setEmail("fire@gmail.com");
        user.setFirstname("Michalis");
        user.setLastname("K");
        user.setDob(new Date(System.currentTimeMillis()));
        user.setPassword(BCrypt.hashpw("123", BCrypt.gensalt(12)));
        user.setHasVoted(0);

        userDAO.insert(user);
    }

    @Test
    void getById() throws UserDAOException {
        User retrievedUser = userDAO.getById(2);
        assertEquals("dragon", retrievedUser.getUsername());
        assertEquals("fire@gmail.com", retrievedUser.getEmail());
        assertEquals("Michalis", retrievedUser.getFirstname());
        assertEquals("K", retrievedUser.getLastname());
        assertEquals("2024-08-19", retrievedUser.getDob().toString());
        assertTrue(BCrypt.checkpw("123", retrievedUser.getPassword()));
        System.out.println(retrievedUser);
    }

    @Test
    void update() throws UserDAOException {
        User user = userDAO.getById(2);
        user.setHasVoted(1);
        user.setVotedCid(3);
        userDAO.update(user);
        User updated = userDAO.getById(2);
        assertEquals(1, updated.getHasVoted());
        assertEquals(3, updated.getVotedCid());
    }

    @Test
    void delete() throws UserDAOException {
        userDAO.delete(3);
        User retrievedUser = userDAO.getById(3);
        assertNull(retrievedUser);
    }

    @Test
    void getByUsername() throws UserDAOException {
        User foundUser = userDAO.getByUsername("gkoutraa");
        System.out.println(foundUser);
        assertNull(foundUser);
    }

    @Test
    void userExistence() throws UserDAOException {
        boolean userExistsByUsername = userDAO.usernameExists("user007");
        boolean userExistsByEmail = userDAO.emailExists("abc@gmail.com");
        assertTrue(userExistsByUsername);
        assertTrue(userExistsByEmail);
    }
}