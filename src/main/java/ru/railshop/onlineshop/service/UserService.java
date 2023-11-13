package ru.railshop.onlineshop.service;

import org.hibernate.Session;
import org.modelmapper.ModelMapper;
import ru.railshop.onlineshop.dao.UserDao;
import ru.railshop.onlineshop.dto.UserDto;
import ru.railshop.onlineshop.entity.User;
import ru.railshop.onlineshop.exception.ValidationException;
import ru.railshop.onlineshop.validator.CreateUserValidator;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserService {
    private final CreateUserValidator createUserValidator = CreateUserValidator.getInstance();


    private final static UserService INSTANCE = new UserService();
    private final UserDao userDao = UserDao.getInstance();
    private final ModelMapper modelMapper = new ModelMapper();

    private UserService() {
    }

    public static UserService getInstance() {
        return INSTANCE;
    }

    public User convertUserDtoToUser(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        return user;
    }


    public Optional<UserDto> login(String email, String password) throws Exception {
        return userDao.findByEmailAndPassword(email, password)
                .map(user -> UserDto.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .role(user.getRole())
                        .gender(user.getGender())
                        .build());
    }
    public List<UserDto> findAllUser() throws Exception {
        return userDao.findAll().stream()
                .map(user -> UserDto.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .role(user.getRole())
                        .gender(user.getGender())
                        .build())
                .collect(Collectors.toList());
    }


    public User create(UserDto userDto) {
        System.out.println("Received email: " + userDto.getEmail()); // for debug

        var validationResult = createUserValidator.isValid(userDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }

        var mappedUser = convertUserDtoToUser(userDto);

        System.out.println("Mapped user email: " + mappedUser.getEmail());

        var result = userDao.save(mappedUser);
        return result;
    }
}