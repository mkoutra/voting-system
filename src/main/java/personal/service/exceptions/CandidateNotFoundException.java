package personal.service.exceptions;

public class CandidateNotFoundException extends Exception {
    private final static long serialVersionUID = 1L;

    public CandidateNotFoundException(String message) {
        super(message);
    }
}
