package com.felixyu.springbootmall.service.impl;

import com.felixyu.springbootmall.dao.ProductDao;
import com.felixyu.springbootmall.model.Product;
import com.felixyu.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;

    @Override
    public Product getProducyById(Integer productId) {
        return productDao.getProducyById(productId);
    }
}