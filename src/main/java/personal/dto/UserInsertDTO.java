package personal.dto;

import java.util.Date;

/**
 * Data Transfer Object (DTO) used for transferring data required to create a new user
 * from the view layer to the service layer.
 */
public class UserInsertDTO {
    private String username;
    private String email;
    private String firstname;
    private String lastname;
    private Date dateOfBirth;
    private String password;
    private String reEnteredPassword;

    public UserInsertDTO() {}

    public UserInsertDTO(String username, String email, String firstname, String lastname,
                         Date dateOfBirth, String password, String reEnteredPassword) {
        this.username = username;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.dateOfBirth = dateOfBirth;
        this.password = password;
        this.reEnteredPassword = reEnteredPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getReEnteredPassword() {
        return reEnteredPassword;
    }

    public void setReEnteredPassword(String reEnteredPassword) {
        this.reEnteredPassword = reEnteredPassword;
    }

    @Override
    public String toString() {
        return "UserInsertDTO{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", password='" + password + '\'' +
                ", reEnteredPassword='" + reEnteredPassword + '\'' +
                '}';
    }
}
