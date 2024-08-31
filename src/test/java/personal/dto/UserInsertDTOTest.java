package personal.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserInsertDTOTest {

    @Test
    void testConstructor() {
        UserInsertDTO insertDTO = new UserInsertDTO("gkoutra", "123@123.sa", "G", "K", null,
            "pass", "passo");

        assertNull(insertDTO.getDateOfBirth());
        System.out.println(insertDTO);
    }
}