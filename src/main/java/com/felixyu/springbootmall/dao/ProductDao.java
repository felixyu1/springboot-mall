package com.felixyu.springbootmall.dao;

import com.felixyu.springbootmall.dto.ProductRequest;
import com.felixyu.springbootmall.model.Product;

public interface ProductDao {

    Product getProducyById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);
    void deleteProductById(Integer productId);
}
