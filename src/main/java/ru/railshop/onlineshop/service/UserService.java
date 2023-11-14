package ru.railshop.onlineshop.service;

import org.modelmapper.ModelMapper;


import ru.railshop.onlineshop.dto.UserDto;
import ru.railshop.onlineshop.entity.User;
import ru.railshop.onlineshop.repository.UserRepository;

import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserService {


    private final static UserService INSTANCE = new UserService();
    private final UserRepository userRepository = UserRepository.getInstance();
    private final ModelMapper modelMapper = new ModelMapper();

    private UserService() {
    }

    public static UserService getInstance() {
        return INSTANCE;
    }

    private User convertUserDtoToUser(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        return user;
    }


    public Optional<UserDto> login(String email, String password) throws Exception {
        return userRepository.findByEmailAndPassword(email, password)
                .map(user -> UserDto.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .role(user.getRole())
                        .gender(user.getGender())
                        .password(user.getPassword())
                        .build());
    }

    public List<UserDto> findAllUser() throws Exception {
        return userRepository.findAll().stream()
                .map(user -> UserDto.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .role(user.getRole())
                        .gender(user.getGender())
                        .password(user.getPassword())
                        .build())
                .collect(Collectors.toList());
    }


    public User create(UserDto userDto) {

        var validationFactory = Validation.buildDefaultValidatorFactory();
        var validator = validationFactory.getValidator();
        var validationResult = validator.validate(userDto);

        if (!validationResult.isEmpty()) {
            throw new ConstraintViolationException(validationResult);
        }

        var mappedUser = convertUserDtoToUser(userDto);

        System.out.println("Mapped user email: " + mappedUser.getEmail());

        var result = userRepository.save(mappedUser);
        return result;
    }
}