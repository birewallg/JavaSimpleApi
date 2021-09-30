package local.ts3snet.utils;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JDBCConnection {
    private static final Logger logger = Logger.getLogger(JDBCConnection.class.getName());

    static {
        try {
            Class.forName( DBConfig.DRIVER_NAME );
            logger.info("SQL driver loaded!");
        }
        catch ( ClassNotFoundException e ) {
            logger.log(Level.WARNING, "Driver class not found" );
        }
    }

    private JDBCConnection() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DBConfig.URL, DBConfig.USERNAME, DBConfig.PASSWORD);
    }

    public static void close(Connection connection ) throws SQLException {
        if (connection != null ) {
            connection.close();
        }
    }
}
