package local.ts3snet.api;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import local.ts3snet.dao.UserDAO;
import local.ts3snet.entity.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@WebServlet(
        name = "TasksApiHttpServlet",
        urlPatterns = {"/api/task/*"})
public class TasksApiHttpServlet extends HttpServlet {
    private final Logger logger = Logger.getLogger(TasksApiHttpServlet.class.getName());

    @Override
    public void init(ServletConfig config) throws ServletException {
        logger.info("Servlet init");
        super.init(config);
    }

    /**
     * Show users
     * if (age > 20)
     * GET /api/task/1
     * if (last name contains "en")
     * GET /api/task/2
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String path = req.getPathInfo();
        if (path == null || path.equals("/")) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        String[] paths = req.getPathInfo().split("/");
        if (paths.length != 2) {
            logger.log(Level.WARNING, "SC_BAD_REQUEST");
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        try {

            UserDAO repository = new UserDAO();
            List<User> list = repository.readAll();
            List<User> result = new LinkedList<>();
            switch (paths[1]) {
                // test 1 | get number users where user.lastname is 'en'
                case "1": {
                    long count = list.stream()
                            .map(User::getLastname)
                            .filter(lastname -> lastname.endsWith("en"))
                            .count();
                    resp.getWriter().println(JsonTranslate.toJson(count));
                    break;
                }
                // test 2 | get all user lastnames where user.age < 20
                case "2": {

                    List<String> lastnames = list.stream()
                            .filter(user -> user.getAge() < 20)
                            .map(User::getLastname)
                            .collect(Collectors.toList());

                    resp.getWriter().println(JsonTranslate.toJson(lastnames));
                    break;
                }
                default: {
                    logger.log(Level.WARNING, "SC_BAD_REQUEST");
                    resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
