package personal.dto;

/**
 * Data Transfer Object (DTO) used for transferring user data in a read-only format
 * from the service layer to the view layer.
 *
 * @author Michail E. Koutrakis
 */
public class UserReadOnlyDTO extends NamesDTO {
    private String username;

    public UserReadOnlyDTO() {}

    public UserReadOnlyDTO(String username, String firstname, String lastname) {
        super(firstname, lastname);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
