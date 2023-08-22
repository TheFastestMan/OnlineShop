package ru.railshop.onlineshop;

import ru.railshop.onlineshop.dao.OrderDao;
import ru.railshop.onlineshop.dao.ProductDao;
import ru.railshop.onlineshop.dao.UserDao;
import ru.railshop.onlineshop.entity.Order;
import ru.railshop.onlineshop.entity.OrderStatus;
import ru.railshop.onlineshop.entity.Product;
import ru.railshop.onlineshop.entity.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class JdbcRunner {
    public static void main(String[] args) {

//        UserDao userDao = UserDao.getInstance();
//        User user1 = new User(4L, "user4", "password4","user4@example.com");
//        System.out.println(userDao.findById(10L));
//        userDao.delete(4L);

//        ProductDao productDao = ProductDao.getInstance();
//        BigDecimal price = new BigDecimal(1200.00);
//        Product product = new Product(11L, "Charger", "Описание товара", price, 100);
//
//        productDao.delete(11L);

        LocalDateTime orderDate = LocalDateTime.of(2020, 01, 01, 07, 25);
        OrderDao orderDao = OrderDao.getInstance();
        Order order = new Order(10L, orderDate, OrderStatus.PROCESSING);

        System.out.println(orderDao.findById(1L));
    }
}
