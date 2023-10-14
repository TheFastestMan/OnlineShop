package ru.railshop.onlineshop.dao;

import ru.railshop.onlineshop.entity.Order;
import ru.railshop.onlineshop.entity.OrderStatus;
import ru.railshop.onlineshop.exception.DaoException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderDao implements Dao<Long, Order> {
    private static final OrderDao INSTANCE = new OrderDao();

    private OrderDao() {
    }

    public static OrderDao getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean update(Order order) {
        return false;
    }

    @Override
    public List<Order> findAll() {
        return null;
    }

    @Override
    public Optional<Order> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Order save(Order order) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
