package local.ts3snet.utils;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JDBCConnection {
    private static Logger log = Logger.getLogger(JDBCConnection.class.getName());

    static {
        try {
            Class.forName( DBConstants.DRIVER_NAME );
            log.info("SQL driver loaded!");
        }
        catch ( ClassNotFoundException e ) {
            log.log(Level.WARNING, "Driver class not found" );
        }
    }

    private JDBCConnection() {
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DBConstants.URL, DBConstants.USERNAME, DBConstants.PASSWORD);
    }

    public static void close(Connection connection ) throws SQLException {
        if (connection != null ) {
            connection.close();
        }
    }
}
