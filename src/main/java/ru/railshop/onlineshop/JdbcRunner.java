package ru.railshop.onlineshop;

import ru.railshop.onlineshop.dao.*;
import ru.railshop.onlineshop.entity.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class JdbcRunner {
    public static void main(String[] args) {

        /***
         * UserDaoCheck
         */

        UserDao userDao = UserDao.getInstance();
        User user9 = new User(9L, "user9", "password9", "user9@example.com");

        // Test Save
        //userDao.save(user9);

        // Test Update
        //userDao.update(user9);

        // Test Find All
        System.out.println(userDao.findAll());

        // Test Find By ID
        //System.out.println(userDao.findById(10L));

        // Test Delete
        //userDao.delete(4L);

        /***
         * ProductDaoCheck
         */

        ProductDao productDao = ProductDao.getInstance();
        BigDecimal price = new BigDecimal(40000.00);
        Product product = new Product(10L, "Монитор Acer", "Описание товара", price, 10);

        // Test Save
        //productDao.save(product);

        // Test Update
        // productDao.update(product);

        // Test Find All
        // System.out.println(productDao.findAll());

        // Test Find By ID
        //System.out.println(productDao.findById(1L));

        // Test Delete
        //productDao.delete(10L);

        /***
         * OrderDaoCheck
         */

        LocalDateTime orderDate = LocalDateTime.of(2020, 01, 01, 07, 25);
        OrderDao orderDao = OrderDao.getInstance();
        Order order = new Order(10L, orderDate, OrderStatus.PROCESSING);

        // Test Save
        //orderDao.save(order);

        // Test Update
        //orderDao.update(order);

        // Test Find All
        //System.out.println(orderDao.findAll());

        // Test Find By ID
        //System.out.println(orderDao.findById(1L));

        // Test Delete
        //orderDao.delete(10L);

        /***
         * ReviewDaoCheck
         */

        ReviewDao reviewDao = ReviewDao.getInstance();
        Review review = new Review(26L, product, "not good", 3);

        // Test Save
        //reviewDao.save(review);

        // Test Update
        //reviewDao.update(review);

        // Test Find All
//        System.out.println(reviewDao.findAll());

        // Test Find By ID
//        System.out.println(reviewDao.findById(1L));

        // Test Delete
        //reviewDao.delete(10L);

        /***
         * OrderDetailDaoCheck
         */

        OrderDetail orderDetail = new OrderDetail(11L, order, product, 111);
        OrderDetailDao orderDetailDao = OrderDetailDao.getInstance();

        // Test Save
        //orderDetailDao.save(orderDetail);

        // Test Update
        // orderDetailDao.update(orderDetail);

        // Test Find All
        //System.out.println(orderDetailDao.findAll());

        // Test Find By ID
        //System.out.println(orderDetailDao.findById(1L));

        // Test Delete
        //orderDetailDao.delete(10L);

        /***
         * CartDaoCheck
         */

        LocalDateTime createdAt = LocalDateTime.of(2020, 01, 01, 07, 25);
        CartDao cartDao = CartDao.getInstance();
        Cart cart = new Cart(9L, user9, createdAt);

        // Test Save
        //cartDao.save(cart);

        // Test Update
        //cartDao.update(cart);

        // Test Find All
//        System.out.println(cartDao.findAll());

        // Test Find By ID
//        System.out.println(cartDao.findById(2L));

        // Test Delete
        //cartDao.delete(10L);

        /***
         * CartItemDaoCheck
         */

        CartItemDao cartItemDao = CartItemDao.getInstance();
        CartItem cartItem = new CartItem(7L, cart, product, 11);

        // Test Save
        //cartItemDao.save(cartItem);

        // Test Update
        //cartItemDao.update(cartItem);

        // Test Find All
//        System.out.println(cartItemDao.findAll());

        // Test Find By ID
//        System.out.println(cartItemDao.findById(1L));

        // Test Delete
        //cartItemDao.delete(10L);
    }
}
