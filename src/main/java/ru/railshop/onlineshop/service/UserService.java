package ru.railshop.onlineshop.service;

import org.modelmapper.ModelMapper;
import ru.railshop.onlineshop.dao.UserDao;
import ru.railshop.onlineshop.dto.ProductDto;
import ru.railshop.onlineshop.dto.UserDto;
import ru.railshop.onlineshop.entity.Gender;
import ru.railshop.onlineshop.entity.Product;
import ru.railshop.onlineshop.entity.Role;
import ru.railshop.onlineshop.entity.User;
import ru.railshop.onlineshop.exception.ValidationException;
import ru.railshop.onlineshop.validator.CreateUserValidator;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserService {

    private final static UserService INSTANCE = new UserService();
    private final UserDao userDao = UserDao.getInstance();
    private final ModelMapper modelMapper = new ModelMapper();

    private final CreateUserValidator createUserValidator = CreateUserValidator.getInstance();

    public List<UserDto> findAllUser() {
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


    public Optional<UserDto> findUserById(Long id) {
        return userDao.findById(id)
                .map(user -> UserDto.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .build());
    }

    public User create(UserDto userDto) {
        System.out.println("Received email: " + userDto.email()); // for debug

        var validationResult = createUserValidator.isValid(userDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }

        var mappedUser = convertUserDtoToUser(userDto);

        System.out.println("Mapped user email: " + mappedUser.getEmail());

        var result = userDao.save(mappedUser);
        return result;
    }

    public User convertUserDtoToUser(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        user.setEmail(userDto.email());
        user.setPassword(userDto.password());
        user.setUsername(userDto.username());
        user.setRole(userDto.role());
        user.setGender(userDto.gender());
        return user;
    }

    public Product convertProductDtoToProduct(ProductDto productDto) {
        Product product = modelMapper.map(productDto, Product.class);
        product.setDescription(productDto.description());
        product.setOwner(product.getOwner());
        product.setName(product.getName());
        product.setPrice(product.getPrice());
        product.setQuantity(product.getQuantity());
        return product;
    }


    private UserService() {
    }

    public static UserService getInstance() {
        return INSTANCE;
    }

    public Optional<UserDto> login(String email, String password) {
        return userDao.findByEmailAndPassword(email, password)
                .map(user -> UserDto.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .role(user.getRole())
                        .gender(user.getGender())
                        .build());

    }

    public List<ProductDto> getAllProductsByUserId(Long userId) {
        List<Product> products = userDao.findAllProductsByUserId(userId);
        return products.stream()
                .map(product -> new ProductDto(product.getId(), product.getDescription()))
                .collect(Collectors.toList());
    }

}