package personal.service.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * A utility class that helps with reading the "config.properties" file in the root directory
 */
public class ConfigFileUtil {
    private static final String CONFIGURATION_FILENAME = "config.properties";

    private ConfigFileUtil() {};

    /**
     * Returns a {@link Properties} object that corresponds to the configuration file.
     * @return
     */
    public static Properties getPropertiesInstance() {
//        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String rootPath = "./";
        String configPath = Paths.get(rootPath, CONFIGURATION_FILENAME).toString();
        Properties props = new Properties();

        try {
            props.load(new FileInputStream(configPath));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }

        return props;
    }
}
