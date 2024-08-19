package personal.dao.exceptions;

public class UserDAOException extends Exception {
    private final static long serialVersionUID = 1L;

    public UserDAOException(String message) {
        super(message);
    }
}
