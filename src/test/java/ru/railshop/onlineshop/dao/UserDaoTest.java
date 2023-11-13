package ru.railshop.onlineshop.dao;

import lombok.Cleanup;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.*;
import ru.railshop.onlineshop.entity.*;
import ru.railshop.onlineshop.util.HibernateUtil;
import ru.railshop.onlineshop.utils.TestDataImporter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
class UserDaoTest {
    private Date currentTimestamp = Timestamp.valueOf(LocalDateTime.now());
    private final UserDao userDao = UserDao.getInstance();
    private final CartDao cartDao = CartDao.getInstance();
    private final ProductDao productDao = ProductDao.getInstance();
    private final SessionFactory sessionFactory = HibernateUtil.configureWithAnnotatedClasses(User.class,
            Cart.class, CartItem.class, Product.class, UserProduct.class);

    @BeforeAll
    public void initDb() {
        TestDataImporter.importData(sessionFactory);
    }

    @AfterAll
    public void finish() {
        sessionFactory.close();
    }

    /**
     * User test
     */

    @Test
    void testFindAllUsersShouldEqualsAssert() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<User> results = userDao.findAll();
        assertThat(results).hasSize(2);

        List<String> userNames = results.stream()
                .map(User::getUsername)
                .collect(Collectors.toList());
        assertThat(userNames).containsExactly("TestUser1", "TestUser2");

        session.getTransaction().commit();
    }

    @Test
    public void testSaveUserShouldEqualsAssert() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        User newUser = new User();
        newUser.setEmail("user3_test@gmail.com");
        newUser.setGender(Gender.FEMALE);
        newUser.setPassword("password3");
        newUser.setRole(Role.USER);
        newUser.setUsername("user");

        userDao.save(newUser);

        assertEquals("user3_test@gmail.com", newUser.getEmail(), "Emails should match");
        assertEquals("user", newUser.getUsername(), "Emails should match");
        assertEquals("password3", newUser.getPassword(), "Passwords should match");
        assertEquals(Role.USER, newUser.getRole(), "Passwords should match");
        assertEquals(Gender.FEMALE, newUser.getGender(), "Passwords should match");

        session.getTransaction().commit();
    }

    @Test
    public void testFindByEmailAndPasswordShouldEqualsAssert() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        String email = "user1_test@gmail.com";
        String password = "password1";

        Optional<User> optionalUser = userDao.findByEmailAndPassword(email, password);

        assertTrue(optionalUser.isPresent(), "User should be present");
        assertEquals(email, optionalUser.get().getEmail(), "Emails should match");
        assertEquals(password, optionalUser.get().getPassword(), "Passwords should match");

        session.getTransaction().commit();
    }

    @Test
    public void findByEmailShouldEqualsAssert() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        String email = "user1_test@gmail.com";

        Optional<User> optionalUser = userDao.findByEmail(email);

        assertTrue(optionalUser.isPresent(), "User should be present");
        assertEquals(email, optionalUser.get().getEmail(), "Emails should match");

        session.getTransaction().commit();

    }

    /**
     * Product test
     */

    @Test
    public void testFindAllProductsShouldEqualsAssert() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        List<Product> products = productDao.findAll();

        assertThat(products).hasSize(1);

        List<String> productName = products.stream()
                .map(Product::getProductName)
                .collect(Collectors.toList());

        assertThat(productName).containsExactly("testProduct");

    }

    @Test
    public void testGetProductByIdShouldEqualsAssert() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        Long productId = 1L;

        Optional<Product> foundProduct = ProductDao.getInstance().findById(productId);

        Assertions.assertNotNull(foundProduct, "The product should be found");
        Assertions.assertEquals(productId, foundProduct.get().getId(), "The product IDs should match");
    }

    @Test
    public void testGetProductsByUserIdShouldEqualsAssert() {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        Long userId = 2L;

        List<Product> products = productDao.getProductsByUserId(userId);

        List<String> productName = products.stream()
                .map(Product::getProductName)
                .collect(Collectors.toList());

        assertThat(productName).containsExactly("testProduct");

    }

    /**
     * Cart test
     */

    @Test
    public void testAddProductToCartShouldEqualsAssert() throws Exception {
        @Cleanup Session session = sessionFactory.openSession();
        session.beginTransaction();

        User newUser = new User();
        newUser.setEmail("user4_test@gmail.com");
        newUser.setGender(Gender.FEMALE);
        newUser.setPassword("password4");
        newUser.setRole(Role.USER);
        newUser.setUsername("user4");

        newUser.setCarts(new ArrayList<>());

        Product newProduct = new Product();
        newProduct.setProductName("product2");
        newProduct.setPrice(200.5);
        newProduct.setDescription("description2");

        Cart newCart = new Cart();
        newCart.setUser(newUser);
        newCart.setCreatedAt(currentTimestamp);

        newUser.getCarts().add(newCart);

        assertEquals("user4", newUser.getUsername(), "Usernames should match");
        assertEquals("product2", newProduct.getProductName(), "Product names should match");

        session.getTransaction().commit();
    }
}