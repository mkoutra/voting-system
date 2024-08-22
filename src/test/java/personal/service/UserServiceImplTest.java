package personal.service;

import org.junit.jupiter.api.Test;
import personal.dao.IUserDAO;
import personal.dao.UserDAOImpl;
import personal.dao.exceptions.UserDAOException;
import personal.dto.CandidateReadOnlyDTO;
import personal.dto.UserInsertDTO;
import personal.dto.UserReadOnlyDTO;
import personal.model.User;

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
}