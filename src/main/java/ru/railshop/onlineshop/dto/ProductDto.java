package ru.railshop.onlineshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ProductDto {
    private Long id;

    private String productName;

    private String description;

    private Integer quantity;

    private Double price;


}
