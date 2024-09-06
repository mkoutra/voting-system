package personal.service.util;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Utility class for managing database connections using Apache Commons DBCP.
 */
public class DBUtil {
    private final static BasicDataSource ds = new BasicDataSource();
    static {
        // Import data from configuration file config.properties
        Properties props = ConfigFileUtil.getPropertiesInstance();
        String dbName = props.getProperty("db.name");
        String username = props.getProperty("db.user");
        String password = props.getProperty("db.password");

        ds.setUrl("jdbc:mysql://localhost:3306/" + dbName + "?serverTimeZone=UTC");
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setInitialSize(10);
        ds.setMaxTotal(50);
        ds.setMinIdle(5);
        ds.setMaxIdle(10);
    }

    private DBUtil() {}

    /**
     * Retrieve a {@link Connection} object, which can be used to interact with the database.
     * @returna a {@link Connection} object
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
