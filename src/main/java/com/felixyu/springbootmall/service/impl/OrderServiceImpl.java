package com.felixyu.springbootmall.service.impl;

import com.felixyu.springbootmall.dao.OrderDao;
import com.felixyu.springbootmall.dao.ProductDao;
import com.felixyu.springbootmall.dto.BuyItem;
import com.felixyu.springbootmall.dto.CreateOrderRequest;
import com.felixyu.springbootmall.model.OrderItem;
import com.felixyu.springbootmall.model.Product;
import com.felixyu.springbootmall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ProductDao productDao;

    @Transactional
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {

        List<OrderItem> orderItemList = new ArrayList<>();

        // 計算花費
        Integer totalAmount = 0;
        List<BuyItem> buyItemList = createOrderRequest.getBuyItemList();
        for (BuyItem buyItem: buyItemList) {
            Product product =
                    productDao.getProductById(buyItem.getProductId());
            Integer amount = product.getPrice() * buyItem.getQuantity();

            totalAmount+= amount;

            //轉換BuyItem至OrderItem
            OrderItem orderItem = new OrderItem();
            orderItem.setAmount(amount);
            orderItem.setQuantity(buyItem.getQuantity());
            orderItem.setProductId(buyItem.getProductId());
            orderItemList.add(orderItem);
        }

        Integer orderId = orderDao.createOrder(userId, totalAmount);
        orderDao.createOrderItems(orderId, orderItemList);

        return orderId;
    }
}
