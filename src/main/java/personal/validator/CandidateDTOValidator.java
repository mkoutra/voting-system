package personal.validator;

import personal.dto.CandidateInsertDTO;
import personal.dto.CandidateUpdateDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michail E. Koutrakis
 */
public class CandidateDTOValidator {

    public CandidateDTOValidator() {}

    public List<String> validate(CandidateInsertDTO dto) {
        List<String> errors = new ArrayList<>();
        if (dto == null) {
            errors.add("Invalid Information");
            return errors;
        }

        validateFirstname(dto.getFirstname(), errors);
        validateLastname(dto.getLastname(), errors);

        return errors;
    }

    public List<String> validate(CandidateUpdateDTO dto) {
        List<String> errors = new ArrayList<>();
        if (dto == null) {
            errors.add("Invalid Information");
            return errors;
        }

        validateFirstname(dto.getFirstname(), errors);
        validateLastname(dto.getLastname(), errors);

        return errors;
    }

    private void validateFirstname(String firstname, List<String> errors) {
        if (firstname == null) {
            errors.add("First name null error.");
            return;
        }

        if (!firstname.matches("^[a-zA-Z'\\-]{2,45}$")) {
            errors.add("Invalid first name.");
        }
    }

    private void validateLastname(String lastname, List<String> errors) {
        if (lastname == null) {
            errors.add("Last name null error.");
            return;
        }

        if (!lastname.matches("^[a-zA-Z'\\-]{2,45}$")) {
            errors.add("Invalid last name.");
        }
    }
}
