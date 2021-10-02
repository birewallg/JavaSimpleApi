package local.ts3snet.dao;

import local.ts3snet.entity.User;
import local.ts3snet.utils.JDBCConnection;

import javax.security.auth.login.AccountNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * CRUD Operations
 */
public class UserDAO implements DAO<User, String>{
    private static final String INSERT_QUERY = "INSERT INTO USERS (login, name, lastname, age) VALUES (?, ?, ?, ?)";
    private static final String SELECT_QUERY = "SELECT id, login, name, lastname, age FROM USERS WHERE login = (?)";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM USERS";
    private static final String DELETE_QUERY = "DELETE FROM USERS WHERE login = (?)";
    private static final String UPDATE_QUERY = "UPDATE USERS SET name = (?), lastname = (?), age = (?) WHERE login = (?)";

    private final Logger logger = Logger.getLogger(UserDAO.class.getName());

    private final Connection connection;
    public UserDAO() throws SQLException {
        this.connection = JDBCConnection.getConnection();
    }

    @Override
    public boolean create(User user) throws AccountNotFoundException {
        // check user in database throw AccountNotFoundException
        try {
            read(user.getLogin());
        } catch (AccountNotFoundException e) {
            try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY)) {
                statement.setString(1, user.getLogin());
                statement.setString(2, user.getName());
                statement.setString(3, user.getLastname());
                statement.setInt(4, user.getAge());
                int id = statement.executeUpdate();

                return id > 0;
            } catch (SQLException sqlException) {
                logger.log(Level.WARNING, sqlException.getMessage());
                throw new IllegalArgumentException();
            }
        }
        throw new AccountNotFoundException();
    }

    @Override
    public User read(String login) throws IllegalArgumentException, AccountNotFoundException {
        final User result = new User();
        result.setId(-1);

        try (PreparedStatement statement = connection.prepareStatement(SELECT_QUERY)) {
            statement.setString(1, login);
            final ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                result.setId(Integer.parseInt(rs.getString("id")));
                result.setLogin(login);
                result.setName(rs.getString("name"));
                result.setLastname(rs.getString("lastname"));
                result.setAge(Integer.parseInt(rs.getString("age")));
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
            throw new IllegalArgumentException();
        }

        if (result.getId() == -1)
            throw new AccountNotFoundException();
        return result;
    }

    @Override
    public List<User> readAll() {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_QUERY)) {
            final ResultSet rs = statement.executeQuery();
            List<User> allUsers = new ArrayList<>();
            while (rs.next()) {
                User user = new User();
                user.setId(Integer.parseInt(rs.getString("id")));
                user.setLogin(rs.getString("login"));
                user.setName(rs.getString("name"));
                user.setLastname(rs.getString("lastname"));
                user.setAge(Integer.parseInt(rs.getString("age")));
                allUsers.add(user);
            }
            return allUsers;
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public boolean update(User user) throws AccountNotFoundException {
        // check user in database throw AccountNotFoundException
        read(user.getLogin());

        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getLastname());
            statement.setInt(3, user.getAge());
            statement.setString(4, user.getLogin());
            int id = statement.executeUpdate();
            return id > 0;
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
            throw new IllegalArgumentException();
        }
    }

    @Override
    public boolean delete(User user) throws AccountNotFoundException {
        // check user in database throw AccountNotFoundException
        read(user.getLogin());

        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            //statement.setInt(1, user.getId())
            statement.setString(1, user.getLogin());
            int id = statement.executeUpdate();
            return id > 0;
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
            throw new IllegalArgumentException();
        }
    }
}
