package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;

import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String URL =
            "jdbc:mysql://localhost:3306/testDB?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DIALECT =
            "org.hibernate.dialect.MySQLDialect";

    public static Connection jdbcConnect() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException sqlException) {
            System.err.println(
                    "****\nИСКЛЮЧЕНИЕ ПРИ ПОПЫТКЕ ПОДКЛЮЧЕНИЯ К БД\n****");
            sqlException.printStackTrace();
            throw new SQLException();
        }
    }
    
    public static SessionFactory hibernateConnect() {
        try {
            Properties properties = new Properties();
            properties.setProperty(Environment.URL, URL);
            properties.setProperty(Environment.USER, USER);
            properties.setProperty(Environment.PASS, PASSWORD);
            properties.setProperty(Environment.DRIVER, DRIVER);
            properties.setProperty(Environment.DIALECT, DIALECT);
            properties.setProperty(Environment.SHOW_SQL, "true");
            properties.setProperty(Environment.FORMAT_SQL, "true");
            properties.setProperty(Environment.DEFAULT_ENTITY_MODE, "dynamic-map");
            return new Configuration().addProperties(properties)
                    .addAnnotatedClass(User.class)
                    .buildSessionFactory();
        } catch (Throwable e) {
            System.err.println("Initial SessionFactory creation failed. " + e);
            throw new ExceptionInInitializerError(e);
        }
    }
}
