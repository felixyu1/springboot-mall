package com.felixyu.springbootmall.dao;

import com.felixyu.springbootmall.constant.ProductCategory;
import com.felixyu.springbootmall.dto.ProductRequest;
import com.felixyu.springbootmall.model.Product;

import java.util.List;

public interface ProductDao {

    List<Product> getProducts(ProductCategory category, String search);
    Product getProducyById(Integer productId);

    Integer createProduct(ProductRequest productRequest);

    void updateProduct(Integer productId, ProductRequest productRequest);
    void deleteProductById(Integer productId);
}
