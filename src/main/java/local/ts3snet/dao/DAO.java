package local.ts3snet.dao;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

public interface DAO<Entity, Key> {
    boolean create(Entity model) throws AccountNotFoundException;
    Entity read(Key key) throws ClassNotFoundException, AccountNotFoundException;
    List<Entity> readAll();
    boolean update(Entity model) throws AccountNotFoundException;
    boolean delete(Entity model) throws AccountNotFoundException;
}