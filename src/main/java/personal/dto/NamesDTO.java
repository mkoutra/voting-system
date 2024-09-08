package personal.dto;

import personal.model.IHasFullName;

/**
 * An abstract class containing the firstname and lastname fields
 * that are frequently needed in DTO subclasses.
 *
 * @author Michail E. Koutrakis
 */
public abstract class NamesDTO implements IHasFullName {
    private String firstname;
    private String lastname;

    public NamesDTO() {}

    public NamesDTO(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    @Override
    public String getFirstname() {
        return firstname;
    }

    @Override
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Override
    public String getLastname() {
        return lastname;
    }

    @Override
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
