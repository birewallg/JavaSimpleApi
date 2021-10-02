package local.ts3snet.api;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import local.ts3snet.entity.User;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CreateApiHttpServletTest {
    /**
     * Generate Test data
     * BODY contain list of users
     */
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
}