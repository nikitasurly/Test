package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import java.util.List;

@SuppressWarnings("ALL")
public class UserDaoHibernateImpl implements UserDao {
    private static NativeQuery query;

    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try (Session session = Util.hibernateConnect().openSession()) {
            session.beginTransaction();
            query = session.createNativeQuery(CREATE_TABLE);
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception exception) {
            System.err.println("****\nИСКЛЮЧЕНИЕ ПРИ СОЗДАНИИ ТАБЛИЦЫ\n****");
            exception.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = Util.hibernateConnect().openSession()) {
            session.beginTransaction();
            query = session.createNativeQuery(DROP_TABLE);
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception exception) {
            System.err.println(
                    "****\nИСКЛЮЧЕНИЕ ПРИ УДАЛЕНИИ ТАБЛИЦЫ\n****");
            exception.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.hibernateConnect().openSession()) {
            User user = new User(name, lastName, age);
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            System.out.printf(
                    "User с именем – %s добавлен в базу данных\n", name);
        } catch (Exception exception) {
            System.err.println(
                    "****\nИСКЛЮЧЕНИЕ ПРИ ДОБАВЛЕНИИ ПОЛЬЗОВАТЕЛЯ\n****");
            exception.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.hibernateConnect().openSession()) {
            session.beginTransaction();
            User user = new User();
            user = session.load(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
        } catch (Exception exception) {
            System.err.println(
                    "****\nИСКЛЮЧЕНИЕ ПРИ УДАЛЕНИИ ПОЛЬЗОВАТЕЛЯ\n****");
            exception.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = Util.hibernateConnect().openSession()) {
            List<User> users = session.createQuery("FROM User").list();
            users.forEach(user -> new User());
            return users;
        } catch (Exception exception) {
            System.err.println("****\nИСКЛЮЧЕНИЕ ПРИ ПОЛУЧЕНИИ " +
                    "ВСЕХ ПОЛЬЗОВАТЕЛЕЙ ИЗ БАЗЫ\n****");
            exception.printStackTrace();
            throw exception;
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = Util.hibernateConnect().openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("DELETE FROM User");
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception exception) {
            System.err.println(
                    "****\nИСКЛЮЧЕНИЕ ПРИ УДАЛЕНИИ ВСЕХ ПОЛЬЗОВАТЕЛЕЙ\n****");
            exception.printStackTrace();
        }
    }
}
