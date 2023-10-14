package ru.railshop.onlineshop.dao;

import org.hibernate.SessionFactory;
import ru.railshop.onlineshop.entity.Cart;

import ru.railshop.onlineshop.util.ConfigurationUtil;

import java.util.List;
import java.util.Optional;

public class CartDao implements Dao<Long, Cart> {
    private static final CartDao INSTANCE = new CartDao();
    ///////////
    private static SessionFactory sessionFactory;

    public static void initializeSessionFactory() {
        sessionFactory = ConfigurationUtil
                .configureWithAnnotatedClasses(Cart.class);
    }

    static {
        initializeSessionFactory();
    }

    ///////////
    private CartDao() {
    }

    public static CartDao getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean update(Cart cart) {
        return false;
    }

    @Override
    public List<Cart> findAll() {
        return null;
    }

    @Override
    public Optional<Cart> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Cart save(Cart cart) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
