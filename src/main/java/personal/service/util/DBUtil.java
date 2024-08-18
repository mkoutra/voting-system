package personal.service.util;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DBUtil {
    private final static BasicDataSource ds = new BasicDataSource();
    static {
        ds.setUrl("jdbc:mysql://127.0.0.1:3306/votingDB?serverTimeZone=UTC");
        ds.setUsername("votingUser");
//        ds.setPassword();
        ds.setInitialSize(10);
        ds.setMaxTotal(50);
        ds.setMinIdle(5);
        ds.setMaxIdle(10);
    }

    private DBUtil() {}

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
