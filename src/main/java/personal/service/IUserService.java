package personal.service;

import personal.dao.exceptions.UserDAOException;
import personal.dao.exceptions.CandidateDAOException;
import personal.dto.CandidateReadOnlyDTO;
import personal.dto.UserReadOnlyDTO;
import personal.service.exceptions.UserNotFoundException;
import personal.service.exceptions.CandidateNotFoundException;

import personal.dto.UserInsertDTO;
import personal.model.User;

import java.util.List;

public interface IUserService {
    void insertUser(UserInsertDTO dto) throws UserDAOException;
    void voteACandidate(UserReadOnlyDTO userReadOnlyDTO, CandidateReadOnlyDTO candidateReadOnlyDTO)
            throws CandidateNotFoundException, UserNotFoundException, UserDAOException, CandidateDAOException;
    boolean userExistsByUsername(String username) throws UserDAOException;
    boolean userExistsByEmail(String email) throws UserDAOException;
    User getUserByUsername(String username) throws UserNotFoundException,UserDAOException;
    boolean authenticateUser(String username, String plainPassword) throws UserNotFoundException, UserDAOException;
    boolean checkIfUserHasVoted(String username) throws UserNotFoundException, UserDAOException;
    List<User> getAllUsers() throws UserDAOException;
    List<User> getAllUsersByVotedCid(Integer votedCid) throws UserDAOException, CandidateDAOException, CandidateNotFoundException;
    void removeAllVotesOfSpecificCid(Integer votedCid) throws UserDAOException, CandidateDAOException, CandidateNotFoundException;
}
