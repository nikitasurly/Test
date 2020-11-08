package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Alex", "Brown", (byte) 33);
        userService.saveUser("Anna", "White", (byte) 1000);
        userService.saveUser("Nick", "Cox", (byte) 24);
        userService.saveUser("Oleg", "Ivanov", (byte) 24);
        userService.getAllUsers().forEach(System.out::println);
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
