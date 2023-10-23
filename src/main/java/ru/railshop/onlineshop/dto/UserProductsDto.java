package ru.railshop.onlineshop.dto;

import lombok.Data;
import ru.railshop.onlineshop.entity.Product;

import java.util.List;
@Data
public class UserProductsDto {
    private Long userId;
    private String userName;
    private List<Product> products;
}
