package ru.railshop.onlineshop.service;

import org.modelmapper.ModelMapper;
import ru.railshop.onlineshop.dao.CartDao;
import ru.railshop.onlineshop.dto.ProductDto;
import ru.railshop.onlineshop.dto.UserDto;
import ru.railshop.onlineshop.entity.Product;
import ru.railshop.onlineshop.entity.User;


public class CartService {
    private final static CartService INSTANCE = new CartService();
    private final ModelMapper modelMapper = new ModelMapper();

    private CartDao cartDao = CartDao.getInstance();

    private CartService() {
    }

    public static CartService getInstance() {
        return INSTANCE;
    }

    public Product convertProductDtoToProduct(ProductDto productDto) {
        Product product = modelMapper.map(productDto, Product.class);
        product.setId(productDto.getId());
        product.setProductName(productDto.getProductName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        return product;
    }
    public User convertUserDtoToUser(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        return user;
    }

    public void addProductToCart(UserDto userDto, ProductDto productDto, int quantity) throws Exception {
        User user = convertUserDtoToUser(userDto);
        Product product = convertProductDtoToProduct(productDto);

        cartDao.addProductToCart(user, product, quantity);
    }
}
