package local.ts3snet.api;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import local.ts3snet.dao.UserDAO;
import local.ts3snet.entity.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import javax.security.auth.login.AccountNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TasksHttpServletTests {
    private UserDAO repo;
    private User user = null;
    @BeforeEach
    void init() throws SQLException, AccountNotFoundException {
        repo = new UserDAO();
        user = new User();
        user.setLogin("test");
        user.setLastname("test_en");
        user.setName("test_en");
        user.setAge(18);
        repo.create(user);
    }
    @AfterEach
    void destroy() throws AccountNotFoundException {
        repo.delete(user);
    }
    /**
     * Test 1 | get number users where user.lastname is 'en'
     * BODY contain number
     */
    @Test
    void doGet_Task_1() throws Exception {
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getPathInfo()).thenReturn("/1");

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        when(response.getWriter()).thenReturn(printWriter);

        new TasksApiHttpServlet().doGet(request, response);

        Long number = new Gson().fromJson(String.valueOf(stringWriter), Long.class);
        System.out.println(number);

        printWriter.flush();
        assertTrue(number > 0);
    }

    /**
     * Test 2 | get all user lastnames where user.age < 20
     * BODY contain list of user lastnames
     */
    @Test
    void doGet_Task_2() throws Exception {
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getPathInfo()).thenReturn("/2");

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        when(response.getWriter()).thenReturn(printWriter);

        new TasksApiHttpServlet().doGet(request, response);

        List<String> users = new Gson().fromJson(
                String.valueOf(stringWriter),
                new TypeToken<List<String>>(){}.getType()
        );
        System.out.println(users);

        printWriter.flush();
        assertTrue(users.size() > 0);
    }
}