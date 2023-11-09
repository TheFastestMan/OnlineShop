package ru.railshop.onlineshop;

import lombok.Cleanup;
import lombok.experimental.UtilityClass;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.railshop.onlineshop.entity.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@UtilityClass
public class TestDataImporter {
    public void importData(SessionFactory sessionFactory) {
        @Cleanup Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();

            Date currentTimestamp = Timestamp.valueOf(LocalDateTime.now());

            User testUser = saveUser(session, "TestUser10", "password1",
                    "user10_test@gmail.com", Role.USER, Gender.FEMALE);
//            User testUser2 = saveUser(session, "TestUser20", "password2",
//                    "user20_test@gmail.com", Role.USER, Gender.MALE);


            Product testProduct = saveProduct(session, "testProduct", 100.5, "testDescription", 100);
            Product testProduct2 = saveProduct(session, "testProduct2", 100.5, "testDescription2", 100);
            Product testProduct3 = saveProduct(session, "testProduct3", 100.5, "testDescription3", 100);
            Product testProduct4 = saveProduct(session, "testProduct4", 100.5, "testDescription4", 100);

            UserProduct testUserProduct = saveUserProduct(session, testUser, testProduct);
//            UserProduct testUserProduct2 = saveUserProduct(session, testUser2, testProduct);

            Cart testCart = saveCart(session, testUser, currentTimestamp);
//            Cart testCart2 = saveCart(session, testUser2, currentTimestamp);

            session.getTransaction().commit();
        } catch (Exception e) {
            if (session.getTransaction() != null) {
                session.getTransaction().rollback();
            }
            throw e;
        }
    }

    private User saveUser(Session session,
                          String username,
                          String password,
                          String email,
                          Role role,
                          Gender gender
    ) {
        User user = User.builder()
                .username(username)
                .password(password)
                .email(email)
                .role(role)
                .gender(gender)
                .build();

        session.save(user);

        return user;
    }

    private Product saveProduct(Session session, String productName, Double price, String description, Integer quantity) {
        Product product = Product.builder()
                .productName(productName)
                .quantity(quantity)
                .price(price)
                .description(description)
                .build();

        session.save(product);

        return product;
    }

    private UserProduct saveUserProduct(Session session, User user, Product product) {
        UserProduct userProduct = UserProduct.builder()
                .product(product)
                .user(user)
                .build();

        session.save(userProduct);

        return (userProduct);
    }

    private Cart saveCart(Session session, User user, Date date) {
        Cart cart = Cart.builder()
                .user(user)
                .createdAt(date)
                .build();

        session.save(cart);

        return (cart);
    }
}
