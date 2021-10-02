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
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(
        name = "ModifyApiHttpServlet",
        urlPatterns = {"/api/edit/*"})
public class ModifyApiHttpServlet extends HttpServlet {
    private final Logger logger = Logger.getLogger(ModifyApiHttpServlet.class.getName());

    @Override
    public void init(ServletConfig config) throws ServletException {
        logger.info("Servlet init");
        super.init(config);
    }

    /**
     * Edit user metadata
     * GET/api/edit/{login}?name=string&date=long&age=int
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
        try {
            // get Entity
            UserDAO repository = new UserDAO();
            String login = paths[1];
            User user = repository.read(login);
            if (user.getId() == -1) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                logger.log(Level.WARNING, "SC_NOT_FOUND");
                return;
            }
            // set new data
            Set<String> param = req.getParameterMap().keySet();
            for(String k : param) {
                String v = req.getParameter(k);
                if (!v.equals("")) {
                    if (!user.build(k, v)) {
                        logger.log(Level.WARNING, "SC_CONFLICT");
                        resp.sendError(HttpServletResponse.SC_CONFLICT);
                        return;
                    }
                }
            }
            // update Entity
            if (repository.update(user))
                resp.getWriter().println(JsonRetranslator.toJson(repository.read(login)));
            else throw new SQLException();
        } catch (AccountNotFoundException e){
            logger.log(Level.WARNING, e.getMessage());
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Delete user from database
     * DELETE/api/edit/{login}
     */
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
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
        try {
            String login = paths[1];
            UserDAO repository = new UserDAO();
            User user = repository.read(login);
            if (repository.delete(user))
                resp.getWriter().println(JsonRetranslator.toJson(user));
            else throw new SQLException();
        } catch (AccountNotFoundException e){
            logger.log(Level.WARNING, e.getMessage());
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}