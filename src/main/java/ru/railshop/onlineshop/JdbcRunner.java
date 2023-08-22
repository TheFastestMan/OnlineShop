package ru.railshop.onlineshop;

import ru.railshop.onlineshop.dao.UserDao;
import ru.railshop.onlineshop.entity.User;

public class JdbcRunner {
    public static void main(String[] args) {

        UserDao userDao = UserDao.getInstance();
        User user1 = new User(4L, "user4", "password4","user4@example.com");
        System.out.println(userDao.findById(10L));
        userDao.delete(4L);
    }
}
