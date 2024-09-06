package personal.dto;

/**
 * An abstract class containing the firstname and lastname fields
 * that are frequently needed in DTO subclasses.
 *
 * @author Michail E. Koutrakis
 */
public abstract class NamesDTO {
    private String firstname;
    private String lastname;

    public NamesDTO() {}

    public NamesDTO(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
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
