package ru.railshop.onlineshop;

import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.railshop.onlineshop.entity.*;
import ru.railshop.onlineshop.util.HibernateUtil;
import org.hibernate.query.Query;

import javax.persistence.LockModeType;


public class HibernateRunner {
    public static void main(String[] args) {

        try (SessionFactory sessionFactory = HibernateUtil.configureWithAnnotatedClasses(CartItem.class,
                Product.class, Cart.class, User.class, UserProduct.class);
             Session session = sessionFactory.openSession();
             Session session1 = sessionFactory.openSession()) {

            TestDataImporter.importData(sessionFactory);
            session.beginTransaction();
            session1.beginTransaction();

            var cart = session.find(Cart.class, 1L, LockModeType.OPTIMISTIC);
            cart.setUser(cart.getUser() );

            var cart2 = session.find(Cart.class, 1L, LockModeType.OPTIMISTIC);
            cart2.setUser(cart2.getUser());

            session.getTransaction().commit();
            session1.getTransaction().commit();
        }


    }

}