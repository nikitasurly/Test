package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static PreparedStatement preparedStatement;

    public UserDaoJDBCImpl() {}

    public void createUsersTable() {
        try (Connection connection = Util.jdbcConnect()) {
            preparedStatement = connection.prepareStatement(CREATE_TABLE);
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            System.err.println("****\nИСКЛЮЧЕНИЕ ПРИ СОЗДАНИИ ТАБЛИЦЫ\n****");
            sqlException.printStackTrace();
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.jdbcConnect()) {
            preparedStatement = connection.prepareStatement(DROP_TABLE);
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            System.err.println(
                    "****\nИСКЛЮЧЕНИЕ ПРИ УДАЛЕНИИ ТАБЛИЦЫ\n****");
            sqlException.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.jdbcConnect()) {
            preparedStatement = connection.prepareStatement(INSERT);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.printf(
                    "User с именем – %s добавлен в базу данных\n", name);
        } catch (SQLException sqlException) {
            System.err.println(
                    "****\nИСКЛЮЧЕНИЕ ПРИ ДОБАВЛЕНИИ ПОЛЬЗОВАТЕЛЯ\n****");
            sqlException.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.jdbcConnect()) {
            preparedStatement = connection.prepareStatement(DELETE);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            System.err.println(
                    "****\nИСКЛЮЧЕНИЕ ПРИ УДАЛЕНИИ ПОЛЬЗОВАТЕЛЯ\n****");
            sqlException.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> listUsers = new ArrayList<>();
        try (Connection connection = Util.jdbcConnect()) {
            preparedStatement = connection.prepareStatement(FIND_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong(1));
                user.setName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge(resultSet.getByte(4));
                listUsers.add(user);
            }
        } catch (SQLException sqlException) {
            System.err.println("****\nИСКЛЮЧЕНИЕ ПРИ ПОЛУЧЕНИИ " +
                    "ВСЕХ ПОЛЬЗОВАТЕЛЕЙ ИЗ БАЗЫ\n****");
            sqlException.printStackTrace();
        }
        return listUsers;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.jdbcConnect()) {
            preparedStatement = connection.prepareStatement(DELETE_ALL);
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            System.err.println(
                    "****\nИСКЛЮЧЕНИЕ ПРИ УДАЛЕНИИ ВСЕХ ПОЛЬЗОВАТЕЛЕЙ\n****");
            sqlException.printStackTrace();
        }
    }
}
