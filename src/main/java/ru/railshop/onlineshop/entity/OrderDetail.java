package ru.railshop.onlineshop.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class OrderDetail {
    private Long id;
    private Order orderId;
    private Product productId;
    private Integer quantity;
}
