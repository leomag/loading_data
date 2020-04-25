package service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author isaev on 2019-12-16
 * @project unloading
 */
public class DBConnection {

    private final static Logger LOG = LogManager.getLogger(DBConnection.class);

    protected static Connection connection() throws IOException {
        Connection dbConnection = null;
        Properties props = new Properties();
        try {
            props.load(ClassLoader.getSystemClassLoader().getResourceAsStream("postgresql.properties"));
            String driver = props.getProperty("jdbc.driver");
            if (driver != null) {
                Class.forName(driver);
            }
        } catch (ClassNotFoundException e) {
            LOG.error(e.getMessage());
        }
        try {
            String url = props.getProperty("jdbc.url");
            String login = props.getProperty("jdbc.login");
            String password = props.getProperty("jdbc.password");

            dbConnection = DriverManager.getConnection(url, login, password);

        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }

        return dbConnection;
    }
}
