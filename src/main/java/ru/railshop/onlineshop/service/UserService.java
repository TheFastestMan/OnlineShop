package ru.railshop.onlineshop.service;

import ru.railshop.onlineshop.dao.UserDao;
import ru.railshop.onlineshop.dto.UserDto;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserService {

    private final static UserService INSTANCE = new UserService();
    private final UserDao userDao = UserDao.getInstance();

    public List<UserDto> findAllUser() {
        return userDao.findAll().stream()
                .map(user -> new UserDto(user.getId(),
                        "%s - %s".formatted(
                                user.getUsername(),
                                user.getEmail()
                        ))).collect(Collectors.toList());
    }

    public Optional<UserDto> findUserDyId(Long id) {
        return userDao.findById(id).map(user -> new UserDto(user.getId(),
                "%s - %s- %s".formatted(
                        user.getUsername(),
                        user.getPassword(),
                        user.getEmail()
                )));
    }

    private UserService() {
    }

    public static UserService getInstance() {
        return INSTANCE;
    }
}
