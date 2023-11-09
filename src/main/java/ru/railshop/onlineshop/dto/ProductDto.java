package ru.railshop.onlineshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductDto {
    private Long productId;

    private String productName;

    private String description;

    private Integer quantity;

    private Double price;


}
