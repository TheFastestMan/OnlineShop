package ru.railshop.onlineshop.mapper;

import ru.railshop.onlineshop.dto.CreateUserDto;
import ru.railshop.onlineshop.entity.User;

public class UserMapper {
    private static final UserMapper INSTANCE = new UserMapper();

    public CreateUserDto mapTo(User object) {
        String name = object.getUsername();
        String password = object.getPassword();
        String email = object.getEmail();
        String role = String.valueOf(object.getRole());
        String gender = String.valueOf(object.getGender());

        CreateUserDto createUserDto = new CreateUserDto(name, password, email, role, gender);
        return createUserDto;
    }

    public static UserMapper getInstance() {
        return INSTANCE;
    }
}
