package local.ts3snet.dao;

import local.ts3snet.entity.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DatabaseTests {

    UserDAO repo;
    User user = null;

    @Before
    public void setUp() throws Exception {
        repo = new UserDAO();
        user = new User();
        user.setLogin("test");
        user.setName("testName");

        repo.create(user);
    }

    @Test
    public void readTest() {

        User local = repo.read("test");
        assertEquals(local.getLogin(), user.getLogin());
    }

    @Test
    public void updateTest() {
        User local = repo.read("test");
        local.setName("testUpdate");
        repo.update(local);
        local = repo.read("test");

        assertEquals("testUpdate", local.getName());
    }

    @Test
    public void deleteTest() {
        repo.delete(user);
        User local = repo.read("test");
        boolean b = (local.getId() == -1);
        assertTrue(b);
    }
}