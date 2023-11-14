package ru.railshop.onlineshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.railshop.onlineshop.entity.Cart;
import ru.railshop.onlineshop.entity.Gender;
import ru.railshop.onlineshop.entity.Role;

import javax.validation.constraints.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long id;

    @NotEmpty(message = "Name should not be empty")
    private String username;

    @Email(message = "Email should be valid")
    @NotEmpty(message = "Email should not be empty")
    private String email;

    @NotNull(message = "Role should not be empty")
    private Role role;

    @NotNull(message = "Gender should not be empty")
    private Gender gender;

    @Size(min = 5, message = "Password should be not less than 5 characters")
    @NotEmpty(message = "Password should not be empty")
    private String password;

    private List<Cart> carts;

}
