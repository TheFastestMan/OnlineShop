package ru.railshop.onlineshop.dao;

import ru.railshop.onlineshop.entity.Gender;
import ru.railshop.onlineshop.entity.Role;
import ru.railshop.onlineshop.entity.User;
import ru.railshop.onlineshop.exception.DaoException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDao implements Dao<Long, User> {

    private static final UserDao INSTANCE = new UserDao();

    public static UserDao getInstance() {
        return INSTANCE;
    }

    private UserDao() {
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
