package ru.railshop.onlineshop.service;

import ru.railshop.onlineshop.dao.ProductDao;
import ru.railshop.onlineshop.dto.ProductDto;

import java.util.List;
import java.util.stream.Collectors;

public class ProductService {
    private static final ProductService INSTANCE = new ProductService();
    private final ProductDao productDao = ProductDao.getInstance();

    private ProductService() {
    }

    public List<ProductDto> findAllProduct() {
        return productDao.findAll().stream().map(product -> new ProductDto(product.getId(),
                "%s - %s - %s".formatted(
                        product.getName(),
                        product.getPrice(),
                        product.getQuantity()
                ))).collect(Collectors.toList());
    }

    public static ProductService getInstance() {
        return INSTANCE;
    }
}
