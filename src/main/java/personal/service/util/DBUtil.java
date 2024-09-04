package personal.service.util;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Utility class for managing database connections using Apache Commons DBCP.
 */
public class DBUtil {
    private final static BasicDataSource ds = new BasicDataSource();
    static {
        ds.setUrl("jdbc:mysql://localhost:3306/votingDB?serverTimeZone=UTC");
        ds.setUsername("votingUser");
        ds.setPassword("voting");
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
