package local.ts3snet.api;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class PresentationApiHttpServletTest {
    /**
     * Get all users test
     * BODY contain list of users
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
        System.out.println(stringWriter);

        printWriter.flush();
        assertTrue(stringWriter.toString().length() > 2);
    }

    /**
     * Select one user test
     * BODY contain user data
     */
    @Test
    void doGet_GetOneUser() throws Exception {
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getPathInfo()).thenReturn("/notuser");

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        when(response.getWriter()).thenReturn(printWriter);

        new PresentationApiHttpServlet().doGet(request, response);
        System.out.println(stringWriter);

        printWriter.flush();
        assertFalse(stringWriter.toString().length() > 2);
    }
}