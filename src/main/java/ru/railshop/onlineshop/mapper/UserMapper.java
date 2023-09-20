package ru.railshop.onlineshop.mapper;

import ru.railshop.onlineshop.dto.UserDto;
import ru.railshop.onlineshop.entity.User;

public class UserMapper {
    private static final UserMapper INSTANCE = new UserMapper();

    public UserDto mapTo(User object) {

        UserDto createUserDto = new UserDto(id, description);
        return createUserDto;
    }

    public static UserMapper getInstance() {
        return INSTANCE;
    }
}
