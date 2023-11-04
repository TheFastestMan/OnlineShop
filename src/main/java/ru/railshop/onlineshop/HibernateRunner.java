package ru.railshop.onlineshop;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.railshop.onlineshop.entity.*;
import ru.railshop.onlineshop.util.HibernateUtil;
import org.hibernate.query.Query;


public class HibernateRunner {
    public static void main(String[] args) {

        try (SessionFactory sessionFactory = HibernateUtil.configureWithAnnotatedClasses(CartItem.class,
                Product.class, Cart.class, User.class, UserProduct.class);
             Session session = sessionFactory.openSession()) {
            TestDataImporter.importData(sessionFactory);
            session.beginTransaction();

            Query<Cart> query = session.createQuery(
                    "SELECT c FROM Cart c LEFT JOIN FETCH c.cartItems WHERE c.cartId = :cartId",
                    Cart.class
            );
            query.setParameter("cartId", 1L);
            Cart cart = query.uniqueResult();

            cart.getCartItems().forEach(cartItem -> {
                System.out.println(cartItem);
            });

            session.getTransaction().commit();
        }
    }
}