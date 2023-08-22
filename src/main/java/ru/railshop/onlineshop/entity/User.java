package ru.railshop.onlineshop.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class User {

    private Long id;
    private String username;
    private String password;
    private String email;

}



