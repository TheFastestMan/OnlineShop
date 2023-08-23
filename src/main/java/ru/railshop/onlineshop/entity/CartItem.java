package ru.railshop.onlineshop.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class CartItem {
    private Long id;
    private Cart cartId;
    private Product productId;
    private Integer quantity;
}
