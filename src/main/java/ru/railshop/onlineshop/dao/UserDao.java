package ru.railshop.onlineshop.dao;

import ru.railshop.onlineshop.entity.User;
import ru.railshop.onlineshop.exception.DaoException;
import ru.railshop.onlineshop.util.ConnectionManager;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDao implements Dao<Long, User> {

    private static final UserDao INSTANCE = new UserDao();

    private static final String SAVE_SQL = """
            INSERT INTO users (
            username, password, email
            ) 
            values 
            (?,?,?);
            """;

    private static final String UPDATE_SQL = """
            UPDATE users
             SET username = ?, password = ?, email = ?
            WHERE id = ?
            """;

    private static final String FIND_ALL_SQL = """
            SELECT id, username, password, email FROM users
            """;
    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?;
            """;
    private static final String DELETE_SQL = """
            DELETE FROM users WHERE
            id = ?;
            """;

    @Override
    public boolean update(User user) {

        try (var connection = ConnectionManager.open();
             var prepareStatement = connection.prepareStatement(UPDATE_SQL)) {

            prepareStatement.setString(1, user.getUsername());
            prepareStatement.setString(2, user.getPassword());
            prepareStatement.setString(3, user.getEmail());
            prepareStatement.setLong(4, user.getId());

            return prepareStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<User> findAll() {

        try (var connection = ConnectionManager.open();
             var prepareStatement = connection.prepareStatement(FIND_ALL_SQL)) {

            List<User> users = new ArrayList<>();

            var result = prepareStatement.executeQuery();

            while (result.next())
                users.add(new User(result.getLong("id"),
                        result.getString("username"),
                        result.getString("password"),
                        result.getString("email")));
            return users;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        try (var connection = ConnectionManager.open();
             var prepareStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            prepareStatement.setLong(1, id);

            User user = null;
            var result = prepareStatement.executeQuery();

            while (result.next()) {
                user = new User(result.getLong("id"),
                        result.getString("username"),
                        result.getString("password"),
                        result.getString("email"));
            }
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public User save(User user) {

        try (var connection = ConnectionManager.open();
             var prepareStatement = connection.prepareStatement(SAVE_SQL,
                     Statement.RETURN_GENERATED_KEYS)) {

            prepareStatement.setString(1, user.getUsername());
            prepareStatement.setString(2, user.getPassword());
            prepareStatement.setString(3, user.getEmail());

            prepareStatement.executeUpdate();

            var keys = prepareStatement.getGeneratedKeys();

            if (keys.next())
                user.setId(keys.getLong("id"));
            return user;

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

    public static UserDao getInstance() {
        return INSTANCE;
    }

    private UserDao() {
    }
}
