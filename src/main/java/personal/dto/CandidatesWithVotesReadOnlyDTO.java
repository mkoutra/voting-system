package personal.dto;

/**
 * Data Transfer Object (DTO) used for transferring candidate data along with their vote counts
 * from the service layer to the view layer in a read-only manner.
 *
 * @author Michail E. Koutrakis
 */
public class CandidatesWithVotesReadOnlyDTO extends CandidateReadOnlyDTO {
    private Integer totalVotes;

    public CandidatesWithVotesReadOnlyDTO() {}

    public CandidatesWithVotesReadOnlyDTO(Integer cid, String firstname, String lastname, Integer totalVotes) {
        super(cid, firstname, lastname);
        this.totalVotes = totalVotes;
    }

    public Integer getTotalVotes() {
        return totalVotes;
    }

    public void setTotalVotes(Integer totalVotes) {
        this.totalVotes = totalVotes;
    }

    @Override
    public String toString() {
        return super.getCid().toString() + "," + super.getFirstname() + ","
                + super.getLastname() + "," + totalVotes.toString();
    }
}
