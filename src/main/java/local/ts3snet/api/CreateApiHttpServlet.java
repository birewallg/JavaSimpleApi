package local.ts3snet.api;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import local.ts3snet.dao.UserDAO;
import local.ts3snet.entity.User;
import local.ts3snet.utils.JDBCConnection;

import javax.security.auth.login.AccountNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(
        name = "CreateApiHttpServlet",
        urlPatterns = {"/api/new/*"})
public class CreateApiHttpServlet extends HttpServlet {
    private final Logger logger = Logger.getLogger(CreateApiHttpServlet.class.getName());

    @Override
    public void init(ServletConfig config) throws ServletException {
        logger.info("Servlet init");
        super.init(config);
    }

    /**
     * Add user
     * GET/api/new/{login}?name=string&date=long&age=int
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = req.getPathInfo();
        if (path == null || path.equals("/")) {
            logger.log(Level.WARNING, "SC_BAD_REQUEST");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        String[] paths = req.getPathInfo().split("/");
        if (paths.length != 2) {
            logger.log(Level.WARNING, "SC_BAD_REQUEST");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        String login = paths[1];

        try {
            UserDAO repository = new UserDAO();
            if (repository.read(login) != null)
                throw new IllegalArgumentException();
        } catch (AccountNotFoundException e) {
            logger.info("User not found. Create new user ..");
        } catch (IllegalArgumentException e) {
            logger.info("This User already exists ..");
            resp.sendError(HttpServletResponse.SC_CONFLICT);
            return;
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }
        try {
            // get User
            UserDAO repository = new UserDAO();
            User user = new User();
            // set data
            user.setLogin(login);
            Set<String> param = req.getParameterMap().keySet();
            for (String k : param) {
                String v = req.getParameter(k);
                if (!v.equals(""))
                    if (!user.build(k, v)) {
                        logger.log(Level.WARNING, "SC_CONFLICT");
                        resp.sendError(HttpServletResponse.SC_CONFLICT);
                        return;
                    }
            }
            // create User
            repository.create(user);
            resp.getWriter().println(JsonTranslate.toJson(repository.read(login)));
        } catch (AccountNotFoundException e) {
            logger.log(Level.WARNING, e.getMessage());
            resp.sendError(HttpServletResponse.SC_CONFLICT);
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Create database
     * HEAD/app/api/new
     */
    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // clear table
        try(Connection connection = JDBCConnection.getConnection();
            Statement statement = connection.createStatement()) {
            String sql =  "TRUNCATE TABLE USERS";
            statement.execute(sql);
        } catch(Exception se) {
            logger.log(Level.WARNING, se.getMessage());
        }
        // create table
        try(Connection connection = JDBCConnection.getConnection();
            Statement statement = connection.createStatement()) {
            String sql =  "CREATE TABLE IF NOT EXISTS  USERS " +
                    "(id INTEGER AUTO_INCREMENT, login VARCHAR(255), name VARCHAR(255), lastname VARCHAR(255), age INTEGER, " +
                    "PRIMARY KEY ( login ))";
            statement.execute(sql);
        } catch(Exception se) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            logger.log(Level.WARNING, se.getMessage());
            return;
        }
        // add users
        try {
            UserDAO repository = new UserDAO();
            for (int i = 0; i < 40; i++) {
                int r = new Random().nextInt(10) + 15;
                repository.create(new User("name" + i, "" + r, "lastname_" + ((r > 20) ? "ov" : "en"), r));
            }
            resp.getWriter().println(JsonTranslate.toJson(repository.readAll()));
        } catch (AccountNotFoundException e) {
            resp.sendError(HttpServletResponse.SC_CONFLICT);
        } catch (SQLException exception) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
