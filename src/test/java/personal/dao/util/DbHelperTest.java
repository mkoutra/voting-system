package personal.dao.util;

import org.junit.jupiter.api.Test;
import personal.dao.CandidateDAOImpl;
import personal.dao.ICandidateDAO;
import personal.dao.IUserDAO;
import personal.dao.UserDAOImpl;
import personal.dao.exceptions.CandidateDAOException;
import personal.dao.exceptions.UserDAOException;
import personal.service.CandidateServiceImpl;
import personal.service.ICandidateService;
import personal.service.IUserService;
import personal.service.UserServiceImpl;

import static org.junit.jupiter.api.Assertions.*;

class DbHelperTest {
    private final static IUserDAO userDAO = new UserDAOImpl();
    private final static IUserService userService = new UserServiceImpl(userDAO);

    private final static ICandidateDAO candidateDAO = new CandidateDAOImpl();
    private final static ICandidateService candidateService = new CandidateServiceImpl(candidateDAO);

    @Test
    void createUsers() throws UserDAOException {
        DbHelper.createDummyUsers(3);
    }

    @Test
    void createCandidates() throws CandidateDAOException {
        DbHelper.createDummyCandidates(3);
    }
}