package com.felixyu.springbootmall.dao.impl;

import com.felixyu.springbootmall.dao.OrderDao;
import com.felixyu.springbootmall.model.Order;
import com.felixyu.springbootmall.model.OrderItem;
import com.felixyu.springbootmall.model.Product;
import com.felixyu.springbootmall.rowmapper.OrderItemRowMapper;
import com.felixyu.springbootmall.rowmapper.OrderRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OrderDaoImpl implements OrderDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Integer createOrder(Integer userId, Integer totalAmount) {

        String sql = " INSERT INTO `order`(user_id, total_amount, created_date, last_modified_date) " +
                     " VALUES(:userId, :totalAmount, :createdDate, :lastModifiedDate) ";

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("totalAmount", totalAmount);

        Date nowDate = new Date();
        map.put("createdDate", nowDate);
        map.put("lastModifiedDate", nowDate);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
        int orderId = keyHolder.getKey().intValue();

        return orderId;
    }

    @Override
    public void createOrderItems(Integer orderId, List<OrderItem> orderItemList) {

        String sql = " INSERT INTO order_item(order_id, product_id, quantity, amount) " +
                     " VALUES(:orderId, :productId, :quantity, :amount) ";

        Map<String, Object>[] maps = new Map[orderItemList.size()];

        for (int i=0; i<orderItemList.size(); i++) {
            OrderItem orderItem = orderItemList.get(i);

            maps[i] = new HashMap<>();
            maps[i].put("orderId", orderId);
            maps[i].put("productId", orderItem.getProductId());
            maps[i].put("quantity", orderItem.getQuantity());
            maps[i].put("amount", orderItem.getAmount());

        }

        namedParameterJdbcTemplate.batchUpdate(sql, maps);
    }

    @Override
    public Order getOrderById(Integer orderId) {
        String sql = " SELECT order_id, user_id, total_amount, created_date, last_modified_date " +
                     " FROM `order` " +
                     " WHERE order_id = :orderId ";

        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);

        List<Order> orderList =
                namedParameterJdbcTemplate.query(sql, map, new OrderRowMapper());

        if(orderList.size()>0){
            return orderList.get(0);
        }else {
            return null;
        }
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(Integer orderId) {

        String sql = " SELECT oi.order_item_id, oi.order_id, oi.product_id, oi.quantity, oi.amount, " +
                     " p.product_name, p.image_url " +
                     " FROM order_item oi " +
                     " LEFT JOIN product p ON oi.product_id = p.product_id " +
                     " WHERE oi.order_id = :orderId ";

        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);

        List<OrderItem> orderItemList =
                namedParameterJdbcTemplate.query(sql, map, new OrderItemRowMapper());

        return orderItemList;
    }
}
