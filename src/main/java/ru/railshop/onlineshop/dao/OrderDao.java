package ru.railshop.onlineshop.dao;

import ru.railshop.onlineshop.entity.Order;
import ru.railshop.onlineshop.entity.OrderStatus;
import ru.railshop.onlineshop.entity.Product;
import ru.railshop.onlineshop.exception.DaoException;
import ru.railshop.onlineshop.util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderDao implements Dao<Long, Order> {

    private static final OrderDao INSTANCE = new OrderDao();

    private static final String SAVE_SQL = """
            INSERT INTO orders (
             order_date, status
            ) 
            values 
            (?,?);
            """;

    private static final String UPDATE_SQL = """
            UPDATE orders
             SET order_date = ?, status = ?
            WHERE id = ?
            """;

    private static final String FIND_ALL_SQL = """
            SELECT id, order_date, status FROM orders
            """;
    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?;
            """;
    private static final String DELETE_SQL = """
            DELETE FROM orders WHERE
            id = ?;
            """;

    @Override
    public boolean update(Order order) {
        try (var connection = ConnectionManager.open();
             var prepareStatement = connection.prepareStatement(UPDATE_SQL)) {

            prepareStatement.setTimestamp(1, Timestamp.valueOf(order.getOrderDate()));
            prepareStatement.setString(2, String.valueOf(order.getOrderStatus()));
            prepareStatement.setLong(3, order.getId());

            return prepareStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Order> findAll() {
        try (var connection = ConnectionManager.open();
             var prepareStatement = connection.prepareStatement(FIND_ALL_SQL)) {

            List<Order> orders = new ArrayList<>();

            var result = prepareStatement.executeQuery();

            while (result.next())
                orders.add(buildOrder(result)
                );
            return orders;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private static Order buildOrder(ResultSet result) throws SQLException {
        return new Order(result.getLong("id"),
                result.getTimestamp("order_date").toLocalDateTime(),
                OrderStatus.valueOf(result.getString("status"))
        );
    }

    @Override
    public Optional<Order> findById(Long id) {
        try (var connection = ConnectionManager.open();
             var prepareStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            prepareStatement.setLong(1, id);

            Order order = null;
            var result = prepareStatement.executeQuery();

            while (result.next()) {
                order = buildOrder(result);
            }
            return Optional.ofNullable(order);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Order save(Order order) {
        try (var connection = ConnectionManager.open();
             var prepareStatement = connection.prepareStatement(SAVE_SQL,
                     Statement.RETURN_GENERATED_KEYS)) {

            prepareStatement.setTimestamp(1, Timestamp.valueOf(order.getOrderDate()));
            prepareStatement.setString(2, String.valueOf(order.getOrderStatus()));


            prepareStatement.executeUpdate();

            var keys = prepareStatement.getGeneratedKeys();

            if (keys.next())
                order.setId(keys.getLong("id"));
            return order;

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

    private OrderDao() {
    }

    public static OrderDao getInstance() {
        return INSTANCE;
    }
}
