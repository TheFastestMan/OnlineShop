package ru.railshop.onlineshop.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Order {
    private Long id;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
}
