package local.ts3snet.api;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import local.ts3snet.entity.User;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class ApiHttpServletAllTests {
    /**
     * Generate Test data
     * BODY contain list of users
     */
    @Order(1)
    @Test
    void doOptions_generateTestData() throws Exception {
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getPathInfo()).thenReturn("/");

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        when(response.getWriter()).thenReturn(printWriter);

        new CreateApiHttpServlet().doOptions(request, response);

        List<User> users = new Gson().fromJson(
                String.valueOf(stringWriter),
                new TypeToken<List<User>>(){}.getType()
        );
        System.out.println(users);

        printWriter.flush();
        assertFalse(users.isEmpty());
    }

    /**
     * Create user test
     * BODY contain user data
     */
    @Order(2)
    @Test
    void doGet_CreateUser() throws Exception {
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getPathInfo()).thenReturn("/testuser");
        Map<String, String[]> params = new HashMap<>();
        params.put("name", new String[] {"newname"});
        params.put("age", new String[] {"42"});
        when(request.getParameterMap()).thenReturn(params);
        when(request.getParameter("name")).thenReturn("newname");
        when(request.getParameter("age")).thenReturn("42");

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        when(response.getWriter()).thenReturn(printWriter);

        new CreateApiHttpServlet().doGet(request, response);

        User user = new Gson().fromJson(String.valueOf(stringWriter), User.class);
        System.out.println(user);

        printWriter.flush();
        assertEquals("testuser", user.getLogin());
    }
    /**
     * Get all users test
     * BODY contain list of users
     * if database is notnull
     */
    @Order(3)
    @Test
    void doGet_GetAllUsers() throws Exception {
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getPathInfo()).thenReturn("/");

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        when(response.getWriter()).thenReturn(printWriter);

        new PresentationApiHttpServlet().doGet(request, response);

        List<User> users = new Gson().fromJson(
                String.valueOf(stringWriter),
                new TypeToken<List<User>>(){}.getType()
        );
        System.out.println(users);

        printWriter.flush();
        assertFalse(users.isEmpty());
    }

    /**
     * Select one user test
     * BODY contain user data
     */
    @Order(4)
    @Test
    void doGet_GetOneUser() throws Exception {
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getPathInfo()).thenReturn("/testuser");

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        when(response.getWriter()).thenReturn(printWriter);

        new PresentationApiHttpServlet().doGet(request, response);

        User user = new Gson().fromJson(String.valueOf(stringWriter), User.class);
        System.out.println(user);

        printWriter.flush();
        assertEquals("testuser", user.getLogin());
    }
    /**
     * Edit user metadata
     * BODY contain list of users
     */
    @Order(5)
    @Test
    void doGet_editUserMetadata() throws Exception {
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getPathInfo()).thenReturn("/testuser");
        Map<String, String[]> params = new HashMap<>();
        params.put("name", new String[] {"MODIFYname"});
        params.put("lastname", new String[] {"MODIFYlastname"});
        params.put("age", new String[] {"50"});
        when(request.getParameterMap()).thenReturn(params);
        when(request.getParameter("name")).thenReturn("MODIFYname");
        when(request.getParameter("lastname")).thenReturn("MODIFYlastname");
        when(request.getParameter("age")).thenReturn("50");

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        when(response.getWriter()).thenReturn(printWriter);

        new ModifyApiHttpServlet().doGet(request, response);

        User user = new Gson().fromJson(String.valueOf(stringWriter), User.class);
        System.out.println(user);

        printWriter.flush();
        assertEquals("MODIFYname", user.getName());
        assertEquals("MODIFYlastname", user.getLastname());
        assertEquals(50, user.getAge());
    }

    /**
     * Remove user from database
     * BODY contain user data
     */
    @Order(6)
    @Test
    void doDelete_deleteUserFromDatabase() throws Exception {
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getPathInfo()).thenReturn("/testuser");

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        when(response.getWriter()).thenReturn(printWriter);

        new ModifyApiHttpServlet().doDelete(request, response);

        User user = new Gson().fromJson(String.valueOf(stringWriter), User.class);
        System.out.println(user);

        printWriter.flush();
        assertEquals("testuser", user.getLogin());
    }
}