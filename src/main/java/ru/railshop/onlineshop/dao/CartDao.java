package ru.railshop.onlineshop.dao;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.railshop.onlineshop.entity.*;
import ru.railshop.onlineshop.util.HibernateUtil;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

@Slf4j
public class CartDao {
    private static final CartDao INSTANCE = new CartDao();

    ///////////
    private static SessionFactory sessionFactory;

    public static void initializeSessionFactory() {
        sessionFactory = HibernateUtil
                .configureWithAnnotatedClasses(CartItem.class, Product.class, Cart.class,
                        User.class, UserProduct.class);
    }

    static {
        initializeSessionFactory();
    }
    ///////////

    public static CartDao getInstance() {
        return INSTANCE;
    }

    private CartDao() {
    }

    public void addProductToCart(User user, Product product, int quantity) throws Exception {
        Transaction transaction = null;
        Date currentTimestamp = Timestamp.valueOf(LocalDateTime.now());
        Session session = null;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            if (user.getCarts() == null) {
                user.setCarts(new ArrayList<>());
            }

            Cart cart = findOrCreateCartForUser(user, session, currentTimestamp);
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);

            session.save(cartItem);

            UserProduct userProduct = new UserProduct();
            userProduct.setUser(user);
            userProduct.setProduct(product);

            session.save(userProduct);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new Exception("Error adding product to cart", e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    private Cart findOrCreateCartForUser(User user, Session session, Date timestamp) {
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setCreatedAt(timestamp);
        session.save(cart);
        user.getCarts().add(cart);
        return cart;
    }
}