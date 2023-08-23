package ru.railshop.onlineshop.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Review {
    private Long id;
    private Product productId;
    private String reviewText;
    private Integer rating;
}
