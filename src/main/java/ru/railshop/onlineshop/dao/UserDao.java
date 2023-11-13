package ru.railshop.onlineshop.dao;

import com.querydsl.jpa.impl.JPAQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.railshop.onlineshop.entity.*;
import ru.railshop.onlineshop.exception.DaoException;
import ru.railshop.onlineshop.util.HibernateUtil;

import java.util.List;
import java.util.Optional;

public class UserDao {

    private static final QUser qUser = QUser.user;
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

    public Optional<User> findByEmailAndPassword(String email, String password) {
        try (Session session = sessionFactory.openSession()) {
            JPAQuery<User> query = new JPAQuery<>(session);
            User user = query.select(qUser)
                    .from(qUser)
                    .where(qUser.email.eq(email).and(qUser.password.eq(password)))
                    .fetchOne();

            return Optional.ofNullable(user);
        } catch (Exception e) {
            throw new DaoException("Error retrieving user by email and password", e);
        }
    }

    public List<User> findAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            JPAQuery<User> query = new JPAQuery<>(session);
            return query.select(qUser)
                    .from(qUser)
                    .fetch();
        } catch (Exception e) {
            throw new DaoException("Error retrieving all users", e);
        }
    }

    public User save(User user) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Long id = (Long) session.save(user);
            transaction.commit();
            user.setId(id);
            return user;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DaoException("Error saving user", e);
        }
    }

    public Optional<User> findByEmail(String email) { // for validation
        try (Session session = sessionFactory.openSession()) {
            JPAQuery<User> query = new JPAQuery<>(session);
            User user = query.select(qUser)
                    .from(qUser)
                    .where(qUser.email.eq(email))
                    .fetchOne();

            return Optional.ofNullable(user);
        } catch (Exception e) {
            throw new DaoException("Error retrieving user by email", e);
        }
    }
}