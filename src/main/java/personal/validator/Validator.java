package personal.validator;

import personal.dao.IUserDAO;
import personal.dao.UserDAOImpl;
import personal.dao.exceptions.UserDAOException;
import personal.dto.ChangePasswordDTO;
import personal.dto.UserInsertDTO;
import personal.dto.UserLoginDTO;
import personal.model.IHasFullName;
import personal.service.IUserService;
import personal.service.UserServiceImpl;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A utility class that validates the DTOs that need to be validated.
 * No authentication is performed by the following methods.
 *
 * @author Michail E. Koutrakis
 */
public class Validator {
    private static final IUserDAO userDAO = new UserDAOImpl();
    private static final IUserService userService = new UserServiceImpl(userDAO);

    private Validator() {}

    // New user insertion fields validation
    public static Map<String, String> validate(UserInsertDTO dto) {
        Map<String, String> errors = new HashMap<>();

        if (dto == null) {
            errors.put("nullError", "Null error");
            return errors;
        }

        validateUsername(dto.getUsername(), errors);
        validateEmail(dto.getEmail(), errors);
        validateFirstname(dto.getFirstname(), errors);
        validateLastname(dto.getLastname(), errors);
        validateDateOfBirth(dto.getDateOfBirth(), errors);
        validatePassword(dto.getPassword(), errors);
        validateReEnteredPassword(dto.getReEnteredPassword(), dto.getPassword(), errors);

        return errors;
    }

    // Change password DTO
    public static Map<String, String> validate(ChangePasswordDTO dto) {
        Map<String, String> errors = new HashMap<>();

        if (dto == null) {
            errors.put("nullError", "Null error");
            return errors;
        }

        validatePassword(dto.getNewPassword(), errors);
        validateReEnteredPassword(dto.getReEnteredPassword(), dto.getNewPassword(), errors);

        return errors;
    }

    // Validates firstname and lastname
    public static Map<String, String> validate(IHasFullName dto) {
        Map<String ,String> errors = new HashMap<>();

        if (dto == null) {
            errors.put("nullError", "Null error");
            return errors;
        }

        validateFirstname(dto.getFirstname(), errors);
        validateLastname(dto.getLastname(), errors);

        return errors;
    }

    // Validates input at login
    public static Map<String, String> validate(UserLoginDTO dto) {
        Map<String, String> errors = new HashMap<>();

        if (dto == null) {
            errors.put("nullError", "Null error");
            return errors;
        }

        if (dto.getUsername() == null || dto.getUsername().isEmpty()) {
            errors.put("username", "Invalid username.");
        }

        if (dto.getPassword() == null || dto.getPassword().isEmpty()) {
            errors.put("password", "Invalid password.");
        }

        return errors;
    }

    private static void validateUsername(String username, Map<String, String> errors) {
        if (username == null) {
            errors.put("username", "Username null error.");
            return;
        }
        if (!username.matches("^[a-z0-9A-Z._]{3,45}$")) {
            errors.put("username", "Invalid username.");
            return;
        }

        try {
            if (userService.userExistsByUsername(username)) {
                errors.put("username", "This username is already taken.");
            }
        } catch (UserDAOException e) {
            errors.put("username", "DAO exception.");
        }
    }

    private static void validateFirstname(String firstname, Map<String, String> errors) {
        if (firstname == null) {
            errors.put("firstname", "First name null error.");
            return;
        }

        if (!firstname.matches("^[a-zA-Z'\\-]{2,45}$")) {
            errors.put("firstname", "Invalid first name.");
        }
    }

    private static void validateLastname(String lastname, Map<String, String> errors) {
        if (lastname == null) {
            errors.put("lastname", "Last name null error.");
            return;
        }

        if (!lastname.matches("^[a-zA-Z'\\-]{2,45}$")) {
            errors.put("lastname", "Invalid last name.");
        }
    }

    private static void validateEmail(String email, Map<String, String> errors) {
        if (email == null) {
            errors.put("email", "Email null error.");
            return;
        }

        if (!email.matches("^[\\w\\-.]+@([\\w\\-]+\\.)+[\\w\\-]{2,6}$")) {
            errors.put("email", "Invalid email address.");
            return;
        }

        try {
            if (userService.userExistsByEmail(email)) {
                errors.put("email", "This email is already registered.");
            }
        } catch (UserDAOException e) {
            errors.put("email", "DAO exception.");
        }
    }

    private static void validatePassword(String password, Map<String, String> errors) {
        if (password == null) {
            errors.put("password", "Password null error.");
            return;
        }

        Pattern pattern = Pattern.compile(
                "(?=.*?[a-z])(?=.*?[A-Z])(?=.*?[0-9])(?=.*?[()#?!@$%^&*\\-])^.{8,20}$");
        Matcher matcher;
        matcher = pattern.matcher(password);
        if (!matcher.find()) {
            errors.put("password", "Invalid password.");
        }
    }

    private static void validateReEnteredPassword(String reEnteredPassword, String password, Map<String, String> errors) {
        if (reEnteredPassword == null || password == null) {
            errors.put("reEnteredPassword", "Re-Entered password null error.");
            return;
        }

        if (!reEnteredPassword.equals(password)) {
            errors.put("reEnteredPassword", "Passwords do not much.");
        }
    }

    private static void validateDateOfBirth(Date dob, Map<String, String> errors) {
        if (dob == null) {
            errors.put("dob", "Invalid date.");
            return;
        }

        Calendar minDate = Calendar.getInstance();
        Calendar currentDate = Calendar.getInstance();

        minDate.set(1900, Calendar.JANUARY, 0, 0, 0,0 );

        // Check if the dob is after 1900 and before the current date.
        if (dob.after(currentDate.getTime()) || dob.before(minDate.getTime())) {
            errors.put("dob", "Invalid date.");
        }
    }
}
