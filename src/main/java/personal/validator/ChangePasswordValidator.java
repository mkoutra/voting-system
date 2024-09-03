package personal.validator;

import personal.dto.ChangePasswordDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChangePasswordValidator {
    private List<String> errors = new ArrayList<>();

    public ChangePasswordValidator() {}

    public List<String> validate(ChangePasswordDTO dto) {
        if (dto == null || dto.getCurrentPassword() == null || dto.getNewPassword() == null
                || dto.getReEnteredPassword().isEmpty()) {
            errors.add("Form has not completed yet.");
            return errors;
        }

        validatePassword(dto.getNewPassword(), errors);

        if (errors.isEmpty()) {
            validateReEnteredPassword(dto.getReEnteredPassword(), dto.getNewPassword(), errors);
        }
        return errors;
    }

    private void validatePassword(String password, List<String> errors) {
        if (password == null) {
            errors.add("Password null error.");
            return;
        }

        Pattern pattern = Pattern.compile(
                "(?=.*?[a-z])(?=.*?[A-Z])(?=.*?[0-9])(?=.*?[()#?!@$%^&*\\-])^.{8,20}$");
        Matcher matcher;
        matcher = pattern.matcher(password);
        if (!matcher.find()) {
            errors.add("Invalid password.");
        }
    }

    private void validateReEnteredPassword(String reEnteredPassword, String password, List<String> errors) {
        if (reEnteredPassword == null || password == null) {
            errors.add("Re-Entered password null error.");
            return;
        }

        if (!reEnteredPassword.equals(password)) {
            errors.add("Passwords do not much.");
        }
    }
}
