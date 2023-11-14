package ru.railshop.onlineshop.repository;

import com.querydsl.jpa.impl.JPAQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.railshop.onlineshop.entity.*;
import ru.railshop.onlineshop.exception.DaoException;
import ru.railshop.onlineshop.util.HibernateUtil;

import java.util.Optional;

public class UserRepository extends BaseRepository<Long, User> {

    private static final QUser qUser = QUser.user;

    private static final SessionFactory sessionFactory = initializeSessionFactory();
    private static final UserRepository INSTANCE = new UserRepository(sessionFactory);

    public UserRepository(SessionFactory sessionFactory) {
        super(sessionFactory, User.class);
    }

    public static SessionFactory initializeSessionFactory() {
        return HibernateUtil
                .configureWithAnnotatedClasses(User.class, Cart.class, CartItem.class,
                        Product.class, UserProduct.class);
    }


    public static UserRepository getInstance() {
        return INSTANCE;
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

}