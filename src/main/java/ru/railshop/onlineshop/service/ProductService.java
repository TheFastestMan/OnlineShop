package ru.railshop.onlineshop.service;

import org.modelmapper.ModelMapper;
import ru.railshop.onlineshop.dao.ProductDao;
import ru.railshop.onlineshop.dto.ProductDto;
import ru.railshop.onlineshop.entity.Product;

import java.util.List;
import java.util.stream.Collectors;

public class ProductService {
    private final static ProductService INSTANCE = new ProductService();
    private ModelMapper modelMapper = new ModelMapper();
    private ProductDao productDao = ProductDao.getInstance();
    public static ProductService getInstance(){
        return INSTANCE;
    }

    private ProductService() {
    }
    public ProductDto convertProductToProductDto(Product product) {
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        productDto.setProductId(product.getProductId());
        productDto.setProductName(product.getProductName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        return productDto;
    }
    public List<ProductDto> getAllProducts() throws Exception {

        return productDao.findAllProducts().stream().map(product ->
                new ProductDto(product.getProductId(),
                        product.getProductName(),
                        product.getDescription(),
                        product.getQuantity(),
                        product.getPrice())).collect(Collectors.toList());
    }

    public ProductDto getProductById(Long productId) {
        Product product = productDao.getProductById(productId);
        if (product != null) {
            return convertProductToProductDto(product);
        }
        return null;
    }

    public List<ProductDto> getProductsByUserId(Long userId) throws Exception {
        List<Product> products = productDao.getProductsByUserId(userId);
        return products.stream()
                .map(product -> new ProductDto(product.getProductId(), product.getProductName()
                , product.getDescription(), product.getQuantity(), product.getPrice()))
                .collect(Collectors.toList());
    }

    public void reduceQuantityByOne(Long productId, int quantityToDecrease){
        productDao.decreaseQuantityByAmount( productId, quantityToDecrease);
    }
}
