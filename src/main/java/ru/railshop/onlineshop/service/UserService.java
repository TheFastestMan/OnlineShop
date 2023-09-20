package ru.railshop.onlineshop.service;

import ru.railshop.onlineshop.dao.UserDao;
import ru.railshop.onlineshop.dto.UserDto;
import ru.railshop.onlineshop.entity.User;
import ru.railshop.onlineshop.exception.ValidationException;
import ru.railshop.onlineshop.mapper.CreateUserMapper;
import ru.railshop.onlineshop.mapper.UserMapper;
import ru.railshop.onlineshop.validator.CreateUserValidator;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserService {

    private final static UserService INSTANCE = new UserService();
    private final UserDao userDao = UserDao.getInstance();
    private final UserMapper userMapper = UserMapper.getInstance();
    private final CreateUserMapper createUserMapper = CreateUserMapper.getInstance();

    private final CreateUserValidator createUserValidator = CreateUserValidator.getInstance();

    public List<UserDto> findAllUser() {
        return userDao.findAll().stream()
                .map(user -> new UserDto(user.getId(),
                        "%s - %s".formatted(
                                user.getUsername(),
                                user.getEmail()
                        ))).collect(Collectors.toList());
    }

    public Optional<UserDto> findUserById(Long id) {

        return userDao.findById(id).map(user -> new UserDto(user.getId(),
                "%s - %s- %s".formatted(
                        user.getUsername(),
                        user.getPassword(),
                        user.getEmail()
                )));

    }

    public User create(UserDto userDto) {

        var validationResult = createUserValidator.isValid(userDto);

        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }

        var mappedUser = createUserMapper.mapFrom(userDto);
        var result = userDao.save(mappedUser);
        return result;

    }

    private UserService() {
    }

    public static UserService getInstance() {
        return INSTANCE;
    }

    public Optional<UserDto> login(String email, String password) {
        return userDao.setGetByEmailAndPassword(email, password).map(user -> new UserDto(user.getId(),
                "%s - %s- %s".formatted(
                        user.getUsername(),
                        user.getPassword(),
                        user.getEmail()
                )));
    }
}
