package ru.railshop.onlineshop.dao;

import com.querydsl.jpa.hibernate.HibernateQueryFactory;
import com.querydsl.jpa.impl.JPAQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.railshop.onlineshop.entity.*;
import ru.railshop.onlineshop.util.HibernateUtil;

import java.util.List;

public class ProductDao {
    private static final ProductDao INSTANCE = new ProductDao();
    private static final QProduct qProduct = QProduct.product;
    private static final QUserProduct qUserProduct = QUserProduct.userProduct;
    private static final QProduct product = QProduct.product;


    private static SessionFactory sessionFactory;

    public static void initializeSessionFactory() {
        sessionFactory = HibernateUtil
                .configureWithAnnotatedClasses(CartItem.class, Product.class,
                        Cart.class, User.class, UserProduct.class);
    }

    static {
        initializeSessionFactory();
    }

    public static ProductDao getInstance() {
        return INSTANCE;
    }

    private ProductDao() {
    }

    public List<Product> findAllProducts() {
        try (Session session = sessionFactory.openSession()) {
            JPAQuery<Product> query = new JPAQuery<>(session);
            return query.select(qProduct)
                    .from(qProduct)
                    .fetch();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving all products", e);
        }
    }

    public Product getProductById(Long productId) {
        try (Session session = sessionFactory.openSession()) {
            JPAQuery<Product> query = new JPAQuery<>(session);
            return query.select(qProduct)
                    .from(qProduct)
                    .where(qProduct.productId.eq(productId))
                    .fetchOne();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving product by ID: " + productId, e);
        }
    }

    public List<Product> getProductsByUserId(Long userId) {
        try (Session session = sessionFactory.openSession()) {
            JPAQuery<Product> query = new JPAQuery<>(session);
            return query.select(qUserProduct.product)
                    .from(qUserProduct)
                    .where(qUserProduct.user.userId.eq(userId))
                    .fetch();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving products for user ID: " + userId, e);
        }
    }

    public void decreaseQuantityByAmount(Long productId, int quantityToDecrease) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            HibernateQueryFactory queryFactory = new HibernateQueryFactory(session);

            long affectedRows = queryFactory.update(product)
                    .where(product.productId.eq(productId)
                            .and(product.quantity.goe(quantityToDecrease)))
                    .set(product.quantity, product.quantity.subtract(quantityToDecrease))
                    .execute();

            if (affectedRows == 0) {

                throw new RuntimeException("No product was updated, check the product ID and its quantity");
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw e;
        }
    }


}
