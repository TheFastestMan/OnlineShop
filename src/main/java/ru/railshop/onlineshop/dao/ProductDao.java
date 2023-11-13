package ru.railshop.onlineshop.dao;

import com.querydsl.jpa.hibernate.HibernateQueryFactory;
import com.querydsl.jpa.impl.JPAQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.railshop.onlineshop.entity.*;
import ru.railshop.onlineshop.util.HibernateUtil;

import java.util.List;

public class ProductDao extends BaseRepository<Long, Product> {
    private static final QProduct qProduct = QProduct.product;
    private static final QUserProduct qUserProduct = QUserProduct.userProduct;
    private static final QProduct product = QProduct.product;
    private static SessionFactory sessionFactory = initializeSessionFactory();
    private static final ProductDao INSTANCE = new ProductDao(sessionFactory);

    public ProductDao(SessionFactory sessionFactory) {
        super(sessionFactory, Product.class);
    }

    public static SessionFactory initializeSessionFactory() {
        return HibernateUtil
                .configureWithAnnotatedClasses(CartItem.class, Product.class,
                        Cart.class, User.class, UserProduct.class);
    }

    public static ProductDao getInstance() {
        return INSTANCE;
    }


    public List<Product> getProductsByUserId(Long userId) {
        try (Session session = sessionFactory.openSession()) {
            JPAQuery<Product> query = new JPAQuery<>(session);
            return query.select(qUserProduct.product)
                    .from(qUserProduct)
                    .where(qUserProduct.user.id.eq(userId))
                    .fetch();
        } catch (Exception e) {
            throw new RuntimeException("Error retrieving products for user ID: " + userId, e);
        }
    }

    public void decreaseQuantityByAmount(Long productId, int quantityToDecrease) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            Product product = session.get(Product.class, productId);
            if (product != null) {
                int newQuantity = product.getQuantity() - quantityToDecrease;
                product.setQuantity(newQuantity);
                session.saveOrUpdate(product);
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error during quantity decrease", e);
        }
    }

}
