package ru.railshop.onlineshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ProductDto {
    private Long id;
    @NotEmpty(message = "Product name should not be empty")
    private String productName;
    @NotEmpty(message = "Description should not be empty")
    private String description;

    @NotNull(message = "Quantity should not be null")
    @Min(value = 1, message = "Quantity should not be less than 1")
    private Integer quantity;
    @NotNull(message = "Price should not be null")
    private Double price;

}
