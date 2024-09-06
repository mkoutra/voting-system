package personal.dto;

/**
 * Data Transfer Object (DTO) used for transferring candidate data
 * from the service layer to the view layer in a read-only manner.
 *
 * @author Michail E. Koutrakis
 */
public class CandidateReadOnlyDTO extends NamesDTO {
    private Integer cid;

    public CandidateReadOnlyDTO() {}

    public CandidateReadOnlyDTO(Integer cid, String firstname, String lastname) {
        super(firstname, lastname);
        this.cid = cid;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }
}
