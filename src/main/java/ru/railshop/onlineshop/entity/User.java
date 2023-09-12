package ru.railshop.onlineshop.entity;

import lombok.*;

@Data
@AllArgsConstructor
@Builder
public class User {

    private Long id;
    private String username;
    private String password;
    private String email;
    private Role role;
    private Gender gender;

}



