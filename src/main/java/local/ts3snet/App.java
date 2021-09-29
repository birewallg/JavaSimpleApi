package local.ts3snet;

import local.ts3snet.dao.UserDAO;
import local.ts3snet.entity.User;
import local.ts3snet.utils.JDBCConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

public class App {
    private Logger log = Logger.getLogger(App.class.getName());

    public static void main( String[] args ) throws SQLException {
        App app = new App();
        app.defaultTable();

        UserDAO user = new UserDAO();
        user.create(
                new User(
                        "one1",
                        "",
                        System.currentTimeMillis(),
                        42
                )
        );

    }

    private void defaultTable() {
        try(Connection connection = JDBCConnection.getConnection();
            Statement statement = connection.createStatement()) {
            String sql =  "CREATE TABLE IF NOT EXISTS  USERS " +
                    "(id INTEGER AUTO_INCREMENT, login VARCHAR(255), name VARCHAR(255), date DATE, age INTEGER, " +
                    "PRIMARY KEY ( login ))";

            statement.execute(sql);
            log.info("Created table in db...");
        } catch(Exception se) {
            se.printStackTrace();
        }
    }
}
