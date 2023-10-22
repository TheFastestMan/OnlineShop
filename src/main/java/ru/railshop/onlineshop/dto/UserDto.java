package ru.railshop.onlineshop.dto;

import lombok.Builder;
import ru.railshop.onlineshop.entity.Gender;
import ru.railshop.onlineshop.entity.Product;
import ru.railshop.onlineshop.entity.Role;

import java.util.List;

@Builder
public record UserDto(Long id, String username, String email, String password, Role role, Gender gender,
                      List<Product> productList) {
    public UserDto(String username, String password, String email, Role role, Gender gender, List<Product> productList) {
        this(null, username, email, password, role, gender, productList);
    }

}
