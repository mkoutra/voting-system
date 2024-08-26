package personal.dto;

public class CandidateUpdateDTO {
    private Integer cid;
    private String firstname;
    private String lastname;

    public CandidateUpdateDTO() {
    }

    public CandidateUpdateDTO(Integer cid, String firstname, String lastname) {
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

    @Override
    public String toString() {
        return "CandidateUpdateDTO{" +
                "cid=" + cid +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}
