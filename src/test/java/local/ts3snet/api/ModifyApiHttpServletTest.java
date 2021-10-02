package local.ts3snet.api;


import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import local.ts3snet.entity.User;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ModifyApiHttpServletTest {
    /**
     * Edit user metadata
     * BODY contain list of users
     */
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