package com.felixyu.springbootmall.service.impl;

import com.felixyu.springbootmall.dao.OrderDao;
import com.felixyu.springbootmall.dao.ProductDao;
import com.felixyu.springbootmall.dao.UserDao;
import com.felixyu.springbootmall.dto.BuyItem;
import com.felixyu.springbootmall.dto.CreateOrderRequest;
import com.felixyu.springbootmall.dto.OrderQueryParams;
import com.felixyu.springbootmall.model.Order;
import com.felixyu.springbootmall.model.OrderItem;
import com.felixyu.springbootmall.model.Product;
import com.felixyu.springbootmall.model.User;
import com.felixyu.springbootmall.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

    private final static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private UserDao userDao;

    @Transactional
    @Override
    public Integer createOrder(Integer userId, CreateOrderRequest createOrderRequest) {

        // 檢查user是否存在
        User user = userDao.getUserById(userId);
        if(user == null){
            log.warn("該userId {} 不存在", userId);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        List<OrderItem> orderItemList = new ArrayList<>();

        // 計算花費
        Integer totalAmount = 0;
        List<BuyItem> buyItemList = createOrderRequest.getBuyItemList();
        for (BuyItem buyItem: buyItemList) {
            Product product =
                    productDao.getProductById(buyItem.getProductId());

            // 檢查 product是否存在
            if(product == null){
                log.warn("該productId {} 不存在", buyItem.getProductId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            // 檢查庫存是否足夠
            if(product.getStock() < buyItem.getQuantity()){
                log.warn("該商品 {} 庫存數量不足，無法購買。/n剩餘庫存:{} 欲購數量：{} ",
                        buyItem.getProductId(), product.getStock(), buyItem.getQuantity());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            // 扣除商品庫存
            productDao.updateStock(buyItem.getProductId(), product.getStock()-buyItem.getQuantity());

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

    @Override
    public Order getOrderById(Integer orderId) {
        Order order = orderDao.getOrderById(orderId);

        List<OrderItem> orderItemList = orderDao.getOrderItemsByOrderId(orderId);

        order.setOrderItemList(orderItemList);

        return order;
    }

    @Override
    public List<Order> getOrders(OrderQueryParams orderQueryParams) {
        List<Order> orderList = orderDao.getOrders(orderQueryParams);

        for (Order order: orderList) {
            List<OrderItem> orderItemList = orderDao.getOrderItemsByOrderId(order.getOrderId());
            order.setOrderItemList(orderItemList);
        }

        return orderList;
    }

    @Override
    public Integer countOrder(OrderQueryParams orderQueryParams) {
        return orderDao.countOrder(orderQueryParams);
    }
}
