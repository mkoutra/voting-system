package personal.dto;

/**
 * Data Transfer Object (DTO) used for transferring candidate data along with their vote counts
 * from the service layer to the view layer in a read-only manner.
 *
 * @author Michail E. Koutrakis
 */
public class CandidatesWithVotesReadOnlyDTO extends NamesDTO {
    private Integer cid;
    private Integer totalVotes;

    public CandidatesWithVotesReadOnlyDTO() {}

    public CandidatesWithVotesReadOnlyDTO(Integer cid, String firstname, String lastname, Integer totalVotes) {
        super(firstname, lastname);
        this.cid = cid;
        this.totalVotes = totalVotes;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getTotalVotes() {
        return totalVotes;
    }

    public void setTotalVotes(Integer totalVotes) {
        this.totalVotes = totalVotes;
    }

    @Override
    public String toString() {
        return cid.toString() + "," + super.getFirstname() + ","
                + super.getLastname() + "," + totalVotes.toString();
    }
}
