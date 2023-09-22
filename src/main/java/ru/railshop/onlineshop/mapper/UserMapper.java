package ru.railshop.onlineshop.mapper;

import ru.railshop.onlineshop.dto.UserDto;
import ru.railshop.onlineshop.entity.User;

public class UserMapper {

    private static final UserMapper INSTANCE = new UserMapper();

    private UserMapper() {
    }

    public UserDto mapTo(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole().name())
                .gender(user.getGender().name())
                .build();
    }


    public static UserMapper getInstance() {
        return INSTANCE;
    }
}
