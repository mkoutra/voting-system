package personal.dao.exceptions;

/**
 * @author Michail E. Koutrakis
 */
public class UserDAOException extends Exception {
    private final static long serialVersionUID = 1L;

    public UserDAOException(String message) {
        super(message);
    }
}
