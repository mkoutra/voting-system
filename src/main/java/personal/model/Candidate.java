package personal.model;

/**
 * A class representing the Candidate.
 * The same fields are used in the database.
 *
 * @author Michail E. Koutrakis
 */
public class Candidate {
    private Integer cid;
    private String firstname;
    private String lastname;

    public Candidate() {}

    public Candidate(Integer cid, String firstname, String lastname) {
        this.cid = cid;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    // Copy Constructor
    public Candidate(Candidate candidate) {
        this.cid = candidate.getCid();
        this.firstname = candidate.getFirstname();
        this.lastname = candidate.getLastname();
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

    @Override
    public String toString() {
        return "Candidate{" +
                "cid=" + cid +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}
