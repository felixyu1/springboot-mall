package com.felixyu.springbootmall.dao;

import com.felixyu.springbootmall.dto.ProductRequest;
import com.felixyu.springbootmall.model.Product;

import java.util.List;

public interface ProductDao {

    List<Product> getProducts();
    Product getProducyById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);
    void deleteProductById(Integer productId);
}
