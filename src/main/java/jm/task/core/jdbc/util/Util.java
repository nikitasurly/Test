package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/testDB?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static Connection connect() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException sqlException) {
            System.err.println(
                    "****\nИСКЛЮЧЕНИЕ ПРИ ПОПЫТКЕ ПОДКЛЮЧЕНИЯ К БД\n****");
            sqlException.printStackTrace();
            throw new SQLException();
        }
    }
}
