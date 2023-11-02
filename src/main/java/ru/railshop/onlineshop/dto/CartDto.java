package ru.railshop.onlineshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.railshop.onlineshop.entity.CartItem;
import ru.railshop.onlineshop.entity.User;


import java.util.Date;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartDto {
    private Long cartId;

    private User user;

    private Date createdAt;

    private List<CartItem> cartItems;
}
