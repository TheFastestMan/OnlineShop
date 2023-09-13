package ru.railshop.onlineshop.dto;

import lombok.Builder;
import lombok.Value;
import ru.railshop.onlineshop.entity.Gender;
import ru.railshop.onlineshop.entity.Role;

@Value
public class CreateUserDto {
 String username;
     String password;
     String email;
     String role;
     String gender;

}
