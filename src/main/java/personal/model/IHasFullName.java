package personal.model;

/**
 * Implemented by classes with firstname and lastname fields.
 *
 * @author Michail E. Koutrakis
 */
public interface IHasFullName {
    void setFirstname(String firstname);
    String getFirstname();
    void setLastname(String lastname);
    String getLastname();
}
