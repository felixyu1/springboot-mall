package com.felixyu.springbootmall.service;

import com.felixyu.springbootmall.dto.ProductQueryParams;
import com.felixyu.springbootmall.dto.ProductRequest;
import com.felixyu.springbootmall.model.Product;

import java.util.List;

public interface ProductService {

    List<Product> getProducts(ProductQueryParams productQueryParams);
    Product getProductById(Integer productId);
    Integer countProduct(ProductQueryParams productQueryParams);
    Integer createProduct(ProductRequest productRequest);
    void updateProduct(Integer productId, ProductRequest productRequest);
    void deleteProductById(Integer productId);
}
