package personal.service;

import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import personal.dao.IUserDAO;
import personal.dao.UserDAOImpl;
import personal.dao.exceptions.UserDAOException;
import personal.dto.CandidateReadOnlyDTO;
import personal.dto.ChangePasswordDTO;
import personal.dto.UserInsertDTO;
import personal.dto.UserReadOnlyDTO;
import personal.model.User;
import personal.service.exceptions.UserNotFoundException;
import personal.service.exceptions.WrongPasswordException;

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

    @Test
    void voteACandidate() {
        UserReadOnlyDTO userReadOnlyDTO = new UserReadOnlyDTO("taxiDriver", "M", "K");
        CandidateReadOnlyDTO candidateReadOnlyDTO = new CandidateReadOnlyDTO(3, "random", "random");

        try {
            userService.voteACandidate(userReadOnlyDTO, candidateReadOnlyDTO);
            User afterVotingUser = userService.getUserByUsername(userReadOnlyDTO.getUsername());
            assertEquals("taxiDriver", afterVotingUser.getUsername());
            assertEquals(3, afterVotingUser.getVotedCid());
            assertEquals(1, afterVotingUser.getHasVoted());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void changePassword() throws UserNotFoundException, UserDAOException, WrongPasswordException {
        User user = userService.getUserByUsername("Michalis");
        ChangePasswordDTO dto = new ChangePasswordDTO("AAAa1234567*", "Aa1234567*", "Aa1234567*");

        User updatedUser = userService.changePassword(user, dto);

        assertEquals("Michalis", updatedUser.getUsername());
        assertTrue(BCrypt.checkpw("Aa1234567*", updatedUser.getPassword()));
    }
}