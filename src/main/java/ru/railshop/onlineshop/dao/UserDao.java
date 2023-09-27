package ru.railshop.onlineshop.dao;

import ru.railshop.onlineshop.entity.Gender;
import ru.railshop.onlineshop.entity.Role;
import ru.railshop.onlineshop.entity.User;
import ru.railshop.onlineshop.exception.DaoException;
import ru.railshop.onlineshop.util.ConnectionManager;

import java.sql.*;
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
    private static final String GET_BY_EMAIL_AND_PASSWORD_SQL = """
            SELECT id, username, password, email, role, gender FROM users WHERE email = ? AND password = ?;
            """;

    public Optional<User> findByEmailAndPassword(String email, String password) {
        try (var connection = ConnectionManager.open();
             var prepareStatement = connection.prepareStatement(GET_BY_EMAIL_AND_PASSWORD_SQL)) {
            prepareStatement.setString(1, email);
            prepareStatement.setString(2, password);

            var result = prepareStatement.executeQuery();
            User user = null;
            while (result.next()) {
                user = buildUser(result);
            }
            return Optional.ofNullable(user);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean update(User user) {

        try (var connection = ConnectionManager.open();
             var prepareStatement = connection.prepareStatement(UPDATE_SQL)) {

            prepareStatement.setString(1, user.getUsername());
            prepareStatement.setString(2, user.getPassword());
            prepareStatement.setString(3, user.getEmail());
            prepareStatement.setObject(4, user.getRole().name(), Types.OTHER);
            prepareStatement.setObject(5, user.getGender().name(), Types.OTHER);
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
        Role role;
        Gender gender;
        try {
            role = Role.valueOf(result.getString("role"));
        } catch (IllegalArgumentException e) {
            throw new DaoException(new Exception("Invalid role in database for user ID: " + result.getLong("id")));
        }
        try {
            gender = Gender.valueOf(result.getString("gender"));
        } catch (IllegalArgumentException e) {
            throw new DaoException(new Exception("Invalid gender in database for user ID: " + result.getLong("id")));
        }
        return new User(result.getLong("id"),
                result.getString("username"),
                result.getString("password"),
                result.getString("email"),
                role,
                gender);
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
        if (user.getRole() == null || user.getGender() == null) {
            throw new DaoException(new Exception("Role or Gender is missing for the user"));
        }

        try (var connection = ConnectionManager.open();
             var prepareStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {

            prepareStatement.setString(1, user.getUsername());
            prepareStatement.setString(2, user.getPassword());
            prepareStatement.setString(3, user.getEmail());
            prepareStatement.setObject(4, user.getRole().name(), Types.OTHER);
            prepareStatement.setObject(5, user.getGender().name(), Types.OTHER);


            int affectedRows = prepareStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new DaoException(new Exception("Failed to insert the user. No rows affected."));
            }

            var keys = prepareStatement.getGeneratedKeys();
            if (keys.next()) {
                user.setId(keys.getLong(1));
            }
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
