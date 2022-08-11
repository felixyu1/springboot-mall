package com.felixyu.springbootmall.service.impl;

import com.felixyu.springbootmall.constant.ProductCategory;
import com.felixyu.springbootmall.dao.ProductDao;
import com.felixyu.springbootmall.dto.ProductRequest;
import com.felixyu.springbootmall.model.Product;
import com.felixyu.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;

    @Override
    public List<Product> getProducts(ProductCategory category, String search) {
        return productDao.getProducts(category, search);
    }

    @Override
    public Product getProductById(Integer productId) {
        return productDao.getProducyById(productId);
    }

    @Override
    public Integer createProduct(ProductRequest productRequest) {
        return productDao.createProduct(productRequest);
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest productRequest) {

        productDao.updateProduct(productId, productRequest);
    }

    @Override
    public void deleteProductById(Integer productId) {

        productDao.deleteProductById(productId);
    }
}
