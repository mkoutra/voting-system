package personal.dto;

/**
 * Data Transfer Object (DTO) used for transferring candidate data
 * from the view layer to the service layer during a candidate insertion operation.
 *
 * @author Michail E. Koutrakis
 */
public class CandidateInsertDTO extends NamesDTO {
    public CandidateInsertDTO() {
    }

    public CandidateInsertDTO(String firstname, String lastname) {
        super(firstname, lastname);
    }

    @Override
    public String toString() {
        return "CandidateInsertDTO{" +
                "firstname='" + super.getFirstname() + '\'' +
                ", lastname='" + super.getLastname() + '\'' +
                '}';
    }
}
