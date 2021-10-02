package local.ts3snet.api;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import local.ts3snet.entity.User;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PresentationApiHttpServletTest {
    /**
     * Get all users test
     * BODY contain list of users
     * if database is notnull
     */
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
}