package ru.railshop.onlineshop.dao;

import ru.railshop.onlineshop.entity.Product;
import ru.railshop.onlineshop.entity.User;
import ru.railshop.onlineshop.exception.DaoException;
import ru.railshop.onlineshop.util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDao implements Dao<Long, Product> {

    private static final ProductDao INSTANCE = new ProductDao();

    private static final String SAVE_SQL = """
            INSERT INTO products (
            name, description, price, quantity
            ) 
            values 
            (?,?,?,?);
            """;

    private static final String UPDATE_SQL = """
            UPDATE products
             SET name = ?, description = ?, price = ?, quantity = ?
            WHERE id = ?
            """;

    private static final String FIND_ALL_SQL = """
            SELECT id, name, description,price, quantity FROM products
            """;
    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?;
            """;
    private static final String DELETE_SQL = """
            DELETE FROM products WHERE
            id = ?;
            """;

    @Override
    public boolean update(Product product) {

        try (var connection = ConnectionManager.open();
             var prepareStatement = connection.prepareStatement(UPDATE_SQL)) {

            prepareStatement.setString(1, product.getName());
            prepareStatement.setString(2, product.getDescription());
            prepareStatement.setBigDecimal(3, product.getPrice());
            prepareStatement.setInt(4, product.getQuantity());
            prepareStatement.setLong(5, product.getId());

            return prepareStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Product> findAll() {
        try (var connection = ConnectionManager.open();
             var prepareStatement = connection.prepareStatement(FIND_ALL_SQL)) {

            List<Product> products = new ArrayList<>();

            var result = prepareStatement.executeQuery();

            while (result.next())
                products.add(buildProduct(result));
            return products;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private static Product buildProduct(ResultSet result) throws SQLException {
        return new Product(result.getLong("id"),
                result.getString("name"),
                result.getString("description"),
                result.getBigDecimal("price"),
                result.getInt("quantity"));
    }

    @Override
    public Optional<Product> findById(Long id) {
        try (var connection = ConnectionManager.open();
             var prepareStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            prepareStatement.setLong(1, id);

            Product product = null;
            var result = prepareStatement.executeQuery();

            while (result.next()) {
                product = buildProduct(result);
            }
            return Optional.ofNullable(product);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Product save(Product product) {
        try (var connection = ConnectionManager.open();
             var prepareStatement = connection.prepareStatement(SAVE_SQL,
                     Statement.RETURN_GENERATED_KEYS)) {

            prepareStatement.setString(1, product.getName());
            prepareStatement.setString(2, product.getDescription());
            prepareStatement.setBigDecimal(3, product.getPrice());
            prepareStatement.setInt(4, product.getQuantity());

            prepareStatement.executeUpdate();

            var keys = prepareStatement.getGeneratedKeys();

            if (keys.next())
                product.setId(keys.getLong("id"));
            return product;

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (var connection = ConnectionManager.open();
             var prepareStatement = connection.prepareStatement(DELETE_SQL)) {
            prepareStatement.setLong(1, id);

            return prepareStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private ProductDao() {
    }

    public static ProductDao getInstance() {
        return INSTANCE;
    }
}
