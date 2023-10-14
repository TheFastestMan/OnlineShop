package ru.railshop.onlineshop.dao;

import ru.railshop.onlineshop.entity.Cart;
import ru.railshop.onlineshop.entity.Gender;
import ru.railshop.onlineshop.entity.Role;
import ru.railshop.onlineshop.entity.User;
import ru.railshop.onlineshop.exception.DaoException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CartDao implements Dao<Long, Cart> {
    private static final CartDao INSTANCE = new CartDao();


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
