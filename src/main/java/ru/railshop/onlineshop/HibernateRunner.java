package ru.railshop.onlineshop;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.railshop.onlineshop.entity.*;
import ru.railshop.onlineshop.util.HibernateUtil;


import javax.persistence.LockModeType;


public class HibernateRunner {
    public static void main(String[] args) {

//        try (SessionFactory sessionFactory = HibernateUtil.configureWithAnnotatedClasses(CartItem.class,
//                Product.class, Cart.class, User.class, UserProduct.class);
//             Session session = sessionFactory.openSession()) {
//
//            TestDataImporter.importData(sessionFactory);
//            session.beginTransaction();
//
//            var product = session.find(Product.class, 1L, LockModeType.OPTIMISTIC);
//            product.setQuantity(product.getQuantity() + 10);
//
//            session.getTransaction().commit();
//
//        }

        User user = null;

        try (SessionFactory sessionFactory = HibernateUtil.configureWithAnnotatedClasses(CartItem.class,
                Product.class, Cart.class, User.class, UserProduct.class)) {
            Session session = sessionFactory.openSession();
            session.beginTransaction();

            user = session.find(User.class, 1L);
            var user1 = session.find(User.class, 1L);
            System.out.println(user.getCarts());
            session.getTransaction().commit();
        }
        try (SessionFactory sessionFactory = HibernateUtil.configureWithAnnotatedClasses(CartItem.class,
                Product.class, Cart.class, User.class, UserProduct.class)) {
            Session session = sessionFactory.openSession();
            session.beginTransaction();

            var user2 = session.find(User.class, 1L);
            System.out.println(user2.getCarts());
            session.getTransaction().commit();
        }

    }

}