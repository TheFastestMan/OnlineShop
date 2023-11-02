package ru.railshop.onlineshop.dao;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ru.railshop.onlineshop.entity.*;
import ru.railshop.onlineshop.exception.DaoException;
import ru.railshop.onlineshop.util.HibernateUtil;

import java.util.List;
import java.util.Optional;

@Slf4j
public class UserDao {

    private static final UserDao INSTANCE = new UserDao();
    private static SessionFactory sessionFactory;

    public static void initializeSessionFactory() {
        sessionFactory = HibernateUtil
                .configureWithAnnotatedClasses(User.class, Cart.class, CartItem.class,
                        Product.class, UserProduct.class);
    }

    static {
        initializeSessionFactory();
    }

    public static UserDao getInstance() {
        return INSTANCE;
    }

    private UserDao() {
    }


    public Optional<User> findByEmailAndPassword(String email, String password) throws Exception {
        User user = null;
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM User WHERE email = :email AND password = :password";
            Query<User> query = session.createQuery(hql, User.class);
            query.setParameter("email", email);
            query.setParameter("password", password);
            user = query.uniqueResult();
            if (user != null) {
            }
        } catch (Exception e) {
            throw new Exception("Error retrieving user by email and password", e);
        }
        return Optional.ofNullable(user);
    }

    public List<User> findAllUsers() throws Exception {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM User ", User.class).list();
        } catch (Exception e) {
            throw new Exception("Error retrieving all products", e);
        }
    }


    public User save(User user) {
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            Long id = (Long) session.save(user);
            transaction.commit();
            user.setUserId(id);
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

    public Optional<User> findByEmail(String email) throws Exception { // for validation
        User user = null;
        try (Session session = sessionFactory.openSession()) {
            String hql = "FROM User WHERE email = :email";
            Query<User> query = session.createQuery(hql, User.class);
            query.setParameter("email", email);
            user = query.uniqueResult();
        } catch (Exception e) {
            throw new Exception("Error retrieving user by email", e);
        }
        return Optional.ofNullable(user);
    }

}