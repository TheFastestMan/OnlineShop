package ru.railshop.onlineshop.dao;

import org.hibernate.SessionFactory;
import ru.railshop.onlineshop.entity.*;
import ru.railshop.onlineshop.exception.DaoException;
import ru.railshop.onlineshop.util.ConfigurationUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderDetailDao implements Dao<Long, OrderDetail> {
    private static final OrderDetailDao INSTANCE = new OrderDetailDao();

    ///////////
    private static SessionFactory sessionFactory;

    public static void initializeSessionFactory() {
        sessionFactory = ConfigurationUtil
                .configureWithAnnotatedClasses(OrderDetail.class);
    }

    static {
        initializeSessionFactory();
    }

    ///////////

    private OrderDetailDao() {
    }

    public static OrderDetailDao getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean update(OrderDetail orderDetail) {
        return false;
    }

    @Override
    public List<OrderDetail> findAll() {
        return null;
    }

    @Override
    public Optional<OrderDetail> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public OrderDetail save(OrderDetail orderDetail) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
