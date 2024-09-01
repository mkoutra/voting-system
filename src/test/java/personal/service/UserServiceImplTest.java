package personal.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import personal.dao.IUserDAO;
import personal.dao.UserDAOImpl;
import personal.dao.exceptions.CandidateDAOException;
import personal.dao.exceptions.UserDAOException;
import personal.dao.util.DbHelper;
import personal.dto.CandidateReadOnlyDTO;
import personal.dto.ChangePasswordDTO;
import personal.dto.UserInsertDTO;
import personal.dto.UserReadOnlyDTO;
import personal.model.User;
import personal.service.exceptions.UserNotFoundException;
import personal.service.exceptions.WrongPasswordException;

import java.sql.SQLException;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {
    private static IUserDAO userDAO;
    private static IUserService userService;

    @BeforeAll
    public static void preparation() throws SQLException {
        userDAO = new UserDAOImpl();
        userService = new UserServiceImpl(userDAO);
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
    void insertUser() throws UserDAOException {
        UserInsertDTO insertDTO = new UserInsertDTO("user4", "123@123.sa",
                "firstname4", "lastname4", new Date(System.currentTimeMillis()),
                "pass", "pass");

        userService.insertUser(insertDTO);

        User user = userDAO.getById(4);
        assertEquals("firstname4", user.getFirstname());
    }

    @Test
    void voteACandidate() {
        UserReadOnlyDTO userReadOnlyDTO = new UserReadOnlyDTO("user1", "firstname1", "lastname1");
        CandidateReadOnlyDTO candidateReadOnlyDTO = new CandidateReadOnlyDTO(3, "CandidateFirstname3", "CandidateLastname3");

        try {
            userService.voteACandidate(userReadOnlyDTO, candidateReadOnlyDTO);
            User afterVotingUser = userService.getUserByUsername(userReadOnlyDTO.getUsername());

            assertEquals("user1", afterVotingUser.getUsername());
            assertEquals(3, afterVotingUser.getVotedCid());
            assertEquals(1, afterVotingUser.getHasVoted());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void changePassword() throws UserNotFoundException, UserDAOException, WrongPasswordException {
        User user = userService.getUserByUsername("user1");
        ChangePasswordDTO dto = new ChangePasswordDTO("Password*1", "Password*100", "Password*100");

        User updatedUser = userService.changePassword(user, dto);

        assertEquals("user1", updatedUser.getUsername());
        assertTrue(BCrypt.checkpw("Password*100", updatedUser.getPassword()));
    }
}