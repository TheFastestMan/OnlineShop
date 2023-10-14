package ru.railshop.onlineshop.entity;

import lombok.*;
import javax.persistence.*;


@Data
@AllArgsConstructor
@Builder
@Entity
@Table(name="user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, unique = true, nullable = false)
    private String username;

    @Column(length = 60, nullable = false)
    private String password;

    @Column(length = 100, unique = true, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "user_role")
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "user_gender")
    private Gender gender;

}
