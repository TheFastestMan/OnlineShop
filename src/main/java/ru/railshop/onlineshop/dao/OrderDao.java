package ru.railshop.onlineshop.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.railshop.onlineshop.entity.Cart;
import ru.railshop.onlineshop.entity.Order;
import ru.railshop.onlineshop.entity.OrderStatus;
import ru.railshop.onlineshop.exception.DaoException;
import ru.railshop.onlineshop.util.ConfigurationUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderDao implements Dao<Long, Order> {
    private static final OrderDao INSTANCE = new OrderDao();

    ///////////
    private static SessionFactory sessionFactory;

    public static void initializeSessionFactory() {
        sessionFactory = ConfigurationUtil
                .configureWithAnnotatedClasses(Order.class);
    }

    static {
        initializeSessionFactory();
    }

    ///////////


    private OrderDao() {
    }
    public static OrderDao getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Order> findAll() {
        List<Order> orders;
        try (Session session = sessionFactory.openSession()) {
            Query<Order> query = session.createQuery("FROM Order", Order.class);
            orders = query.list();
        } catch (Exception e) {
            throw new DaoException("Error retrieving all orders", e);
        }
        return orders;
    }

    @Override
    public Optional<Order> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Order order = session.get(Order.class, id);
            return Optional.ofNullable(order);
        } catch (Exception e) {
            throw new DaoException("Error finding order by ID", e);
        }
    }

    @Override
    public Order save(Order order) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.saveOrUpdate(order);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new DaoException("Error saving order", e);
        }
        return order;
    }

    @Override
    public boolean update(Order order) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(order);
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new DaoException("Error updating order", e);
        }
        return true;
    }

    @Override
    public boolean delete(Long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Order order = session.get(Order.class, id);
            if (order != null) {
                session.delete(order);
            }
            session.getTransaction().commit();
        } catch (Exception e) {
            throw new DaoException("Error deleting order", e);
        }
        return true;
    }

}