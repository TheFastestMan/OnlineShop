package ru.railshop.onlineshop.dao;

import ru.railshop.onlineshop.entity.*;
import ru.railshop.onlineshop.exception.DaoException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CartItemDao implements Dao<Long, CartItem> {
    private static final CartItemDao INSTANCE = new CartItemDao();

    private CartItemDao() {
    }

    public static CartItemDao getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean update(CartItem cartItem) {
        return false;
    }

    @Override
    public List<CartItem> findAll() {
        return null;
    }

    @Override
    public Optional<CartItem> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public CartItem save(CartItem cartItem) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
