package com.felixyu.springbootmall.controller;

import com.felixyu.springbootmall.constant.ProductCategory;
import com.felixyu.springbootmall.dto.ProductQueryParams;
import com.felixyu.springbootmall.dto.ProductRequest;
import com.felixyu.springbootmall.model.Product;
import com.felixyu.springbootmall.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(
            @RequestParam(required = false) ProductCategory category,
            @RequestParam(required = false) String search
    ){

        ProductQueryParams productQueryParams = new ProductQueryParams();
        productQueryParams.setCategory(category);
        productQueryParams.setSearch(search);

        List<Product> productList = productService.getProducts(productQueryParams);
        return ResponseEntity.status(HttpStatus.OK).body(productList);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer productId){
        Product product = productService.getProductById(productId);

        if(product != null){
            return ResponseEntity.status(HttpStatus.OK).body(product);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest){

        Integer productId = productService.createProduct(productRequest);
        Product product = productService.getProductById(productId);

        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId,
                                                 @RequestBody @Valid ProductRequest productRequest){

        Product product = productService.getProductById(productId);
        if(product == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        productService.updateProduct(productId, productRequest);

        Product updatedProduct = productService.getProductById(productId);

        return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer productId){

        productService.deleteProductById(productId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
