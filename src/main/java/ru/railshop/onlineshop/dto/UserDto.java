package ru.railshop.onlineshop.dto;

import lombok.Builder;

@Builder
public record UserDto(Long id, String username, String email, String password, String role, String gender) {
    public UserDto(String username, String password, String email, String role, String gender) {
        this(null, username, email, password, role, gender);
    }
}
