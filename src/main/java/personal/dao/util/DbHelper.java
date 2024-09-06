package personal.dao.util;

import personal.dao.CandidateDAOImpl;
import personal.dao.ICandidateDAO;
import personal.dao.IUserDAO;
import personal.dao.UserDAOImpl;
import personal.dao.exceptions.CandidateDAOException;
import personal.dao.exceptions.UserDAOException;
import personal.dto.CandidateInsertDTO;
import personal.dto.UserInsertDTO;
import personal.service.CandidateServiceImpl;
import personal.service.ICandidateService;
import personal.service.IUserService;
import personal.service.UserServiceImpl;
import personal.service.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Class to help with testing of DAO
 *
 * @author Michail E. Koutrakis
 */
public class DbHelper {
    private final static IUserDAO userDAO = new UserDAOImpl();
    private final static IUserService userService = new UserServiceImpl(userDAO);

    private final static ICandidateDAO candidateDAO = new CandidateDAOImpl();
    private final static ICandidateService candidateService = new CandidateServiceImpl(candidateDAO);

    private DbHelper() {}

    /**
     * Erase all data from every table inside the 'votingDB' database.
     */
    public static void eraseAllData() throws SQLException {
        String sqlFKOff = "SET @@foreign_key_checks = 0";   // To disable foreign key checks
        String sqlFKOn = "SET @@foreign_key_checks = 1";    // To enable foreign key checks

        // To get the names of the Database tables
        String sqlSelect = "SELECT TABLE_NAME FROM information_schema.tables WHERE TABLE_SCHEMA = 'votingDB';";

        ResultSet rs;
        List<String> tables;

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps1 = connection.prepareStatement(sqlFKOff);
             PreparedStatement ps2 = connection.prepareStatement(sqlSelect)){

            ps1.executeUpdate();        // Disable foreign key checks

            rs = ps2.executeQuery();    // Get the names of all database tables
            tables = mapRsToList(rs);

            for (String table : tables) {
                // Delete all records
                connection.prepareStatement("DELETE FROM " + table).executeUpdate();
                // Reset auto-increment to 1
                connection.prepareStatement("ALTER TABLE " + table + " AUTO_INCREMENT=1").executeUpdate();
            }

            connection.prepareStatement(sqlFKOn).executeUpdate();   // Re-activate foreign key checks
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void createDummyCandidates(int totalNumberOfCandidates) throws CandidateDAOException {
        List<String> firstnames = wordRepeater("CandidateFirstname", totalNumberOfCandidates);
        List<String> lastnames = wordRepeater("CandidateLastname", totalNumberOfCandidates);

        for (int i = 0; i < totalNumberOfCandidates; i++) {
            CandidateInsertDTO candidateInsertDTO = new CandidateInsertDTO();
            candidateInsertDTO.setFirstname(firstnames.get(i));
            candidateInsertDTO.setLastname(lastnames.get(i));

            candidateService.insertCandidate(candidateInsertDTO);
        }
    }

    public static void createDummyUsers(int totalNumberOfUsers) throws UserDAOException {
        List<String> usernames = wordRepeater("user", totalNumberOfUsers);
        List<String> emails = wordRepeater("email@gmail.c", totalNumberOfUsers);
        List<String> firstnames = wordRepeater("firstname", totalNumberOfUsers);
        List<String> lastnames = wordRepeater("lastname", totalNumberOfUsers);
        List<String> passwords = wordRepeater("Password*", totalNumberOfUsers);
        List<String> reEnteredPasswords =  wordRepeater("Password*", totalNumberOfUsers);

        for (int i = 0; i < totalNumberOfUsers; i++) {
            UserInsertDTO userInsertDTO = new UserInsertDTO();
            userInsertDTO.setUsername(usernames.get(i));
            userInsertDTO.setEmail(emails.get(i));
            userInsertDTO.setFirstname(firstnames.get(i));
            userInsertDTO.setLastname(lastnames.get(i));
            userInsertDTO.setDateOfBirth(new Date(System.currentTimeMillis()));
            userInsertDTO.setPassword(passwords.get(i));
            userInsertDTO.setReEnteredPassword(reEnteredPasswords.get(i));

            userService.insertUser(userInsertDTO);
        }
    }

    public static void eraseDbTable(String tableName) throws SQLException {
        String sqlFKOff = "SET @@foreign_key_checks = 0;";   // To disable foreign key checks
        String sqlFKOn = "SET @@foreign_key_checks = 1;";    // To enable foreign key checks

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps1 = connection.prepareStatement(sqlFKOff)){

            // Disable foreign key checks
            ps1.executeUpdate();

            // Delete all records
            connection.prepareStatement("DELETE FROM " + tableName + ';').executeUpdate();

            // Reset auto-increment to 1
            connection.prepareStatement("ALTER TABLE " + tableName + " AUTO_INCREMENT=1;").executeUpdate();

            // Re-activate foreign key checks
            connection.prepareStatement(sqlFKOn).executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    private static List<String> mapRsToList(ResultSet rs) throws SQLException {
        List<String> tables = new ArrayList<>();    // Contains the names of the MySQL Database tables

        while(rs.next()) {
            tables.add(rs.getString("TABLE_NAME"));
        }

        return tables;
    }

    private static List<String> wordRepeater(String word, int length) {
        List<String> wordArray = new ArrayList<>(length);
        for (int i = 1; i <= length; i++) wordArray.add(word + String.valueOf(i));
        return wordArray;
    }
}
