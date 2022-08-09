package com.felixyu.springbootmall.dao.impl;

import com.felixyu.springbootmall.dao.ProductDao;
import com.felixyu.springbootmall.model.Product;
import com.felixyu.springbootmall.rowmapper.ProductRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class ProductDaoImpl implements ProductDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Product getProducyById(Integer productId) {

        String sql =
                " SELECT product_id, product_name, category, image_url, price, stock, description, " +
                        " created_date, last_modified_date " +
                        " FROM product " +
                        " WHERE product_id = :productId ";

        HashMap<String, Object> map = new HashMap<>();
        map.put("productId", productId);

        List<Product> productList =
                namedParameterJdbcTemplate.query(sql, map, new ProductRowMapper());

        if(productList.size()>0){
            return productList.get(0);
        }else{
            return null;
        }

    }
}
