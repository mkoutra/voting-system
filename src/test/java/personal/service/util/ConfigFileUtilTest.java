package personal.service.util;

import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class ConfigFileUtilTest {

    @Test
    void readValuesFromConfig() {
        Properties props = ConfigFileUtil.getPropertiesInstance();
        String password = props.getProperty("db.password");
        String username = props.getProperty("db.user");
        assertFalse(password.isEmpty());
        assertFalse(username.isEmpty());
    }
}