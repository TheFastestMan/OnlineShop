package ru.railshop.onlineshop.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.railshop.onlineshop.entity.*;
import ru.railshop.onlineshop.util.HibernateUtil;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CartDao {
    private static final CartDao INSTANCE = new CartDao();

    private static SessionFactory sessionFactory;

    public static void initializeSessionFactory() {
        sessionFactory = HibernateUtil
                .configureWithAnnotatedClasses(CartItem.class, Product.class, Cart.class,
                        User.class, UserProduct.class);
    }

    static {
        initializeSessionFactory();
    }

    public static CartDao getInstance() {
        return INSTANCE;
    }

    private CartDao() {
    }

    public void addProductToCart(User user, Product product, int quantity) throws Exception {
        if (product.getQuantity() <= 0 || quantity <= 0) {
            throw new Exception("The product is out of stock or the requested quantity is not valid.");
        }

        Transaction transaction = null;
        Date currentTimestamp = Timestamp.valueOf(LocalDateTime.now());
        Session session = null;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            if (product.getProductId() != null) {
                product = session.get(Product.class, product.getProductId());
            } else {
                session.save(product);
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

//    private Cart findOrCreateCartForUser(User user, Session session, Date timestamp) {
//        Cart cart = new Cart();
//        cart.setUser(user);
//        cart.setCreatedAt(timestamp);
//        session.save(cart);
//        user.getCarts().add(cart);
//        return cart;
//    }

    public Cart findOrCreateCartForUser(User user, Session session, Date timestamp) {

        user = session.find(User.class, user.getId());

        List<Cart> carts = session.createQuery(
                        "select c from Cart c left join fetch c.cartItems where c.user = :user", Cart.class)
                .setParameter("user", user)
                .getResultList();

        if (carts.isEmpty()) {
            Cart cart = new Cart();
            cart.setUser(user);
            cart.setCreatedAt(timestamp);
            session.save(cart);
            user.getCarts().add(cart);
            return cart;
        } else {

            return carts.get(0);
        }
    }

}