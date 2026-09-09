package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    private Util util = new Util();
    static long count = 1;
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = util.setting();
             PreparedStatement statement = connection.prepareStatement(
                     "CREATE TABLE IF NOT EXISTS Users (id BIGINT PRIMARY KEY, name VARCHAR (100)," +
                            "lastName VARCHAR(100), age TINYINT)"))
        {
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try (Connection connection = util.setting();
             PreparedStatement statement = connection.prepareStatement(
                     "DROP TABLE IF EXISTS Users"))
        {
            statement.executeUpdate();
            count = 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = util.setting();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO Users(id, name, lastName, age) VALUES(?, ?, ?, ?)"))
        {
            statement.setLong(1, count++);
            statement.setString(2, name);
            statement.setString(3, lastName);
            statement.setByte(4, age);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = util.setting();
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE FROM Users WHERE id = ?"))
        {
            statement.setLong(1, id);
            statement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = util.setting();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM Users");
             ResultSet resultSet = statement.executeQuery())
        {
            while (resultSet.next()) {
                users.add(new User(resultSet.getString("name"),
                        resultSet.getString("lastName"),
                        resultSet.getByte("age")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public void cleanUsersTable() {
        try (Connection connection = util.setting();
             PreparedStatement statement = connection.prepareStatement(
                     "TRUNCATE TABLE Users"))
        {
            statement.executeUpdate();
            count = 1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
