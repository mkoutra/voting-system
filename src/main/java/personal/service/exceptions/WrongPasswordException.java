package personal.service.exceptions;

public class WrongPasswordException extends Exception {
    private final static long serialVersionUID = 1L;

    public WrongPasswordException(String message) {
        super(message);
    }
}
