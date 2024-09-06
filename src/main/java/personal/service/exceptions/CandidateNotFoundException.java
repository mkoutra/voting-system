package personal.service.exceptions;

/**
 * @author Michail E. Koutrakis
 */
public class CandidateNotFoundException extends Exception {
    private final static long serialVersionUID = 1L;

    public CandidateNotFoundException(String message) {
        super(message);
    }
}
