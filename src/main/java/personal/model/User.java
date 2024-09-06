package personal.model;

import java.util.Date;

/**
 * A class representing the User.
 * The same fields are used in the database.
 *
 * @author Michail E. Koutrakis
 */
public class User {
    private Integer uid;
    private String username;
    private String password;
    private String email;
    private String firstname;
    private String lastname;
    private Date dob;
    private Integer hasVoted;   // 1 if the user has voted, 0 otherwise
    private Integer votedCid;   // It is null if the user hasn't voted.

    public User() {}

    public User(Integer uid, String username, String password, String email, String firstname,
                String lastname, Date dob, Integer hasVoted, Integer votedCid) {
        this.uid = uid;
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.dob = dob;
        this.hasVoted = hasVoted;
        this.votedCid = votedCid;
    }

    // Copy constructor
    public User(User user) {
        this.uid = user.getUid();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.dob = user.getDob();
        this.hasVoted = user.getHasVoted();
        this.votedCid = user.getVotedCid();
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Integer getHasVoted() {
        return hasVoted;
    }

    public void setHasVoted(Integer hasVoted) {
        this.hasVoted = hasVoted;
    }

    public Integer getVotedCid() {
        return votedCid;
    }

    public void setVotedCid(Integer votedCid) {
        this.votedCid = votedCid;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", dob=" + dob +
                ", hasVoted=" + hasVoted +
                ", votedCid=" + votedCid +
                '}';
    }
}
