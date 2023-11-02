package ru.railshop.onlineshop.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import ru.railshop.onlineshop.entity.*;
import ru.railshop.onlineshop.util.HibernateUtil;

import java.util.List;

public class ProductDao {
    private static final ProductDao INSTANCE = new ProductDao();

    ///////////
    private static SessionFactory sessionFactory;

    public static void initializeSessionFactory() {
        sessionFactory = HibernateUtil
                .configureWithAnnotatedClasses(CartItem.class, Product.class,
                        Cart.class, User.class, UserProduct.class);
    }

    static {
        initializeSessionFactory();
    }
    ///////////

    public static ProductDao getInstance() {
        return INSTANCE;
    }

    private ProductDao() {
    }

    public List<Product> findAllProducts() throws Exception {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Product", Product.class).list();
        } catch (Exception e) {
            throw new Exception("Error retrieving all products", e);
        }
    }

    public Product getProductById(Long productId) {
        Transaction transaction = null;
        Product product = null;

        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            // Use Hibernate to retrieve the product by its ID
            product = session.get(Product.class, productId);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

        return product;
    }

    public List<Product> getProductsByUserId(Long userId) throws Exception {
        Transaction transaction = null;
        List<Product> products = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            String hql = "SELECT up.product FROM UserProduct up WHERE up.user.id = :userId";
            Query<Product> query = session.createQuery(hql, Product.class);
            query.setParameter("userId", userId);
            products = query.list();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new Exception("Error retrieving products for user ID: " + userId, e);
        }
        return products;
    }
}
