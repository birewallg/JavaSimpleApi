package local.ts3snet.dao;

import local.ts3snet.entity.User;
import local.ts3snet.utils.JDBCConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAO implements DAO<User, String>{
    private static final String INSERT_QUERY = "INSERT INTO USERS (login, name, date, age) VALUES (?, ?, ?, ?)";
    private static final String SELECT_QUERY = "SELECT id, login, name, date, age FROM USERS WHERE login = (?)";
    private static final String SELECT_ALL_QUERY = "SELECT * FROM USERS";
    private static final String DELETE_QUERY = "DELETE FROM USERS WHERE id = (?) AND login = (?)";
    private static final String UPDATE_QUERY = "UPDATE USERS SET name = (?), date = (?), age = (?) WHERE login = (?)";

    Logger logger = Logger.getLogger(UserDAO.class.getName());

    private final Connection connection;
    public UserDAO() throws SQLException {
        this.connection = JDBCConnection.getConnection();
    }

    @Override
    public boolean create(User user) {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_QUERY)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getName());
            statement.setDate(3, user.getData());
            statement.setInt(4, user.getAge());
            int id = statement.executeUpdate();

            return id > 0;
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
            return false;
        }
    }

    @Override
    public User read(String login) {
        final User result = new User();
        result.setId(-1);

        try (PreparedStatement statement = connection.prepareStatement(SELECT_QUERY)) {
            statement.setString(1, login);
            final ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                result.setId(Integer.parseInt(rs.getString("id")));
                result.setLogin(login);
                result.setName(rs.getString("name"));
                result.setData(rs.getDate("date"));
                result.setAge(Integer.parseInt(rs.getString("age")));
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
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
                user.setData(rs.getDate("date"));
                user.setAge(Integer.parseInt(rs.getString("age")));
                allUsers.add(user);
            }
            return allUsers;
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        return Collections.emptyList();
    }

    @Override
    public boolean update(User user) {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, user.getName());
            statement.setDate(2, user.getData());
            statement.setInt(3, user.getAge());
            statement.setString(4, user.getLogin());
            return statement.executeQuery().next();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        return false;
    }

    @Override
    public boolean delete(User user) {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setInt(1, user.getId());
            statement.setString(2, user.getLogin());
            return statement.executeQuery().next();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
        return false;
    }
}
