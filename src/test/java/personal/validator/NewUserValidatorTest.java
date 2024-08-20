package personal.validator;

import org.junit.jupiter.api.Test;
import personal.dto.UserInsertDTO;

import java.util.Calendar;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class NewUserValidatorTest {

    @Test
    void validate() {
        UserInsertDTO dto = new UserInsertDTO();
        Map<String, String> errors;
        Calendar testDate = Calendar.getInstance();
        testDate.set(2028, Calendar.FEBRUARY, 0, 0, 0 , 0);

        dto.setUsername("user123");
        dto.setEmail("12_3@123.sa");
        dto.setFirstname("Andres");
        dto.setLastname("Iniesta");
        dto.setDateOfBirth(testDate.getTime());
//        dto.setDateOfBirth(null);
        dto.setPassword("123aA*fdasff");
        dto.setReEnteredPassword("123aA*");

        NewUserValidator validator = new NewUserValidator();

        errors = validator.validate(dto);

        for (String key : errors.keySet()) {
            System.out.println(key +": "+ errors.get(key));
        }
        assertEquals("Invalid date.", errors.get("dob"));
        assertEquals("Passwords do not much.", errors.get("reEnteredPassword"));
    }
}