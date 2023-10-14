package ru.railshop.onlineshop.dao;

import org.hibernate.SessionFactory;
import ru.railshop.onlineshop.entity.Cart;
import ru.railshop.onlineshop.entity.Product;
import ru.railshop.onlineshop.entity.Review;
import ru.railshop.onlineshop.exception.DaoException;
import ru.railshop.onlineshop.util.ConfigurationUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReviewDao implements Dao<Long, Review> {
    private static final ReviewDao INSTANCE = new ReviewDao();

    ///////////
    private static SessionFactory sessionFactory;

    public static void initializeSessionFactory() {
        sessionFactory = ConfigurationUtil
                .configureWithAnnotatedClasses(Review.class);
    }

    static {
        initializeSessionFactory();
    }

    ///////////

    private ReviewDao() {
    }

    public static ReviewDao getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean update(Review review) {
        return false;
    }

    @Override
    public List<Review> findAll() {
        return null;
    }

    @Override
    public Optional<Review> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Review save(Review review) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
