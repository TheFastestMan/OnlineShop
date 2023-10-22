package ru.railshop.onlineshop.dao;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.railshop.onlineshop.entity.*;
import ru.railshop.onlineshop.exception.DaoException;
import ru.railshop.onlineshop.util.ConfigurationUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public class UserDao implements Dao<Long, User> {

    private static final UserDao INSTANCE = new UserDao();

    ///////////
    private static SessionFactory sessionFactory;

    public static void initializeSessionFactory() {
        sessionFactory = ConfigurationUtil
                .configureWithAnnotatedClasses(User.class, Product.class);
    }

    static {
        initializeSessionFactory();
    }

    ///////////

    public static UserDao getInstance() {
        return INSTANCE;
    }

    private UserDao() {
    }


    @Override
    public boolean update(User user) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(user);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            log.error("Error updating user", e);
            throw new DaoException("Error updating user", e);
        }
    }


    @Override
    public List<User> findAll() {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("FROM User", User.class);
            return query.list();
        } catch (Exception e) {
            log.error("Error finding all users", e);
            throw new DaoException("Error finding all users", e);
        }
    }


    @Override
    public Optional<User> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            User user = session.get(User.class, id);
            return Optional.ofNullable(user);
        } catch (Exception e) {
            log.error("Error finding user by ID", e);
            throw new DaoException("Error finding user by ID", e);
        }
    }

    @Override
    public User save(User user) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            Long id = (Long) session.save(user);
            transaction.commit();
            user.setId(id);
            log.info("User with name {} saved", user.getUsername());
            return user;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            log.error("Error saving user", e);
            throw new DaoException("Error saving user", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    /*
    * prepareStatement.setObject(4, user.getRole().name(), Types.OTHER);
    prepareStatement.setObject(5, user.getGender().name(), Types.OTHER);

    * */
    @Override
    public boolean delete(Long id) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            User user = session.get(User.class, id);
            if (user != null) {
                session.delete(user);
                transaction.commit();
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            log.error("Error deleting user", e);
            throw new DaoException("Error deleting user", e);
        }
    }

    public Optional<User> findByEmailAndPassword(String email, String password) {
        User user = null;
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM User WHERE email = :email AND password = :password";
            Query<User> query = session.createQuery(hql, User.class);
            query.setParameter("email", email);
            query.setParameter("password", password);
            user = query.uniqueResult();
            if (user != null) {
                log.info("Login user with role: {} and username: {}", user.getRole(), user.getUsername());
            }
        } catch (Exception e) {
            log.error("Error retrieving user by email and password", e);
            throw new DaoException("Error retrieving user by email and password", e);
        }
        return Optional.ofNullable(user);
    }


    public List<Product> findAllProductsByUserId(Long userId) {
        try (Session session = sessionFactory.openSession()) {
            User user = session.get(User.class, userId);
            if (user != null) {
                return user.getProductList();
            } else {
                return new ArrayList<>(); // Return an empty list if the user is not found
            }
        } catch (Exception e) {
            log.error("Error retrieving products for user with ID: " + userId, e);
            throw new DaoException("Error retrieving products for user with ID: " + userId, e);
        }
    }


}