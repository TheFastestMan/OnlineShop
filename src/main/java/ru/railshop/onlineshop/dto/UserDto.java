package ru.railshop.onlineshop.dto;

import lombok.Builder;
import ru.railshop.onlineshop.entity.Gender;
import ru.railshop.onlineshop.entity.Role;

@Builder
public record UserDto(Long id, String username, String email, String password, Role role, Gender gender) {
    public UserDto(String username, String password, String email, Role role, Gender gender) {
        this(null, username, email, password, role, gender);
    }

}
