package ru.railshop.onlineshop.dao;

import ru.railshop.onlineshop.entity.Cart;
import ru.railshop.onlineshop.entity.User;
import ru.railshop.onlineshop.exception.DaoException;
import ru.railshop.onlineshop.util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CartDao implements Dao<Long, Cart> {
    private static final CartDao INSTANCE = new CartDao();
    private static final String SAVE_SQL = """
            INSERT INTO carts (
             user_id, created_at
            ) 
            values 
            (?,?);
            """;

    private static final String UPDATE_SQL = """
            UPDATE carts
             SET user_id = ?, created_at = ?
            WHERE id = ?
            """;

    private static final String FIND_ALL_SQL = """
            SELECT c.id, c.user_id, u.username, u.password, u.email,
            c.created_at
            FROM carts c 
            INNER JOIN users u ON c.user_id = u.id 
            """;
    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE c.id = ?;
            """;
    private static final String DELETE_SQL = """
            DELETE FROM carts WHERE
            id = ?;
            """;

    @Override
    public boolean update(Cart cart) {
        try (var connection = ConnectionManager.open();
             var prepareStatement = connection.prepareStatement(UPDATE_SQL)) {

            prepareStatement.setLong(1, cart.getUserId().getId());
            prepareStatement.setTimestamp(2, Timestamp.valueOf(cart.getCreatedAt()));
            prepareStatement.setLong(3, cart.getId());

            return prepareStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Cart> findAll() {
        try (var connection = ConnectionManager.open();
             var prepareStatement = connection.prepareStatement(FIND_ALL_SQL)) {

            List<Cart> carts = new ArrayList<>();

            var result = prepareStatement.executeQuery();

            while (result.next())
                carts.add(new Cart(result.getLong("id"),
                        new User(
                                result.getLong("id"),
                                result.getString("username"),
                                result.getString("password"),
                                result.getString("email")),
                        result.getTimestamp("created_at").toLocalDateTime())
                );
            return carts;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Cart> findById(Long id) {
        try (var connection = ConnectionManager.open();
             var prepareStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            prepareStatement.setLong(1, id);

            Cart cart = null;
            var result = prepareStatement.executeQuery();

            while (result.next()) {
                cart = buildCart(result);
            }
            return Optional.ofNullable(cart);
        } catch (SQLException e) {

            throw new DaoException(e);
        }
    }

    private static Cart buildCart(ResultSet result) throws SQLException {
        return new Cart(result.getLong("id"),
                new User(
                        result.getLong("id"),
                        result.getString("username"),
                        result.getString("password"),
                        result.getString("email")
                ),

                result.getTimestamp("created_at").toLocalDateTime()
        );
    }

    @Override
    public Cart save(Cart cart) {
        try (var connection = ConnectionManager.open();
             var prepareStatement = connection.prepareStatement(SAVE_SQL,
                     Statement.RETURN_GENERATED_KEYS)) {

            prepareStatement.setLong(1, cart.getUserId().getId());
            prepareStatement.setTimestamp(2, Timestamp.valueOf(cart.getCreatedAt()));

            prepareStatement.executeUpdate();

            var keys = prepareStatement.getGeneratedKeys();

            if (keys.next())
                cart.setId(keys.getLong("id"));
            return cart;

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

    private CartDao() {
    }

    public static CartDao getInstance() {
        return INSTANCE;
    }
}
