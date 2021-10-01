package local.ts3snet.api;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import local.ts3snet.dao.UserDAO;
import local.ts3snet.entity.User;

import javax.security.auth.login.AccountNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(
        name = "PresentationApiHttpServlet",
        urlPatterns = {"/api/users/*"})
public class PresentationApiHttpServlet extends HttpServlet {
    private final Logger logger = Logger.getLogger(PresentationApiHttpServlet.class.getName());

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        logger.info("Servlet init");
    }

    /**
     * Show users
     * GET/app/api/users
     * GET/app/api/users/{login}
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // get all users
        String path = req.getPathInfo();
        if (path == null || path.equals("/")) {
            try {
                UserDAO repository = new UserDAO();
                resp.getWriter().println(repository.readAll().toString());
                return;
            } catch (SQLException e) {
                logger.log(Level.WARNING, e.getMessage());
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return;
            }
        }
        // get user by login
        String[] paths = req.getPathInfo().split("/");
        if (paths.length != 2) {
            logger.log(Level.WARNING, "SC_BAD_REQUEST");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        try {
            UserDAO repository = new UserDAO();
            User user = repository.read(paths[1]);
            resp.getWriter().println(user);
        } catch (AccountNotFoundException e) {
            logger.log(Level.WARNING, "SC_NOT_FOUND");
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
