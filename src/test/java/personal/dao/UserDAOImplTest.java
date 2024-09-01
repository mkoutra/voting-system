package personal.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import personal.dao.exceptions.CandidateDAOException;
import personal.dao.exceptions.UserDAOException;
import personal.dao.util.DbHelper;
import personal.model.User;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserDAOImplTest {
    private static IUserDAO userDAO;

    @BeforeAll
    public static void preparation() throws SQLException {
        userDAO = new UserDAOImpl();
        DbHelper.eraseAllData();
    }

    @BeforeEach
    public void setup() throws UserDAOException, CandidateDAOException {
        DbHelper.createDummyCandidates(3);
        DbHelper.createDummyUsers(3);
    }

    @AfterEach
    public void tearDown() throws SQLException {
        DbHelper.eraseAllData();
    }

    @Test
    void getById() throws UserDAOException {
        User retrievedUser = userDAO.getById(2);
        assertEquals("user2", retrievedUser.getUsername());
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
        User nobody = userDAO.getByUsername("user0");
        assertNull(nobody);

        User existedUser = userDAO.getByUsername("user1");
        assertEquals("firstname1", existedUser.getFirstname());
    }

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
        assertEquals(4, userDAO.getById(4).getUid());
        assertEquals(4, userDAO.getByUsername("darkKnight").getUid());
    }

    @Test
    void userExistence() throws UserDAOException {
        boolean userExistsByUsername = userDAO.usernameExists("uuser1");
        boolean userExistsByEmail = userDAO.emailExists("email@gmail.c2");
        assertFalse(userExistsByUsername);
        assertTrue(userExistsByEmail);
    }

    @Test
    void getUsersBasedOnVoting() throws UserDAOException {
        User user1 = userDAO.getById(1);
        User user2 = userDAO.getById(3);

        user1.setHasVoted(1);
        user1.setVotedCid(3);
        userDAO.update(user1);

        user2.setHasVoted(1);
        user2.setVotedCid(3);
        userDAO.update(user2);

        List<User> candidate3Voters = userDAO.getUsersByVotedCid(3);
        candidate3Voters.sort(Comparator.comparing(User::getUid));
        assertEquals(1, candidate3Voters.get(0).getUid());
        assertEquals(3, candidate3Voters.get(1).getUid());
    }

    @Test
    void allUsers() throws UserDAOException {
        List<User> users = userDAO.getAllUsers();

        users.sort(Comparator.comparing(User::getUid));
        assertEquals(1, users.get(0).getUid());
        assertEquals(2, users.get(1).getUid());
        assertEquals(3, users.get(2).getUid());
    }

    @Test
    void removeSpecificVotes() throws UserDAOException {
        User user1 = userDAO.getById(1);
        User user2 = userDAO.getById(3);

        user1.setHasVoted(1);
        user1.setVotedCid(3);
        userDAO.update(user1);

        user2.setHasVoted(1);
        user2.setVotedCid(3);
        userDAO.update(user2);

        List<User> candidate3Voters = userDAO.getUsersByVotedCid(3);
        candidate3Voters.sort(Comparator.comparing(User::getUid));
        assertEquals(1, candidate3Voters.get(0).getUid());
        assertEquals(3, candidate3Voters.get(1).getUid());

        userDAO.removeAllVotesOfSpecificCid(3);

        List<User> emptyList = userDAO.getUsersByVotedCid(3);
        assertTrue(emptyList.isEmpty());
    }
}