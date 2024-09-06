package personal.dto;

/**
 * Data Transfer Object (DTO) used for transferring candidate data along with their vote counts
 * from the service layer to the view layer in a read-only manner.
 *
 * @author Michail E. Koutrakis
 */
public class CandidatesWithVotesReadOnlyDTO {
    private Integer cid;
    private String firstname;
    private String lastname;
    private Integer totalVotes;

    public CandidatesWithVotesReadOnlyDTO() {}

    public CandidatesWithVotesReadOnlyDTO(Integer cid, String firstname, String lastname, Integer totalVotes) {
        this.cid = cid;
        this.firstname = firstname;
        this.lastname = lastname;
        this.totalVotes = totalVotes;
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

    public Integer getTotalVotes() {
        return totalVotes;
    }

    public void setTotalVotes(Integer totalVotes) {
        this.totalVotes = totalVotes;
    }

    @Override
    public String toString() {
        return cid.toString() + "," + firstname + ","
                + lastname + "," + totalVotes.toString();
    }
}
