package ru.railshop.onlineshop.dao;

import ru.railshop.onlineshop.entity.Product;
import ru.railshop.onlineshop.exception.DaoException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDao implements Dao<Long, Product> {

    private static final ProductDao INSTANCE = new ProductDao();

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
        return null;
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
