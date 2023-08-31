package ru.railshop.onlineshop.dao;

import ru.railshop.onlineshop.entity.Product;
import ru.railshop.onlineshop.entity.Review;
import ru.railshop.onlineshop.exception.DaoException;
import ru.railshop.onlineshop.util.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReviewDao implements Dao<Long, Review> {
    private static final ReviewDao INSTANCE = new ReviewDao();

    private static final String SAVE_SQL = """
            INSERT INTO reviews (
            product_id, review_text, rating
            ) 
            values 
            (?,?,?);
            """;

    private static final String UPDATE_SQL = """
            UPDATE reviews
             SET product_id = ?, review_text = ?, rating = ?
            WHERE id = ?
            """;

    private static final String FIND_ALL_SQL = """
            SELECT r.id, r.product_id, p.name, p.description, p.price, p.quantity, r.review_text, r.rating
            FROM reviews r
            INNER JOIN products p ON p.id = r.product_id
            """;

    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE r.id = ?;
            """;
    private static final String DELETE_SQL = """
            DELETE FROM reviews WHERE
            id = ?;
            """;

    @Override
    public boolean update(Review review) {
        try (var connection = ConnectionManager.open();
             var prepareStatement = connection.prepareStatement(UPDATE_SQL)) {

            prepareStatement.setLong(1, review.getProductId().getId());
            prepareStatement.setString(2, review.getReviewText());
            prepareStatement.setInt(3, review.getRating());
            prepareStatement.setLong(4, review.getId());

            return prepareStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Review> findAll() {
        try (var connection = ConnectionManager.open();
             var prepareStatement = connection.prepareStatement(FIND_ALL_SQL)) {

            List<Review> reviews = new ArrayList<>();

            var result = prepareStatement.executeQuery();

            while (result.next())
                reviews.add(buildReview(result, "id")
                );
            return reviews;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private static Review buildReview(ResultSet result, String id) throws SQLException {
        return new Review(result.getLong("id"),
                new Product(
                        result.getLong(id),
                        result.getString("name"),
                        result.getString("description"),
                        result.getBigDecimal("price"),
                        result.getInt("quantity")),

                result.getString("review_text"),
                result.getInt("rating"));
    }

    @Override
    public Optional<Review> findById(Long id) {
        try (var connection = ConnectionManager.open();
             var prepareStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            prepareStatement.setLong(1, id);

            Review review = null;
            var result = prepareStatement.executeQuery();

            while (result.next()) {
                review = buildReview(result, "product_id");
            }
            return Optional.ofNullable(review);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Review save(Review review) {
        try (var connection = ConnectionManager.open();
             var prepareStatement = connection.prepareStatement(SAVE_SQL,
                     Statement.RETURN_GENERATED_KEYS)) {

            prepareStatement.setLong(1, review.getProductId().getId());
            prepareStatement.setString(2, review.getReviewText());
            prepareStatement.setInt(3, review.getRating());

            prepareStatement.executeUpdate();

            var keys = prepareStatement.getGeneratedKeys();

            if (keys.next())
                review.setId(keys.getLong("id"));
            return review;

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

    private ReviewDao() {
    }

    public static ReviewDao getInstance() {
        return INSTANCE;
    }
}
