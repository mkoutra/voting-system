package personal.dto;

/**
 * Data Transfer Object (DTO) used for transferring updated candidate data
 * from the view layer to the service layer during an update operation.
 *
 * @author Michail E. Koutrakis
 */
public class CandidateUpdateDTO extends NamesDTO {
    private Integer cid;

    public CandidateUpdateDTO() {}

    public CandidateUpdateDTO(Integer cid, String firstname, String lastname) {
        super(firstname, lastname);
        this.cid = cid;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    @Override
    public String toString() {
        return "CandidateUpdateDTO{" +
                "cid=" + cid +
                ", firstname='" + super.getFirstname() + '\'' +
                ", lastname='" + super.getLastname() + '\'' +
                '}';
    }
}
