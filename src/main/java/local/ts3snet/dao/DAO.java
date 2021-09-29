package local.ts3snet.dao;

import java.util.List;

public interface DAO<Entity, Key> {
    boolean create(Entity model);
    Entity read(Key key);
    List<Entity> readAll();
    boolean update(Entity model);
    boolean delete(Entity model);
}