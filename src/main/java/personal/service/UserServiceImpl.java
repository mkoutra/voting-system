package personal.service;

import org.mindrot.jbcrypt.BCrypt;
import personal.dao.CandidateDAOImpl;
import personal.dao.ICandidateDAO;
import personal.dao.IUserDAO;
import personal.dao.exceptions.CandidateDAOException;
import personal.dao.exceptions.UserDAOException;
import personal.dto.CandidateReadOnlyDTO;
import personal.dto.UserInsertDTO;
import personal.dto.UserReadOnlyDTO;
import personal.model.User;
import personal.service.exceptions.CandidateNotFoundException;
import personal.service.exceptions.UserNotFoundException;

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

    @Override
    public User getUserByUsername(String username) throws UserNotFoundException, UserDAOException {
        try {
           if (!userDAO.usernameExists(username)) {
               throw new UserNotFoundException("Username: " + username + " does not exist.");
           }
           return userDAO.getByUsername(username);
        } catch (UserDAOException e1) {
            throw new UserDAOException("SQL error in get user by username: " + username);
        }
    }

    @Override
    public boolean authenticateUser(String username, String plainPassword)
            throws UserNotFoundException, UserDAOException {
        if (!userDAO.usernameExists(username)) {
            throw new UserNotFoundException("Username: " + username + " does not exist.");
        }
        User user = userDAO.getByUsername(username);
        return BCrypt.checkpw(plainPassword, user.getPassword());
    }

    @Override
    public void voteACandidate(UserReadOnlyDTO userReadOnlyDTO, CandidateReadOnlyDTO candidateReadOnlyDTO)
            throws CandidateNotFoundException, UserNotFoundException, UserDAOException, CandidateDAOException {
        ICandidateDAO candidateDAO = new CandidateDAOImpl();
        try {
            if (!userDAO.usernameExists(userReadOnlyDTO.getUsername())) {
                throw new UserNotFoundException("User: " + userReadOnlyDTO.getUsername() + " does not exist.");
            }
            if (!candidateDAO.cidExists(candidateReadOnlyDTO.getCid())) {
                throw new CandidateNotFoundException("Candidate with id: " + candidateReadOnlyDTO.getCid() + " does not exist.");
            }

            User voter = userDAO.getByUsername(userReadOnlyDTO.getUsername());
            voter.setHasVoted(1);
            voter.setVotedCid(candidateReadOnlyDTO.getCid());
            userDAO.update(voter);
        } catch (CandidateDAOException | UserDAOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public boolean checkIfUserHasVoted(String username) throws UserNotFoundException, UserDAOException {
        if (!userDAO.usernameExists(username)) {
            throw new UserNotFoundException("Username: " + username + " does not exist.");
        }
        User user = userDAO.getByUsername(username);
        return user.getHasVoted() == 1;
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
