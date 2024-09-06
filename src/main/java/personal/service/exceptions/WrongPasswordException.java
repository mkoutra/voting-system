package personal.service.exceptions;

/**
 * @author Michail E. Koutrakis
 */
public class WrongPasswordException extends Exception {
    private final static long serialVersionUID = 1L;

    public WrongPasswordException(String message) {
        super(message);
    }
}
