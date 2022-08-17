package com.felixyu.springbootmall.service;

import com.felixyu.springbootmall.dto.CreateOrderRequest;
import com.felixyu.springbootmall.dto.OrderQueryParams;
import com.felixyu.springbootmall.model.Order;

import java.util.List;

public interface OrderService {

    Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest);
    Order getOrderById(Integer orderId);
    List<Order> getOrders(OrderQueryParams orderQueryParams);
    Integer countOrder(OrderQueryParams orderQueryParams);

}
