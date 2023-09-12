package ru.railshop.onlineshop.dao;

import ru.railshop.onlineshop.entity.*;
import ru.railshop.onlineshop.exception.DaoException;
import ru.railshop.onlineshop.util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CartItemDao implements Dao<Long, CartItem> {
    private static final CartItemDao INSTANCE = new CartItemDao();
    private static final String SAVE_SQL = """
            INSERT INTO cart_items (
             cart_id, product_id, quantity
            ) 
            values 
            (?,?,?);
            """;

    private static final String UPDATE_SQL = """
            UPDATE cart_items
             SET cart_id = ?, product_id = ?, quantity = ?
            WHERE id = ?
            """;

    private static final String FIND_ALL_SQL = """
            SELECT  c_i.id, c_i.cart_id, c.user_id, c.created_at,
                       c.user_id, u.username, u.password, u.email, u.role, u.gender,
                       c_i.product_id, p.name, p.description, p.price, p.quantity,
                       c_i.quantity
            FROM cart_items c_i
            INNER JOIN carts c ON c_i.cart_id = c.id 
            INNER JOIN users u ON c.user_id = u.id
            INNER JOIN products p ON c_i.product_id = p.id
            """;
    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE c_i.id = ?;
            """;
    private static final String DELETE_SQL = """
            DELETE FROM cart_items WHERE
            id = ?;
            """;

    @Override
    public boolean update(CartItem cartItem) {
        try (var connection = ConnectionManager.open();
             var prepareStatement = connection.prepareStatement(UPDATE_SQL)) {

            prepareStatement.setLong(1, cartItem.getCartId().getId());
            prepareStatement.setLong(2, cartItem.getProductId().getId());
            prepareStatement.setInt(3, cartItem.getQuantity());
            prepareStatement.setLong(4, cartItem.getId());

            return prepareStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<CartItem> findAll() {
        try (var connection = ConnectionManager.open();
             var prepareStatement = connection.prepareStatement(FIND_ALL_SQL)) {

            List<CartItem> cartItems = new ArrayList<>();

            var result = prepareStatement.executeQuery();

            while (result.next())
                cartItems.add(buildCartItem(result)
                );
            return cartItems;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private static CartItem buildCartItem(ResultSet result) throws SQLException {
        return new CartItem(result.getLong("id"),
                new Cart(result.getLong("id"),
                        new User(result.getLong("id"),
                                result.getString("username"),
                                result.getString("password"),
                                result.getString("email"),
                                (Role) result.getObject("role"),
                                (Gender) result.getObject("gender")
                        ),
                        result.getTimestamp("created_at").toLocalDateTime()
                ),
                new Product(
                        result.getLong("id"),
                        result.getString("name"),
                        result.getString("description"),
                        result.getBigDecimal("price"),
                        result.getInt("quantity")
                ),
                result.getInt("quantity")
        );
    }

    @Override
    public Optional<CartItem> findById(Long id) {
        try (var connection = ConnectionManager.open();
             var prepareStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            prepareStatement.setLong(1, id);

            CartItem cartItem = null;
            var result = prepareStatement.executeQuery();

            while (result.next()) {
                cartItem = buildCartItem(result);
            }
            return Optional.ofNullable(cartItem);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public CartItem save(CartItem cartItem) {
        try (var connection = ConnectionManager.open();
             var prepareStatement = connection.prepareStatement(SAVE_SQL,
                     Statement.RETURN_GENERATED_KEYS)) {

            prepareStatement.setLong(1, cartItem.getCartId().getId());
            prepareStatement.setLong(2, cartItem.getProductId().getId());
            prepareStatement.setInt(3, cartItem.getQuantity());


            prepareStatement.executeUpdate();

            var keys = prepareStatement.getGeneratedKeys();

            if (keys.next())
                cartItem.setId(keys.getLong("id"));
            return cartItem;

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

    private CartItemDao() {
    }

    public static CartItemDao getInstance() {
        return INSTANCE;
    }
}
