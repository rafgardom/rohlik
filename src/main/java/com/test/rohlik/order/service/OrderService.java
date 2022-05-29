package com.test.rohlik.order.service;

import com.test.rohlik.order.domain.Order;
import com.test.rohlik.order.dto.OrderDTO;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    Order findById(UUID id);

    List<Order> findAll();

    Order createOrder(OrderDTO orderDTO);

    void cancelOrder(Order order);

    void payOrder(UUID orderId);
}
