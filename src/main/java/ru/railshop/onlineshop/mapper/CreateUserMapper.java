package ru.railshop.onlineshop.mapper;

import ru.railshop.onlineshop.dto.UserDto;
import ru.railshop.onlineshop.entity.Gender;
import ru.railshop.onlineshop.entity.Role;
import ru.railshop.onlineshop.entity.User;

public class CreateUserMapper implements Mapper<User, UserDto> {
    private static final CreateUserMapper INSTANCE = new CreateUserMapper();

    @Override
    public User mapFrom(UserDto object) {
        return User.builder()
                .username(object.username())
                .email(object.email())
                .password(object.password())
                .role(Role.valueOf(object.role()))
                .gender(Gender.valueOf(object.gender()))
                .build();
    }


    public static CreateUserMapper getInstance() {
        return INSTANCE;
    }
}
