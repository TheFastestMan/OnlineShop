package ru.railshop.onlineshop.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.railshop.onlineshop.entity.Product;
import ru.railshop.onlineshop.entity.User;
import ru.railshop.onlineshop.exception.DaoException;
import ru.railshop.onlineshop.util.ConfigurationUtil;

import java.util.List;
import java.util.Optional;

public class ProductDao implements Dao<Long, Product> {

    private static final ProductDao INSTANCE = new ProductDao();

    ///////////
    private static SessionFactory sessionFactory;

    public static void initializeSessionFactory() {
        sessionFactory = ConfigurationUtil
                .configureWithAnnotatedClasses(Product.class, User.class);
    }

    static {
        initializeSessionFactory();
    }

    ///////////

    private ProductDao() {
    }

    public static ProductDao getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean update(Product product) {
        return false;
    }

    @Override
    public List<Product> findAll() {
        List<Product> products;
        try (Session session = sessionFactory.openSession()) {
            Query<Product> query = session.createQuery("FROM Product", Product.class);
            products = query.list();
        } catch (Exception e) {
            throw new DaoException("Error retrieving all products", e);
        }
        return products;
    }

    @Override
    public Optional<Product> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Product save(Product product) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
