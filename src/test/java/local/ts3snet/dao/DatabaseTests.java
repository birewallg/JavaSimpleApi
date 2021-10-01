package local.ts3snet.dao;

import local.ts3snet.entity.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.security.auth.login.AccountNotFoundException;

import static org.junit.jupiter.api.Assertions.*;


class DatabaseTests {

    static UserDAO repo;
    static User user = null;

    @BeforeAll
    static void setUp() throws Exception {
        repo = new UserDAO();
        user = new User();
        user.setLogin("test");
        user.setName("testName");
        repo.create(user);
    }

    @Test
    void readTest() throws AccountNotFoundException {
        User local = repo.read("test");
        assertEquals(local.getLogin(), user.getLogin());
    }

    @Test
    void updateTest() throws AccountNotFoundException {
        User local = repo.read("test");
        local.setName("testUpdate");
        repo.update(local);
        local = repo.read("test");

        assertEquals("testUpdate", local.getName());
    }

    @Test
    void deleteTest() throws AccountNotFoundException {
        repo.delete(user);
        assertThrows(AccountNotFoundException.class, () -> repo.read("test"));
    }
}