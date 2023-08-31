package ru.railshop.onlineshop.service;

import ru.railshop.onlineshop.dao.OrderDao;
import ru.railshop.onlineshop.dto.OrderDto;
import ru.railshop.onlineshop.entity.OrderDetail;
import ru.railshop.onlineshop.entity.OrderStatus;

import java.util.List;
import java.util.stream.Collectors;

public class OrderService {
    private static final OrderService INSTANCE = new OrderService();
    private final OrderDao orderDao = OrderDao.getInstance();

    public List<OrderDto> findAllOrder() {
        return orderDao.findAll().stream().map(order -> new OrderDto(order.getId(),
                "%s - %s".formatted(
                        order.getOrderDate(),
                        order.getOrderStatus()
                ))).collect(Collectors.toList());
    }

    private OrderService() {
    }

    public static OrderService getInstance() {
        return INSTANCE;
    }
}
