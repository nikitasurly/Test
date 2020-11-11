package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    String CREATE_TABLE = "CREATE TABLE users" +
            "(id BIGINT UNSIGNED PRIMARY KEY NOT NULL AUTO_INCREMENT, " +
            "name VARCHAR(45) NOT NULL, " +
            "lastName VARCHAR(45) NOT NULL, " +
            "age TINYINT UNSIGNED NOT NULL)";
    String DROP_TABLE = "DROP TABLE IF EXISTS users";
    String INSERT = "INSERT INTO users (name, lastName, age) " +
            "VALUES (?, ?, ?)";
    String DELETE = "DELETE FROM users WHERE id = ?";
    String FIND_ALL = "SELECT * FROM users";
    String DELETE_ALL = "DELETE FROM users";

    void createUsersTable();

    void dropUsersTable();

    void saveUser(String name, String lastName, byte age);

    void removeUserById(long id);

    List<User> getAllUsers();

    void cleanUsersTable();
}
