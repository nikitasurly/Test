package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static String query;
    private static Statement statement;
    private static PreparedStatement preparedStatement;
    private static ResultSet resultSet;

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        query = "CREATE TABLE users" +
                "(id INT PRIMARY KEY NOT NULL AUTO_INCREMENT, " +
                "name VARCHAR(45) NOT NULL, " +
                "lastName VARCHAR(45) NOT NULL, " +
                "age INT NOT NULL)";
        try (Connection con = Util.connect()) {
            statement = con.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException sqlEx) {
            System.err.println("****\nИСКЛЮЧЕНИЕ ПРИ СОЗДАНИИ ТАБЛИЦЫ\n****");
            sqlEx.printStackTrace();
        }
    }

    public void dropUsersTable() {
        query = "DROP TABLE users";
        try (Connection con = Util.connect()) {
            statement = con.createStatement();
            DatabaseMetaData metaData = con.getMetaData();
            resultSet = metaData.getTables(
                    null, null,
                    "users", null);
            if (resultSet.next()) {
                while (resultSet.next()) {
                    statement.executeUpdate(query);
                }
            }
        } catch (SQLException sqlEx) {
            System.err.println(
                    "****\nИСКЛЮЧЕНИЕ ПРИ УДАЛЕНИИ ТАБЛИЦЫ\n****");
            sqlEx.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        query = "INSERT INTO users (name, lastName, age) " +
                "VALUES (?, ?, ?)";

        try (Connection con = Util.connect()) {
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.printf(
                    "User с именем – %s добавлен в базу данных\n", name);
        } catch (SQLException sqlEx) {
            System.err.println(
                    "****\nИСКЛЮЧЕНИЕ ПРИ ДОБАВЛЕНИИ ПОЛЬЗОВАТЕЛЯ\n****");
            sqlEx.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        query = "DELETE FROM users WHERE id = ?";
        try (Connection con = Util.connect()) {
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException sqlEx) {
            System.err.println(
                    "****\nИСКЛЮЧЕНИЕ ПРИ УДАЛЕНИИ ПОЛЬЗОВАТЕЛЯ\n****");
            sqlEx.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        query = "SELECT * FROM users";
        List<User> listUsers = new ArrayList<>();
        try (Connection con = Util.connect()) {
            preparedStatement = con.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(1));
                user.setName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge(resultSet.getByte(4));
                listUsers.add(user);
            }

        } catch (SQLException sqlEx) {
            System.err.println("****\nИСКЛЮЧЕНИЕ ПРИ ПОЛУЧЕНИИ " +
                    "ВСЕХ ПОЛЬЗОВАТЕЛЕЙ ИЗ БАЗЫ\n****");
            sqlEx.printStackTrace();
        }
        return listUsers;

    }

    public void cleanUsersTable() {
        query = "DELETE FROM users";
        try (Connection con = Util.connect()) {
            statement = con.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException sqlEx) {
            System.err.println(
                    "****\nИСКЛЮЧЕНИЕ ПРИ УДАЛЕНИИ ВСЕХ ПОЛЬЗОВАТЕЛЕЙ\n****");
            sqlEx.printStackTrace();
        }
    }
}
