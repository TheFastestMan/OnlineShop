package ru.railshop.onlineshop.dao;

import ru.railshop.onlineshop.entity.*;
import ru.railshop.onlineshop.exception.DaoException;
import ru.railshop.onlineshop.util.ConnectionManager;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderDetailDao implements Dao<Long, OrderDetail> {
    private static final OrderDetailDao INSTANCE = new OrderDetailDao();

    private static final String SAVE_SQL = """
            INSERT INTO orderdetails (
             order_id, product_id, quantity
            ) 
            values 
            (?,?,?);
            """;

    private static final String UPDATE_SQL = """
            UPDATE orderdetails
             SET order_id = ?, product_id = ?, quantity = ?
            WHERE id = ?
            """;

    private static final String FIND_ALL_SQL = """
            SELECT orderD.id, orderD.order_id, o.order_date, o.status, 
            orderD.product_id, p.name, p.description, p.price, p.quantity, orderD.quantity
            FROM orderdetails orderD 
            INNER JOIN orders o ON orderD.order_id = o.id 
            INNER JOIN products p ON orderD.product_id = p.id
            """;
    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE orderD.id = ?;
            """;
    private static final String DELETE_SQL = """
            DELETE FROM orderdetails WHERE
            id = ?;
            """;


    @Override
    public boolean update(OrderDetail orderDetail) {
        try (var connection = ConnectionManager.open();
             var prepareStatement = connection.prepareStatement(UPDATE_SQL)) {

            prepareStatement.setLong(1, orderDetail.getOrderId().getId());
            prepareStatement.setLong(2, orderDetail.getProductId().getId());
            prepareStatement.setInt(3, orderDetail.getQuantity());
            prepareStatement.setLong(4, orderDetail.getId());

            return prepareStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<OrderDetail> findAll() {
        try (var connection = ConnectionManager.open();
             var prepareStatement = connection.prepareStatement(FIND_ALL_SQL)) {

            List<OrderDetail> orderDetails = new ArrayList<>();

            var result = prepareStatement.executeQuery();

            while (result.next())
                orderDetails.add(new OrderDetail(result.getLong("id"),
                                new Order(result.getLong("id"),
                                        result.getTimestamp("order_date").toLocalDateTime(),
                                        OrderStatus.valueOf(result.getString("status"))
                                ),
                                new Product(
                                        result.getLong("id"),
                                        result.getString("name"),
                                        result.getString("description"),
                                        result.getBigDecimal("price"),
                                        result.getInt("quantity")
                                ),
                                result.getInt("quantity")
                        )
                );
            return orderDetails;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }


    @Override
    public Optional<OrderDetail> findById(Long id) {
        try (var connection = ConnectionManager.open();
             var prepareStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            prepareStatement.setLong(1, id);

            OrderDetail orderDetail = null;
            var result = prepareStatement.executeQuery();

            while (result.next()) {
                orderDetail = new OrderDetail(result.getLong("id"),
                        new Order(result.getLong("id"),
                                result.getTimestamp("order_date").toLocalDateTime(),
                                OrderStatus.valueOf(result.getString("status"))
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
            return Optional.ofNullable(orderDetail);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }


    @Override
    public OrderDetail save(OrderDetail orderDetail) {
        try (var connection = ConnectionManager.open();
             var prepareStatement = connection.prepareStatement(SAVE_SQL,
                     Statement.RETURN_GENERATED_KEYS)) {

            prepareStatement.setLong(1, orderDetail.getOrderId().getId());
            prepareStatement.setLong(2, orderDetail.getProductId().getId());
            prepareStatement.setInt(3, orderDetail.getQuantity());

            prepareStatement.executeUpdate();

            var keys = prepareStatement.getGeneratedKeys();

            if (keys.next())
                orderDetail.setId(keys.getLong("id"));
            return orderDetail;

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

    private OrderDetailDao() {
    }

    public static OrderDetailDao getInstance() {
        return INSTANCE;
    }
}
