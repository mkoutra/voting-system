package personal.dto;

/**
 * Data Transfer Object (DTO) used for transferring candidate data
 * from the service layer to the view layer in a read-only manner.
 */
public class CandidateReadOnlyDTO {
    private Integer cid;
    private String firstname;
    private String lastname;

    public CandidateReadOnlyDTO() {}

    public CandidateReadOnlyDTO(Integer cid, String firstname, String lastname) {
        this.cid = cid;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
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
}
