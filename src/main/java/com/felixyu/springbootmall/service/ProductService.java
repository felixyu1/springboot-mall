package com.felixyu.springbootmall.service;

import com.felixyu.springbootmall.constant.ProductCategory;
import com.felixyu.springbootmall.dto.ProductRequest;
import com.felixyu.springbootmall.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getProducts(ProductCategory category, String search);
    Product getProductById(Integer productId);
    Integer createProduct(ProductRequest productRequest);
    void updateProduct(Integer productId, ProductRequest productRequest);
    void deleteProductById(Integer productId);
}
