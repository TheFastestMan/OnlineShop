package ru.railshop.onlineshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.railshop.onlineshop.entity.Cart;
import ru.railshop.onlineshop.entity.Gender;
import ru.railshop.onlineshop.entity.Role;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long userId;
    private String username;
    private String email;
    private Role role;
    private Gender gender;
    private String password;
    private List<Cart> carts;

}
