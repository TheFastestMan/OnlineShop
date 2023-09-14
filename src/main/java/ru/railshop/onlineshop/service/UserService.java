package ru.railshop.onlineshop.service;

import ru.railshop.onlineshop.dao.UserDao;
import ru.railshop.onlineshop.dto.CreateUserDto;
import ru.railshop.onlineshop.dto.UserDto;
import ru.railshop.onlineshop.entity.User;
import ru.railshop.onlineshop.exception.ValidationException;
import ru.railshop.onlineshop.mapper.CreateUserMapper;
import ru.railshop.onlineshop.validator.CreateUserValidator;
import ru.railshop.onlineshop.validator.ValidateResult;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserService {

    private final static UserService INSTANCE = new UserService();
    private final UserDao userDao = UserDao.getInstance();
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

    public Optional<UserDto> findUserDyId(Long id) {
        return userDao.findById(id).map(user -> new UserDto(user.getId(),
                "%s - %s- %s".formatted(
                        user.getUsername(),
                        user.getPassword(),
                        user.getEmail()
                )));
    }

     public User create(CreateUserDto createUserDto){

        var validationResult = createUserValidator.isValid(createUserDto);

        if (!validationResult.isValid()){
            throw new ValidationException(validationResult.getErrors() );
        }

        var mappedUser = createUserMapper.mapFrom(createUserDto);
        var result = userDao.save(mappedUser);
        return result;

     }

    private UserService() {
    }

    public static UserService getInstance() {
        return INSTANCE;
    }
}
