package ru.railshop.onlineshop.dao;

import ru.railshop.onlineshop.entity.Gender;
import ru.railshop.onlineshop.entity.Role;
import ru.railshop.onlineshop.entity.User;
import ru.railshop.onlineshop.exception.DaoException;
import ru.railshop.onlineshop.util.ConnectionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDao implements Dao<Long, User> {

    private static final UserDao INSTANCE = new UserDao();

    private static final String SAVE_SQL = """
            INSERT INTO users (
            username, password, email, role, gender
            ) 
            values 
            (?,?,?,?,?);
            """;

    private static final String UPDATE_SQL = """
            UPDATE users
             SET username = ?, password = ?, email = ?, role = ?, gender = ?
            WHERE id = ?
            """;

    private static final String FIND_ALL_SQL = """
            SELECT id, username, password, email, role, gender FROM users
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
            prepareStatement.setObject(4, user.getRole());
            prepareStatement.setObject(5, user.getGender());
            prepareStatement.setLong(6, user.getId());

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
                users.add(buildUser(result));
            return users;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private static User buildUser(ResultSet result) throws SQLException {
        return new User(result.getLong("id"),
                result.getString("username"),
                result.getString("password"),
                result.getString("email"),
                (Role) result.getObject("role"),
                (Gender) result.getObject("gender"));
    }

    @Override
    public Optional<User> findById(Long id) {
        try (var connection = ConnectionManager.open();
             var prepareStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            prepareStatement.setLong(1, id);

            User user = null;
            var result = prepareStatement.executeQuery();

            while (result.next()) {
                user = buildUser(result);
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
            prepareStatement.setObject(4, user.getRole());
            prepareStatement.setObject(5, user.getGender());

            prepareStatement.executeUpdate();

            var keys = prepareStatement.getGeneratedKeys();
            if (keys.next())
                user.setId(keys.getObject("id", Long.class));
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
