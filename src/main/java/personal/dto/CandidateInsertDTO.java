package personal.dto;

/**
 * Data Transfer Object (DTO) used for transferring candidate data
 * from the view layer to the service layer during a candidate insertion operation.
 */
public class CandidateInsertDTO {
    private String firstname;
    private String lastname;

    public CandidateInsertDTO() {
    }

    public CandidateInsertDTO(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Override
    public String toString() {
        return "CandidateInsertDTO{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}
