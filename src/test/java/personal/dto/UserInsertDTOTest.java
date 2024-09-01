package personal.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserInsertDTOTest {

    @Test
    void testConstructor() {
        UserInsertDTO insertDTO = new UserInsertDTO("username", "123@123.sa", "fff",
                "lll", null, "pass", "passo");

        assertNull(insertDTO.getDateOfBirth());
    }
}