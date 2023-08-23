package ru.railshop.onlineshop.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Cart {
    private Long id;
    private User userId;
    private LocalDateTime createdAt;
}
