package com.felixyu.springbootmall.service;

import com.felixyu.springbootmall.dto.CreateOrderRequest;
import com.felixyu.springbootmall.model.Order;

public interface OrderService {

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
    Order getOrderById(Integer orderId);
}
